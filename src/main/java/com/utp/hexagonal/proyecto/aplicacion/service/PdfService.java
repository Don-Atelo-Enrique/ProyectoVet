package com.utp.hexagonal.proyecto.aplicacion.service;

import com.utp.hexagonal.proyecto.dominio.modelo.Reserva;
import com.utp.hexagonal.proyecto.infraestructura.controller.AdminReservaController;
import com.utp.hexagonal.proyecto.infraestructura.controller.ReservaController;
import org.springframework.stereotype.Service;

import com.spire.doc.*;
import com.spire.doc.documents.*;
import com.spire.doc.fields.TextRange;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfService {

    public byte[] generarPdf(ReservaController.ReservaRequest reserva) throws Exception {

        System.out.println(">>> Generando PDF con Spire.Doc");

        // 1. Cargar plantilla DOCX
        Document document = new Document();
        document.loadFromFile("C:/VetClinic/Plantilla/prueba.docx");

        // 2. Obtener fecha actual
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String fechaHora = fechaHoraActual.format(formato);

        // 3. Reemplazar campos
        document.replace("[FECHA_Y_HORA_ACTUAL]", fechaHora, false, true);
        document.replace("[NOMBRE_MASCOTA]", reserva.getNombreMascota(), false, true);
        document.replace("[FECHA]", reserva.getFecha(), false, true);
        document.replace("[HORA]", reserva.getHora(), false, true);
        document.replace("[MENSAJE]", reserva.getMensaje(), false, true);

        // 4. Convertir a PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.saveToStream(baos, FileFormat.PDF);

        document.close();

        return baos.toByteArray();
    }
}
