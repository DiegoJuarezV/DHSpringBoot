package com.example.proyectoIntegrador11.controller;

import com.example.proyectoIntegrador11.entity.Odontologo;
import com.example.proyectoIntegrador11.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    @Autowired
    private OdontologoService odontologoService;

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarPorId(@PathVariable("id") Integer id) {
       Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(id);
       if (odontologoBuscado.isPresent()) {
           return ResponseEntity.ok(odontologoBuscado.get());
       }
       return ResponseEntity.notFound().build();
    }

    @PostMapping("/registrar")
    public ResponseEntity<Odontologo> registrar(@RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @PutMapping
    public ResponseEntity<String> actualizar(@RequestBody Odontologo odontologo) {
        Optional<Odontologo> actualizarOdontologo = odontologoService.buscarOdontologoPorId(odontologo.getId());
        if (actualizarOdontologo.isPresent()) {
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok("Odontologo actualizado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Odontologo no encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        Optional<Odontologo> odontologo = odontologoService.buscarOdontologoPorId(id);
        if (odontologo.isPresent()) {
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("Odontologo eliminado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Odontologo no encontrado");
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> listarTodos() {
        return ResponseEntity.ok(odontologoService.buscarOdontologos());
    }
}
