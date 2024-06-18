package com.example.proyectoIntegrador11.security;

import com.example.proyectoIntegrador11.entity.Usuario;
import com.example.proyectoIntegrador11.entity.UsuarioRole;
import com.example.proyectoIntegrador11.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosIniciales implements ApplicationRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String passSinCifrar= "admin";
        String passCifrado= passwordEncoder.encode(passSinCifrar);
        Usuario usuario= new Usuario("jorgito", UsuarioRole.ROLE_ADMIN,passCifrado,"admin@admin.com","jpereyradh");
        System.out.println("pass cifrado: "+passCifrado);
        usuarioRepository.save(usuario);

        String passSinCifrarUser= "user";
        String passCifradoUser= passwordEncoder.encode(passSinCifrarUser);
        Usuario usuario1 = new Usuario("Fabiana", UsuarioRole.ROLE_USER, passCifradoUser, "usuario@usuario.com", "fabianaUsuario1");
        System.out.println("pass cifrado usuario: " + passCifradoUser);
        usuarioRepository.save(usuario1);
    }
}