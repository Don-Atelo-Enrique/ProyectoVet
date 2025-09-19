package com.utp.hexagonal.proyecto.aplicacion.service;

import com.utp.hexagonal.proyecto.dominio.modelo.Usuario;
import com.utp.hexagonal.proyecto.dominio.modelo.Rol;
import com.utp.hexagonal.proyecto.infraestructura.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String registrarUsuario(String nombre, String correo, String contraseña, String rol) {
        if (usuarioRepository.findByCorreo(correo) != null) {
            return "El correo ya está registrado.";
        }

        Rol role = Rol.CLIENTE;
        if (rol != null) {
            role = Rol.valueOf(rol);
        }

        Usuario usuario = new Usuario(nombre, correo, contraseña, role);
        usuarioRepository.save(usuario);
        return "Usuario registrado con éxito!";
    }

    public String loginUsuario(String correo, String contraseña) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario != null && usuario.getContraseña().equals(contraseña)) {
            if (usuario.getRol() == Rol.ADMIN) {
                return "Admin dashboard"; // Redirigir al dashboard del administrador
            }
            return "Login exitoso, bienvenido " + usuario.getNombre(); // Para un cliente
        }

        return "Correo o contraseña incorrectos.";
    }
}
