package com.utp.hexagonal.proyecto.infraestructura.controller;

import com.utp.hexagonal.proyecto.aplicacion.service.UsuarioService;
import com.utp.hexagonal.proyecto.dominio.modelo.Rol;
import com.utp.hexagonal.proyecto.dominio.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<Object> registrarUsuario(@RequestBody Usuario usuario) {
        if (usuario.getRol() == null) {
            usuario.setRol(Rol.CLIENTE);
        }

        String respuesta = usuarioService.registrarUsuario(usuario.getNombre(), usuario.getCorreo(), usuario.getContraseña(), usuario.getRol().name());

        if ("Usuario registrado con éxito!".equals(respuesta)) {
            return ResponseEntity.ok(new ResponseMessage("success", respuesta, usuario.getCorreo(), usuario.getRol().name()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("error", respuesta, "", ""));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Object> loginUsuario(@RequestBody Usuario usuario) {
        String respuesta = usuarioService.loginUsuario(usuario.getCorreo(), usuario.getContraseña());

        if (respuesta.startsWith("Login exitoso")) {
            // Aquí también puedes enviar el rol para redirigir al dashboard adecuado
            return ResponseEntity.ok(new ResponseMessage("success", "Login exitoso", usuario.getCorreo(), "CLIENTE"));
        } else if (respuesta.equals("Admin dashboard")) {
            return ResponseEntity.ok(new ResponseMessage("success", "Login de admin exitoso", usuario.getCorreo(), "ADMIN"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage("error", "Correo o contraseña incorrectos", usuario.getCorreo(), ""));
        }
    }

    public static class ResponseMessage {
        private String status;
        private String message;
        private String correo;
        private String rol;

        public ResponseMessage(String status, String message, String correo, String rol) {
            this.status = status;
            this.message = message;
            this.correo = correo;
            this.rol = rol;
        }

        public String getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public String getCorreo() {
            return correo;
        }

        public String getRol() {
            return rol;
        }
    }
}
