package com.example.proyectoIntegrador11.security;


import com.example.proyectoIntegrador11.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity //postman no funciona con esta anotacion
public class ConfigWebSecurity {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean //este metodo provee de la autenticacion de la aplicacion
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(usuarioService);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/get_turnos.html").hasRole("USER")
                    .requestMatchers("/post_odontologos.html","/get_odontologos.html", "/post_turnos.html", "/post_pacientes.html", "/get_pacientes.html").hasRole("ADMIN")
                    .requestMatchers("/odontologos/**", "/pacientes/**", "/turnos/registrar").hasRole("ADMIN")
                    .anyRequest().authenticated()
            )
                .formLogin((form) -> form
                        .defaultSuccessUrl("/index.html", true)
                        .permitAll()
                )
                .logout(withDefaults());
        return http.build();
    }
}