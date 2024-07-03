package com.example.proyectoIntegrador11.service;

import com.example.proyectoIntegrador11.dto.OdontologoDTO;
import com.example.proyectoIntegrador11.dto.TurnoDTO;
import com.example.proyectoIntegrador11.entity.Odontologo;
import com.example.proyectoIntegrador11.repository.OdontologoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    @Autowired
    private OdontologoRepository odontologoRepository;

    @Autowired
    private ObjectMapper mapper;

    public List<OdontologoDTO> buscarOdontologos() {
        List<Odontologo> odontologos = odontologoRepository.findAll();
        List<OdontologoDTO> odontologoDTOS = new ArrayList<>();
        for (Odontologo odontologo: odontologos) {
            OdontologoDTO odontologoDTO = mapper.convertValue(odontologo, OdontologoDTO.class);
            odontologoDTOS.add(odontologoDTO);
        }
        return odontologoDTOS;
    }

    public OdontologoDTO guardarOdontologo(Odontologo odontologo) {
        Odontologo odontologoGuardado = odontologoRepository.save(odontologo);
        return mapper.convertValue(odontologoGuardado, OdontologoDTO.class);
    }

    public Optional<OdontologoDTO> buscarOdontologoPorId(Long id) {
        Optional<Odontologo> odontologo = odontologoRepository.findById(id);
        if (odontologo.isPresent()) {
            Odontologo odontologoBuscado = odontologo.get();
            return Optional.of(mapper.convertValue(odontologoBuscado, OdontologoDTO.class));
        }
        return Optional.empty();
    }

    public void actualizarOdontologo(OdontologoDTO odontologoDTO) {
        Odontologo odontologoActualizado = mapper.convertValue(odontologoDTO, Odontologo.class);
        odontologoRepository.save(odontologoActualizado);
    }

    public void eliminarOdontologo(Long id) {
        odontologoRepository.deleteById(id);
    }

    public Optional<OdontologoDTO> buscarOdontologoPorMatricula(Long numero) {
        Optional<Odontologo> odontologo = odontologoRepository.findByNumeroMatricula(numero);
        if (odontologo.isPresent()) {
            Odontologo odontologoBuscado = odontologo.get();
            return Optional.of(mapper.convertValue(odontologoBuscado, OdontologoDTO.class));
        }
        return Optional.empty();
    }
}
