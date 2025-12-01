// No necesitas mapeo para /admin/reservas/crear en el ReservaController
package com.utp.hexagonal.proyecto.infraestructura.controller;

import com.utp.hexagonal.proyecto.aplicacion.service.PdfService;
import com.utp.hexagonal.proyecto.aplicacion.service.ReservaService;
import com.utp.hexagonal.proyecto.dominio.modelo.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final PdfService pdfService;

    @Autowired
    public ReservaController(ReservaService reservaService , PdfService pdfService) {
        this.reservaService = reservaService;
        this.pdfService = pdfService;
    }


    // API para nueva reserva, solo para clientes
    @PostMapping("/crear")
    public ResponseEntity<Object> crearReserva(@RequestBody ReservaRequest reservaRequest) {
        try {
            String respuesta = reservaService.crearReserva(reservaRequest.getNombreMascota(),
                    reservaRequest.getFecha(),
                    reservaRequest.getHora(),
                    reservaRequest.getMensaje());
            byte[] pdfBytes = pdfService.generarPdf(reservaRequest);
            return ResponseEntity.ok(new ResponseMessage("success", respuesta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("error", "Hubo un problema al crear la reserva."));
        }
    }

    @PostMapping("/pdf-temporal")
    public ResponseEntity<byte[]> generarPdfTemporal(@RequestBody ReservaRequest reservaRequest) throws Exception {

        try {

            byte[] pdfBytes = pdfService.generarPdf(reservaRequest);

            return ResponseEntity.ok()
                    .header("Content-Disposition",
                            "attachment; filename=reserva_" + reservaRequest.getNombreMascota() + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace(); // ⭐ para ver el error real en consola
            throw new RuntimeException(e);
        }
    }


    public static class ReservaRequest {
        private String nombreMascota;
        private String fecha;
        private String hora;
        private String mensaje;

        // Getters y setters
        public String getNombreMascota() {
            return nombreMascota;
        }

        public void setNombreMascota(String nombreMascota) {
            this.nombreMascota = nombreMascota;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public String getHora() {
            return hora;
        }

        public void setHora(String hora) {
            this.hora = hora;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }

    public static class ResponseMessage {
        private String status;
        private String message;

        public ResponseMessage(String status, String message) {
            this.status = status;
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}
