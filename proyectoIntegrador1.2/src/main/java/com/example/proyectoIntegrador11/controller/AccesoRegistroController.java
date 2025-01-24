package com.example.proyectoIntegrador11.controller;

import com.example.proyectoIntegrador11.entity.Usuario;
import com.example.proyectoIntegrador11.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Controller de Turnos", description = "Este endpoint nos permite trabajar solo con turnos")
public class AccesoRegistroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/registrar")
    @Operation(summary = "Nos permite registrar un usuario")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioBuscado = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioBuscado.isPresent()) {
            return ResponseEntity.badRequest().body("El usuario ya ha sido registrado.");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca un usuario por ID")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.notFound().build();
    }
}
