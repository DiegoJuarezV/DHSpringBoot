package com.example.proyectoIntegrador11.dto;

import com.example.proyectoIntegrador11.entity.Odontologo;
import com.example.proyectoIntegrador11.entity.Paciente;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TurnoDTO {

    private Long id;
    private LocalDate fecha;
    private Paciente paciente;
    private Odontologo odontologo;

    public TurnoDTO(LocalDate fecha, Paciente paciente, Odontologo odontologo) {
        this.fecha = fecha;
        this.paciente = paciente;
        this.odontologo = odontologo;
    }
}
