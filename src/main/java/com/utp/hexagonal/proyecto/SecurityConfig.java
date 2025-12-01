package com.utp.hexagonal.proyecto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html",
                                "/login.html",
                                "/usuarios/login",
                                "/usuarios/registro",
                                "/assets/**",
                                "/static/**" ,
                                "/servicios.html" ,
                                "/crear_reserva.html",
                                "admin_dashboard.html",
                                "/admin/reservas/listar",
                                "/admin/reservas/crear",
                                "/admin/reservas/editar/**",
                                "/admin/reservas/eliminar/**",
                                "reservas/crear",
                                "reservas/pdf-temporal",
                                "/usuario.html" ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable()); // desactiva el formulario por defecto
        return http.build();
    }
}
