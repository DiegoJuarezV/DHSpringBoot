package com.example.proyectoIntegrador11.controller;

import com.example.proyectoIntegrador11.dto.OdontologoDTO;
import com.example.proyectoIntegrador11.dto.PacienteDTO;
import com.example.proyectoIntegrador11.entity.Turno;
import com.example.proyectoIntegrador11.dto.TurnoDTO;
import com.example.proyectoIntegrador11.exception.BadRequestException;
import com.example.proyectoIntegrador11.exception.ResourceNotFoundException;
import com.example.proyectoIntegrador11.service.OdontologoService;
import com.example.proyectoIntegrador11.service.PacienteService;
import com.example.proyectoIntegrador11.service.TurnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
@Tag(name = "Controller de Turnos", description = "Este endpoint nos permite trabajar solo con turnos")
public class TurnoController {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping("/registrar")
    @Operation(summary = "Nos permite registrar un objeto turno")
    public ResponseEntity<Turno> guardarTurno(@RequestBody TurnoDTO turnoDTO) throws BadRequestException {
        Optional<PacienteDTO> pacienteBuscado = pacienteService.buscarPacientePorId(turnoDTO.getPaciente().getId());
        Optional<OdontologoDTO> odontologoBuscado = odontologoService.buscarOdontologoPorId(turnoDTO.getOdontologo().getId());
        if (pacienteBuscado.isPresent() && odontologoBuscado.isPresent()) {
            return ResponseEntity.ok(turnoService.registrarTurno(turnoDTO));
        }
        throw new BadRequestException("El paciente y/o odontólogo no está registrado en la base de datos");
    }

    @GetMapping
    @Operation(summary = "Muestra todos los turnos")
    public ResponseEntity<List<TurnoDTO>> listarTodosLosTurnos(){
        /*List<TurnoDTO> turnos = turnoService.listarTodos();
        if (turnos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }*/
        return ResponseEntity.ok(turnoService.listarTodos());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un turno por ID")
    public ResponseEntity<String> eliminarTurno(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurnoId(id);
        if (turnoBuscado.isPresent()) {
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("Turno eliminado");
        }
        throw new ResourceNotFoundException("No existe un turno con id: " + id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca un turno por ID")
    public ResponseEntity<TurnoDTO> buscarPorId(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<TurnoDTO> turno = turnoService.buscarTurnoId(id);
        if (turno.isEmpty()) {
            throw new ResourceNotFoundException("El turno con ID: " + id + " no está registrado");
        }
        return ResponseEntity.ok(turno.get());
    }

    @PutMapping("/actualizar")
    @Operation(summary = "Actualiza un turno mediante ID")
    public ResponseEntity<String> actualizarTurno(@RequestBody TurnoDTO turnoDTO) throws BadRequestException {
        Optional<TurnoDTO> actualizarTurno = turnoService.buscarTurnoId(turnoDTO.getId());
        if (actualizarTurno.isPresent()) {
            turnoService.actualizarTurno(turnoDTO);
            return ResponseEntity.ok("Turno actualizado");
        }
        throw new BadRequestException("Error en actualización: Turno no encontrado");
    }
}
