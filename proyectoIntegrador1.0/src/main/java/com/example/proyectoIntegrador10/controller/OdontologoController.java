package com.example.proyectoIntegrador10.controller;

import com.example.proyectoIntegrador10.model.Odontologo;
import com.example.proyectoIntegrador10.service.OdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

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
    public Odontologo guardarOdontologo(@RequestBody Odontologo odontologo) {
        return odontologoService.guardarOdontologo(odontologo);
    }

    @GetMapping
    public List<Odontologo> buscarTodos() {
        return odontologoService.listarOdontologos();
    }

    /*@DeleteMapping("/{id}")
    public void odontologo(@PathVariable("id") Integer id) {
        odontologoService.eliminarOdontologo(id);
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Integer id) {
        ResponseEntity response = null;
        if (odontologoService.buscarOdontologo(id) == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            odontologoService.eliminarOdontologo(id);
            response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return response;
    }

    /*
    public
     */


    //METODOS PARA MOSTRAR EN PANTALLA LOS DATOS
    /*@GetMapping("/{id}")
    public Odontologo buscarPorId(@PathVariable("id") Integer id) {
        Odontologo odontologo = odontologoService.buscarOdontologo(id);
        model.addAttribute("nombre", odontologo.getNombre());
        model.addAttribute("apellido", odontologo.getApellido());
        return "index";
        return odontologoService.buscarOdontologo(id);
    }*/

    /*
    @GetMapping("/Todos")
    public String buscarTodos(Model model) {
        List<Odontologo> odontologos = odontologoService.listarOdontologos();
        model.addAttribute("odontologos", odontologos);
        return "indexTodos";
    }*/
}
