package com.example.proyectoIntegrador11.service;

import com.example.proyectoIntegrador11.entity.Turno;
import com.example.proyectoIntegrador11.entity.TurnoDTO;
import com.example.proyectoIntegrador11.repository.TurnoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TurnoService {
    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    ObjectMapper mapper;

    public List<TurnoDTO> buscarTurnos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoDTO> turnoDTOS = new ArrayList<>();
        for (Turno turno : turnos) {
            turnoDTOS.add(mapper.convertValue(turno, TurnoDTO.class));
        }
        return turnoDTOS;
    }

    public Turno guardarTurno(TurnoDTO turnoDTO) {
        Turno turno = mapper.convertValue(turnoDTO, Turno.class);
        return turnoRepository.save(turno);
    }

    public Optional<TurnoDTO> buscarTurnoPorId(Integer id) {
        Optional<Turno> turno = turnoRepository.findById(id);
        if (turno.isPresent()) {
            TurnoDTO turnoDTO = mapper.convertValue(turno.get(), TurnoDTO.class);
            return Optional.of(turnoDTO);
        }
        return Optional.empty();
    }

    public void actualizarTurno(TurnoDTO turnoDTO) {
        Turno turno = mapper.convertValue(turnoDTO, Turno.class);
        turnoRepository.save(turno);
    }

    public void eliminarTurno(Integer id) {
        turnoRepository.deleteById(id);
    }
}
