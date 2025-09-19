package com.utp.hexagonal.proyecto.infraestructura.controller;

import com.utp.hexagonal.proyecto.aplicacion.service.ReservaService;
import com.utp.hexagonal.proyecto.dominio.modelo.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/reservas")
public class AdminReservaController {

    private final ReservaService reservaService;

    @Autowired
    public AdminReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Reserva>> obtenerReservas() {
        List<Reserva> reservas = reservaService.obtenerTodasLasReservas();
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<Object> crearReserva(@RequestBody ReservaController.ReservaRequest reservaRequest) {
        String respuesta = reservaService.crearReserva(
                reservaRequest.getNombreMascota(),
                reservaRequest.getFecha(),
                reservaRequest.getHora(),
                reservaRequest.getMensaje()
        );
        return ResponseEntity.ok(new ResponseMessage("success", respuesta));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Object> editarReserva(
            @PathVariable Long id,
            @RequestBody ReservaController.ReservaRequest reservaRequest) {
        String respuesta = reservaService.editarReserva(
                id,
                reservaRequest.getNombreMascota(),
                reservaRequest.getFecha(),
                reservaRequest.getHora(),
                reservaRequest.getMensaje()
        );
        return ResponseEntity.ok(new ResponseMessage("success", respuesta));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Object> eliminarReserva(@PathVariable Long id) {
        String respuesta = reservaService.eliminarReserva(id);
        return ResponseEntity.ok(new ResponseMessage("success", respuesta));
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

    public static class ReservaRequest {
        private String nombreMascota;
        private String fecha;
        private String hora;
        private String mensaje;

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
}
