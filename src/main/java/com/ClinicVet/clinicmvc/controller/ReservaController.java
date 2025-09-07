package com.ClinicVet.clinicmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import com.ClinicVet.clinicmvc.service.ReservaService;
import com.ClinicVet.clinicmvc.model.Reserva;

@Controller
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // Página para mostrar el formulario de reserva
    @GetMapping
    public String mostrarFormularioReserva(Model model) {
        model.addAttribute("reserva", new Reserva());  // Crear un objeto vacío de Reserva
        return "reserva";  // Vista reserva.html
    }

    // Procesar la reserva cuando el usuario envía el formulario
    @PostMapping
    public String crearReserva(@ModelAttribute Reserva reserva, Model model) {
        // Guardar la reserva usando el servicio
        Reserva nuevaReserva = reservaService.guardarReserva(reserva);
        model.addAttribute("reserva", nuevaReserva);  // Pasa los datos de la reserva al modelo
        return "reservaConfirmada";  // Redirigir a la vista de reservaConfirmada.html
    }
}
