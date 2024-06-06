package com.example.proyectoIntegrador11.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "turnos")
public class Turno   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    @JsonIgnoreProperties("turno")
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name = "odontologo_id")
    @JsonIgnoreProperties("turno")
    private Odontologo odontologo;
    @Column
    private LocalDate fecha;
}
