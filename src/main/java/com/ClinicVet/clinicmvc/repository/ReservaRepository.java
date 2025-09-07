package com.ClinicVet.clinicmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ClinicVet.clinicmvc.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Métodos personalizados si es necesario
}
