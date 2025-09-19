package com.utp.hexagonal.proyecto.infraestructura.repository;

import com.utp.hexagonal.proyecto.dominio.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByCorreo(String correo);
}
