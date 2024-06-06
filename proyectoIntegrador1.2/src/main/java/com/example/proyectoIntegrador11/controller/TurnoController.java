package com.example.proyectoIntegrador11.controller;

import com.example.proyectoIntegrador11.entity.Odontologo;
import com.example.proyectoIntegrador11.entity.Paciente;
import com.example.proyectoIntegrador11.entity.Turno;
import com.example.proyectoIntegrador11.service.OdontologoService;
import com.example.proyectoIntegrador11.service.PacienteService;
import com.example.proyectoIntegrador11.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping("/registrar")
    public ResponseEntity<Turno> guardarTurno(@RequestBody Turno turno){
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());
        if (pacienteBuscado.isPresent() && odontologoBuscado.isPresent()) {
            turno.setPaciente(pacienteBuscado.get());
            turno.setOdontologo(odontologoBuscado.get());
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listarTodosLosTurnos(){
        List<Turno> turnos = turnoService.buscarTurnos();
        if (turnos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(turnos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable("id") Integer id) {
        Optional<Turno> turnoBuscado = turnoService.buscarTurnoPorId(id);
        if (turnoBuscado.isPresent()) {
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("Turno eliminado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turno no encontrado o eliminado con anterioridad");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarPorId(@PathVariable("id") Integer id) {
        Optional<Turno> turno = turnoService.buscarTurnoPorId(id);
        if (turno.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(turno.get());
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno) {
        Optional<Turno> actualizarTurno = turnoService.buscarTurnoPorId(turno.getId());
        if (actualizarTurno.isPresent()) {
            turnoService.actualizarTurno(turno);
            return ResponseEntity.ok("Turno actualizado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turno no encontrado");
    }
}
