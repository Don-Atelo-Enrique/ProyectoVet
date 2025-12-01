package com.utp.hexagonal.proyecto.infraestructura.repository;

import com.utp.hexagonal.proyecto.dominio.modelo.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
