package com.utp.hexagonal.proyecto.aplicacion.service;

import com.utp.hexagonal.proyecto.dominio.modelo.Reserva;
import com.utp.hexagonal.proyecto.infraestructura.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva> obtenerTodasLasReservas() {
        return reservaRepository.findAll();
    }

    public String crearReserva(String nombreMascota, String fecha, String hora, String mensaje) {
        Reserva nuevaReserva = new Reserva(nombreMascota, fecha, hora, mensaje);
        reservaRepository.save(nuevaReserva);
        return "Reserva creada con éxito!";
    }

    public String editarReserva(Long id, String nombreMascota, String fecha, String hora, String mensaje) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);
        if (reserva != null) {
            reserva.setNombreMascota(nombreMascota);
            reserva.setFecha(fecha);
            reserva.setHora(hora);
            reserva.setMensaje(mensaje);
            reservaRepository.save(reserva);
            return "Reserva actualizada con éxito!";
        }
        return "Reserva no encontrada.";
    }

    public String eliminarReserva(Long id) {
        reservaRepository.deleteById(id);
        return "Reserva eliminada con éxito!";
    }
}
