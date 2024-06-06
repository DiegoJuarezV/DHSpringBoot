package com.example.proyectoIntegrador11.service;

import com.example.proyectoIntegrador11.entity.Turno;
import com.example.proyectoIntegrador11.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    private TurnoRepository turnoRepository;

    public List<Turno> buscarTurnos() {
        return turnoRepository.findAll();
    }

    public Turno guardarTurno(Turno turno) {
        return turnoRepository.save(turno);
    }

    public Optional<Turno> buscarTurnoPorId(Integer id) {
        return turnoRepository.findById(id);
    }

    public void actualizarTurno(Turno turno) {
        turnoRepository.save(turno);
    }

    public void eliminarTurno(Integer id) {
        turnoRepository.deleteById(id);
    }
}
