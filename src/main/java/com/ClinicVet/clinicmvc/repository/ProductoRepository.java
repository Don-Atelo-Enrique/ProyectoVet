package com.ClinicVet.clinicmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ClinicVet.clinicmvc.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
}
