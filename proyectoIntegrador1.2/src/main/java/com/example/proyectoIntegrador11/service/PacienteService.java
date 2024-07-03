package com.example.proyectoIntegrador11.service;

import com.example.proyectoIntegrador11.dto.PacienteDTO;
import com.example.proyectoIntegrador11.entity.Paciente;
import com.example.proyectoIntegrador11.repository.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ObjectMapper mapper;

    public List<PacienteDTO> buscarPacientes() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        List<PacienteDTO> pacientesDTO = new ArrayList<>();
        for (Paciente paciente : pacientes) {
            PacienteDTO pacienteDTO = mapper.convertValue(paciente, PacienteDTO.class);
            pacientesDTO.add(pacienteDTO);
        }
        return pacientesDTO;
    }

    public PacienteDTO guardarPaciente(Paciente paciente) {
        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        return mapper.convertValue(pacienteGuardado, PacienteDTO.class);
    }

    public Optional<PacienteDTO> buscarPacientePorId(Long id) {
        Optional<Paciente> pacienteBuscado = pacienteRepository.findById(id);
        if (pacienteBuscado.isPresent()) {
            Paciente paciente = pacienteBuscado.get();
            return Optional.of(mapper.convertValue(paciente, PacienteDTO.class));
        }
        return Optional.empty();
    }

    public void actualizarPaciente(Paciente paciente) {
        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        mapper.convertValue(pacienteGuardado, PacienteDTO.class);
    }

    public Optional<PacienteDTO> buscarPacientePorEmail(String email) {
        Optional<Paciente> paciente = pacienteRepository.findByEmail(email);
        if (paciente.isPresent()) {
            Paciente pacienteEmail = paciente.get();
            return Optional.of(mapper.convertValue(pacienteEmail, PacienteDTO.class));
        }
        return Optional.empty();
    }

    public void eliminarPaciente(Long id) {
        pacienteRepository.deleteById(id);
    }
}
