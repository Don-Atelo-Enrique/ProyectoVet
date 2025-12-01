package com.utp.hexagonal.proyecto.aplicacion.service;

import com.utp.hexagonal.proyecto.dominio.modelo.Usuario;
import com.utp.hexagonal.proyecto.dominio.modelo.Rol;
import com.utp.hexagonal.proyecto.infraestructura.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

        //Encriptamos la contraseña
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String contraseñaEncriptada = encoder.encode(contraseña);

        System.out.println("Contraseña encriptada : " + contraseñaEncriptada);

        Usuario usuario = new Usuario(nombre, correo, contraseñaEncriptada, role);
        usuarioRepository.save(usuario);
        return "Usuario registrado con éxito!";
    }

    public String loginUsuario(String correo, String contraseña) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if(encoder.matches(contraseña , usuario.getContraseña())){
                if (usuario.getRol() == Rol.ADMIN) {
                    return "Admin dashboard"; // Redirigir al dashboard del administrador
                }
                return "Login exitoso, bienvenido " + usuario.getNombre(); // Para un cliente
            }
        }
        return "Correo o contraseña incorrectos.";
    }
}
