package com.example.proyectoIntegrador11.dto;

import com.example.proyectoIntegrador11.entity.Domicilio;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PacienteDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String cedula;
    private LocalDate fechaIngreso;
    private Domicilio domicilio;
    private String email;
}
