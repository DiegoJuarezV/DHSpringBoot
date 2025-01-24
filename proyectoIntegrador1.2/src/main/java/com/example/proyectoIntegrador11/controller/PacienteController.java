package com.example.proyectoIntegrador11.controller;


import com.example.proyectoIntegrador11.dto.PacienteDTO;
import com.example.proyectoIntegrador11.entity.Paciente;
import com.example.proyectoIntegrador11.exception.BadRequestException;
import com.example.proyectoIntegrador11.exception.ResourceNotFoundException;
import com.example.proyectoIntegrador11.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
@Tag(name = "Controller de Pacientes", description = "Este endpoint nos permite trabajar solo con pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/{id}")
    @Operation(summary = "Busca un paciente por ID")
    public ResponseEntity<PacienteDTO> bucarPacienteId(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<PacienteDTO> paciente = pacienteService.buscarPacientePorId(id);
        if (paciente.isPresent()) {
            return ResponseEntity.ok(paciente.get());
        }
        throw new ResourceNotFoundException("El paciente con ID: " + id + " no está registrado");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un paciente por ID")
    public ResponseEntity<String> eliminarPacienteId(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<PacienteDTO> paciente = pacienteService.buscarPacientePorId(id);
        if (paciente.isPresent()) {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok().body("Paciente eliminado");
        }
        throw new ResourceNotFoundException("No existe un paciente con id: " + id);
    }

    @GetMapping
    @Operation(summary = "Muestra todos los pacientes")
    public ResponseEntity<List<PacienteDTO>> listarPacientes() {
        /*List<Paciente> pacientes = pacienteService.buscarPacientes();
        if (pacientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }*/
        return ResponseEntity.ok(pacienteService.buscarPacientes());
    }

    @PostMapping("/registrar")
    @Operation(summary = "Nos permite registrar un objeto paciente")
    public ResponseEntity<PacienteDTO> registrar(@RequestBody Paciente paciente) throws BadRequestException {
        Optional<PacienteDTO> pacienteBuscado = pacienteService.buscarPacientePorEmail(paciente.getEmail());
        if (pacienteBuscado.isPresent()) {
            throw new BadRequestException("El paciente ya está registrado con el email ingresado");
        }
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    @PutMapping("/actualizar")
    @Operation(summary = "Actualiza un paciente mediante ID")
    public ResponseEntity<String> actualizar(@RequestBody Paciente paciente) throws BadRequestException {
        Optional<PacienteDTO> pacienteBuscado = pacienteService.buscarPacientePorId(paciente.getId());
        if (pacienteBuscado.isPresent()) {
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok().body("Paciente actualizado");
        }
        throw new BadRequestException("Error en actualización: Paciente no encontrado");
    }
}
