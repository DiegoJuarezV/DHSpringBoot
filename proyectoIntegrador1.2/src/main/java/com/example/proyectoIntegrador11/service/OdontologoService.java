package com.example.proyectoIntegrador11.service;

import com.example.proyectoIntegrador11.entity.Odontologo;
import com.example.proyectoIntegrador11.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    @Autowired
    private OdontologoRepository odontologoRepository;

    public List<Odontologo> buscarOdontologos() {
        return odontologoRepository.findAll();
    }

    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    public Optional<Odontologo> buscarOdontologoPorId(Integer id) {
        return odontologoRepository.findById(id);
    }

    public void actualizarOdontologo(Odontologo odontologo) {
        odontologoRepository.save(odontologo);
    }

    public void eliminarOdontologo(Integer id) {
        odontologoRepository.deleteById(id);
    }

    public Optional<Odontologo> buscarOdontologoPorMatricula(Integer numero) {
        return odontologoRepository.findByNumeroMatricula(numero);
    }
}
