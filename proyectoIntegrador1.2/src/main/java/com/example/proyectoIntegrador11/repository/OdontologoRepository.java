package com.example.proyectoIntegrador11.repository;

import com.example.proyectoIntegrador11.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {
    Optional<Odontologo> findByNumeroMatricula(Long numeroMatricula);
}
