package com.example.proyectoIntegrador11.controller;

import com.example.proyectoIntegrador11.entity.Odontologo;
import com.example.proyectoIntegrador11.exception.BadRequestException;
import com.example.proyectoIntegrador11.exception.ResouceNotFounException;
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
    public ResponseEntity<Odontologo> buscarPorId(@PathVariable("id") Long id) {
       Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(id);
       if (odontologoBuscado.isPresent()) {
           return ResponseEntity.ok(odontologoBuscado.get());
       }
       return ResponseEntity.notFound().build();
    }

    @PostMapping("/registrar")
    public ResponseEntity<Odontologo> registrar(@RequestBody Odontologo odontologo) throws BadRequestException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorMatricula(odontologo.getNumeroMatricula());
        if (odontologoBuscado.isPresent()) {
           throw new BadRequestException("El odontólogo ya está registrado con la matricula ingresada");
        }
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody Odontologo odontologo) {
        Optional<Odontologo> actualizarOdontologo = odontologoService.buscarOdontologoPorId(odontologo.getId());
        if (actualizarOdontologo.isPresent()) {
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok("Odontólogo actualizado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Odontólogo no encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) throws ResouceNotFounException {
        Optional<Odontologo> odontologo = odontologoService.buscarOdontologoPorId(id);
        if (odontologo.isPresent()) {
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("Odontólogo eliminado");
        }
        throw new ResouceNotFounException("No existe un odontólogo con id: " + id);
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> listarTodos() {
        return ResponseEntity.ok(odontologoService.buscarOdontologos());
    }
}
