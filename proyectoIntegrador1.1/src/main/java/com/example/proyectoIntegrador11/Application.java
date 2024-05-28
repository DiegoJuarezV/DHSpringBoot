package com.example.proyectoIntegrador11;

import com.example.proyectoIntegrador11.entity.Odontologo;
import com.example.proyectoIntegrador11.repository.BD;
import com.example.proyectoIntegrador11.service.OdontologoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		BD.crearTablas();
		Odontologo odontologo = new Odontologo(2145, "Juan", "Pablo");
		Odontologo odontologo2 = new Odontologo(789, "Laura", "Garcia");
		OdontologoService odontologoService = new OdontologoService();
		odontologoService.guardarOdontologo(odontologo);
		odontologoService.guardarOdontologo(odontologo2);
		SpringApplication.run(Application.class, args);
	}
}
