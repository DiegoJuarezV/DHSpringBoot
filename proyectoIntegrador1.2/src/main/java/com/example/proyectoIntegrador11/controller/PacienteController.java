package com.example.proyectoIntegrador11.controller;


import com.example.proyectoIntegrador11.entity.Paciente;
import com.example.proyectoIntegrador11.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> bucarPacienteId(@PathVariable("id") Integer id) {
        Optional<Paciente> paciente = pacienteService.buscarPacientePorId(id);
        if (paciente.isPresent()) {
            return ResponseEntity.ok(paciente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPacienteId(@PathVariable("id") Integer id) {
        Optional<Paciente> paciente = pacienteService.buscarPacientePorId(id);
        if (paciente.isPresent()) {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok().body("Paciente eliminado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado o eliminado con anterioridad");
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        List<Paciente> pacientes = pacienteService.buscarPacientes();
        if (pacientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pacienteService.buscarPacientes());
    }

    @PostMapping("/registrar")
    public ResponseEntity<Paciente> registrar(@RequestBody Paciente paciente) {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorEmail(paciente.getEmail());
        if (pacienteBuscado.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody Paciente paciente) {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(paciente.getId());
        if (pacienteBuscado.isPresent()) {
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok().body("Paciente actualizado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado para actualizar");
    }
}
