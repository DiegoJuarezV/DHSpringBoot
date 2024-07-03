package com.example.proyectoIntegrador11;

import com.example.proyectoIntegrador11.dto.TurnoDTO;
import com.example.proyectoIntegrador11.entity.*;
import com.example.proyectoIntegrador11.service.OdontologoService;
import com.example.proyectoIntegrador11.service.PacienteService;
import com.example.proyectoIntegrador11.service.TurnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
    @AutoConfigureMockMvc(addFilters = false)
    public class IntergracionTurnosTest {
        @Autowired
        private TurnoService turnoService;
        @Autowired
        private PacienteService pacienteService;
        @Autowired
        private OdontologoService odontologoService;
        @Autowired
        private MockMvc mockMvc;

        public void cargarDatos(){
            Paciente pacienteGuardado= pacienteService.guardarPaciente(new Paciente("Jorgito","Pereyra","111111", LocalDate.of(2024,6,19),new Domicilio("Calle falsa",123,"La Rioja","Argentina"),"jorgito@digitalhouse.com"));
            Odontologo odontologoGuardado= odontologoService.guardarOdontologo(new Odontologo(125L,"Ivan","Bustamante"));
            Turno turnoGuardado = turnoService.registrarTurno(new TurnoDTO(LocalDate.of(2024,10,25), pacienteGuardado, odontologoGuardado));

            Paciente pacienteGuardado2= pacienteService.guardarPaciente(new Paciente("Daniela","Martinez","222222", LocalDate.of(2024,8,25),new Domicilio("Calle falsa2",12,"La Paz","Bolivia"),"daniela@digitalhouse.com"));
            Turno turnoGuardado2= turnoService.registrarTurno(new TurnoDTO(LocalDate.of(2024,12,10), pacienteGuardado2,odontologoGuardado));
        }

        @Test
        public void listarTodosLosTurnos() throws Exception{
            cargarDatos();
            MvcResult respuesta= mockMvc.perform(MockMvcRequestBuilders.get("/turnos").accept(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            assertFalse(respuesta.getResponse().getContentAsString().isEmpty());
        }
}
