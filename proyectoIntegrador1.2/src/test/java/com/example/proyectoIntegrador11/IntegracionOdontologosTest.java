package com.example.proyectoIntegrador11;

import com.example.proyectoIntegrador11.service.OdontologoService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IntegracionOdontologosTest {

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void guardarOdontologo() throws Exception {
        String odontologoJson = "{ \"numeroMatricula\": \"2525\", \"nombre\": \"Juan\", \"apellido\": \"Lopez\" }";
        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.post("/odontologos/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(odontologoJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertTrue(respuesta.getResponse().getContentAsString().contains("2525"));
        assertTrue(respuesta.getResponse().getContentAsString().contains("Juan"));
        assertTrue(respuesta.getResponse().getContentAsString().contains("Lopez"));
    }
}
