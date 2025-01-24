package com.example.proyectoIntegrador11.controller;

import com.example.proyectoIntegrador11.dto.OdontologoDTO;
import com.example.proyectoIntegrador11.entity.Odontologo;
import com.example.proyectoIntegrador11.exception.BadRequestException;
import com.example.proyectoIntegrador11.exception.ResourceNotFoundException;
import com.example.proyectoIntegrador11.service.OdontologoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
@Tag(name = "Controller de Odontólogos", description = "Este endpoint nos permite trabajar solo con odontólogos")
public class OdontologoController {
    @Autowired
    private OdontologoService odontologoService;

    @GetMapping("/{id}")
    @Operation(summary = "Busca un odontólogo por ID")
    public ResponseEntity<OdontologoDTO> buscarPorId(@PathVariable("id") Long id) throws ResourceNotFoundException{
       Optional<OdontologoDTO> odontologoBuscado = odontologoService.buscarOdontologoPorId(id);
       if (odontologoBuscado.isPresent()) {
           return ResponseEntity.ok(odontologoBuscado.get());
       }
       throw new ResourceNotFoundException("El odontólogo con ID: " + id + " no está registrado");
    }

    @PostMapping("/registrar")
    @Operation(summary = "Nos permite registrar un objeto odontólogo")
    public ResponseEntity<OdontologoDTO> registrar(@RequestBody Odontologo odontologo) throws BadRequestException {
        Optional<OdontologoDTO> odontologoBuscado = odontologoService.buscarOdontologoPorMatricula(odontologo.getNumeroMatricula());
        if (odontologoBuscado.isPresent()) {
           throw new BadRequestException("El odontólogo ya está registrado con la matricula ingresada");
        }
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @PutMapping("/actualizar")
    @Operation(summary = "Actualiza un odontólogo mediante ID")
    public ResponseEntity<String> actualizar(@RequestBody OdontologoDTO odontologo) throws BadRequestException {
        Optional<OdontologoDTO> actualizarOdontologo = odontologoService.buscarOdontologoPorId(odontologo.getId());
        if (actualizarOdontologo.isPresent()) {
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok("Odontólogo actualizado");
        }
        throw new BadRequestException("Error en actualización: Verifica que los datos sean correctos");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un odontólogo por ID")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<OdontologoDTO> odontologo = odontologoService.buscarOdontologoPorId(id);
        if (odontologo.isPresent()) {
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("Odontólogo eliminado");
        }
        throw new ResourceNotFoundException("No existe un odontólogo con id: " + id);
    }

    @GetMapping
    @Operation(summary = "Muestra todos los odontólogos")
    public ResponseEntity<List<OdontologoDTO>> listarTodos() {
        return ResponseEntity.ok(odontologoService.buscarOdontologos());
    }
}
