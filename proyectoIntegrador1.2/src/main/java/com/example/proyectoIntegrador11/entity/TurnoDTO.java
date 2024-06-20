package com.example.proyectoIntegrador11.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TurnoDTO {

    private Long id;
    private LocalDate fecha;
    private Long pacienteId;
    private Long odontologoId;
}
