package com.example.proyectoIntegrador11.service;

import com.example.proyectoIntegrador11.entity.Paciente;
import com.example.proyectoIntegrador11.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Paciente> buscarPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPacientePorId(Integer id) {
        return pacienteRepository.findById(id);
    }

    public void actualizarPaciente(Paciente paciente) {
        pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPacientePorEmail(String email) {
        return pacienteRepository.findByEmail(email);
    }

    public void eliminarPaciente(Integer id) {
        pacienteRepository.deleteById(id);
    }
}
