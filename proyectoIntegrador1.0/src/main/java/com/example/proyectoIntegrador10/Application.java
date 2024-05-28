package com.example.proyectoIntegrador10;

import com.example.proyectoIntegrador10.dao.BD;
import com.example.proyectoIntegrador10.dao.OdontologoDaoH2;
import com.example.proyectoIntegrador10.model.Odontologo;
import com.example.proyectoIntegrador10.service.OdontologoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		BD.crearTablas();
		Odontologo odontologo = new Odontologo(2525, "Juan", "Perez");
		Odontologo odontologo1 = new Odontologo(1525, "Perla", "Dominguez");
		OdontologoService odontologoService = new OdontologoService();
		odontologoService.guardarOdontologo(odontologo);
		odontologoService.guardarOdontologo(odontologo1);
		SpringApplication.run(Application.class, args);
	}
}
