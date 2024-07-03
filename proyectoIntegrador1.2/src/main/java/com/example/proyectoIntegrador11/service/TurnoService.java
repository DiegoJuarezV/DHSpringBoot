package com.example.proyectoIntegrador11.service;

import com.example.proyectoIntegrador11.entity.Turno;
import com.example.proyectoIntegrador11.dto.TurnoDTO;
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
    private ObjectMapper mapper;

    public Turno registrarTurno(TurnoDTO turnoDTO) {
        Turno turno = mapper.convertValue(turnoDTO, Turno.class);
        return turnoRepository.save(turno);
    }

    public List<TurnoDTO> listarTodos() {
        List<Turno> listaTurnos = turnoRepository.findAll();
        List<TurnoDTO> listaDTO = new ArrayList<>();
        for (Turno turno : listaTurnos) {
            TurnoDTO turnoDTO = mapper.convertValue(turno, TurnoDTO.class);
            listaDTO.add(turnoDTO);
        }
        return listaDTO;
    }

    public Optional<TurnoDTO> buscarTurnoId(Long id) {
        Optional<Turno> turnoBuscado= turnoRepository.findById(id);
        if(turnoBuscado.isPresent()){
            Turno turno = turnoBuscado.get();
            return Optional.of(mapper.convertValue(turno, TurnoDTO.class));
        }
        return Optional.empty();
    }

    public void eliminarTurno(Long id){
        turnoRepository.deleteById(id);
    }

    public void actualizarTurno(TurnoDTO turnoDTO) {
        Turno turno = mapper.convertValue(turnoDTO, Turno.class);
        turnoRepository.save(turno);
    }
}
