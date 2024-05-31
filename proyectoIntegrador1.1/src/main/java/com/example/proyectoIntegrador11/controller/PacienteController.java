package com.example.proyectoIntegrador11.controller;


import com.example.proyectoIntegrador11.entity.Paciente;
import com.example.proyectoIntegrador11.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private PacienteService pacienteService;

    public PacienteController() {
        pacienteService = new PacienteService();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> bucarPacienteId(@PathVariable("id") Integer id) {
        Paciente paciente = pacienteService.buscarPaciente(id);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPacienteId(@PathVariable("id") Integer id) {
        Paciente paciente = pacienteService.buscarPaciente(id);
        if (paciente != null) {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok().body("Paciente eliminado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado o eliminado con anterioridad");
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        List<Paciente> pacientes = pacienteService.buscarTodos();
        if (pacientes == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @PostMapping("/registrar")
    public ResponseEntity<Paciente> registrar(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody Paciente paciente) {
        Paciente pacienteBuscado = pacienteService.buscarPaciente(paciente.getId());
        if (pacienteBuscado != null) {
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok().body("Paciente actualizado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado para actualizar");
    }
}
