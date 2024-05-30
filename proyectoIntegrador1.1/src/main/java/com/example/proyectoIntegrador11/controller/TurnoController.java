package com.example.proyectoIntegrador11.controller;

import com.example.proyectoIntegrador11.entity.Turno;
import com.example.proyectoIntegrador11.service.OdontologoService;
import com.example.proyectoIntegrador11.service.PacienteService;
import com.example.proyectoIntegrador11.service.TurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private TurnoService turnoService;

    public TurnoController() {
        turnoService= new TurnoService();
    }
    @PostMapping("/registrar")
    public ResponseEntity<Turno> guardarTurno(@RequestBody Turno turno){

        PacienteService pacienteService= new PacienteService();
        OdontologoService odontologoService= new OdontologoService();
        if(pacienteService.buscarPaciente(turno.getPaciente().getId())!=null&&odontologoService.buscarOdontologo(turno.getOdontologo().getId())!=null){
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        }else{
            //bad request or not found
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<Turno>> listarTodosLosTurnos(){
        return ResponseEntity.ok(turnoService.listarTurnos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTurno(@PathVariable("id") Integer id) {
        ResponseEntity response = null;
        if (turnoService.buscarPorID(id) == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            turnoService.eliminarTurno(id);
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarPorId(@PathVariable("id") Integer id) {
        Turno turno = turnoService.buscarPorID(id);
        if (turno == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(turno);
    }

    @PutMapping("/actualizar")
    public ResponseEntity actualizarTurno(@RequestBody Turno turno) {
        Turno actualizarTurno = turnoService.buscarPorID(turno.getId());
        if (actualizarTurno!= null) {
            turnoService.actualizarTurno(turno);
            return ResponseEntity.ok("Turno actualizado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
