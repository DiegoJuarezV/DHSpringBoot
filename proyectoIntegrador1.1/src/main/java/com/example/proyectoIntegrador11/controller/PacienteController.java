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
    public ResponseEntity eliminarPacienteId(@PathVariable("id") Integer id) {
        ResponseEntity response = null;
        if (pacienteService.buscarPaciente(id) == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            pacienteService.eliminarPaciente(id);
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        List<Paciente> pacientes = pacienteService.buscarTodos();
        if (pacientes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @PostMapping("/registrar")
    public ResponseEntity<Paciente> registrar(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    @PutMapping("/actualizar")
    public String actualizar(@RequestBody Paciente paciente) {
        Paciente pacienteBuscado = pacienteService.buscarPaciente(paciente.getId());
        if (pacienteBuscado != null) {
            pacienteService.actualizarPaciente(paciente);
            return "Paciente actualizado";
        }
        else {
            return "Paciente no existente";
        }
    }
}
