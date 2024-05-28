package com.example.proyectoIntegrador11.controller;

import com.example.proyectoIntegrador11.entity.Odontologo;
import com.example.proyectoIntegrador11.service.OdontologoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    private OdontologoService odontologoService;

    public OdontologoController() {
        odontologoService = new OdontologoService();
    }

    @GetMapping("/{id}")
    public Odontologo buscarPorId(@PathVariable("id") Integer id) {
        return odontologoService.buscarOdontologo(id);
    }

    @PostMapping("/registrar")
    public Odontologo registrar(@RequestBody Odontologo odontologo) {
        return odontologoService.guardarOdontologo(odontologo);
    }

    @PutMapping
    public String actualizar(@RequestBody Odontologo odontologo) {
        Odontologo actualizarOdontologo = odontologoService.buscarOdontologo(odontologo.getId());
        if (actualizarOdontologo != null) {
            odontologoService.actualizarOdontologo(odontologo);
            return "Odontologo actualizado";
        } else {
            return "Odontologo no encontrado";
        }
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable("id") Integer id) {
        Odontologo odontologo = odontologoService.buscarOdontologo(id);
        if (odontologo != null) {
            odontologoService.eliminarOdontologo(id);
            return "Odontologo eliminado";
        } else {
            return "Odontologo no encontrado";
        }
    }

    @GetMapping
    public List<Odontologo> listarTodos() {
        return odontologoService.listarOdontologos();
    }
}
