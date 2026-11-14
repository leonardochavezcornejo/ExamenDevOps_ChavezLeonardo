/**
 * Autor: Henry Wong
 * GitHub: hwongu
 *
 * Esta clase contiene pruebas unitarias para el controlador AlumnoController utilizando
 * MockMvc para simular solicitudes HTTP y verificar el comportamiento del controlador.
 * Se prueban operaciones de creacion, actualizacion, eliminacion y lectura de alumnos.
 */
package com.hwongu.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwongu.controller.AlumnoController;
import com.hwongu.dto.AlumnoDTO;
import com.hwongu.model.Alumno;
import com.hwongu.service.IAlumnoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlumnoController.class)
@Import(AlumnoControllerTest.TestConfig.class)
public class AlumnoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAlumnoService alumnoService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    Alumno ALUMNO_1 = new Alumno(1, "Carlos", "Perez", "12345678", "carlos.perez@correo.com", LocalDate.of(2000, 5, 10), true);
    Alumno ALUMNO_2 = new Alumno(2, "Carla", "Gomez", "87654321", "carla.gomez@correo.com", LocalDate.of(2001, 8, 20), true);
    Alumno ALUMNO_3 = new Alumno(3, "Carmen", "Ruiz", "11223344", "carmen.ruiz@correo.com", LocalDate.of(1999, 3, 15), true);
    AlumnoDTO ALUMNODTO_1 = new AlumnoDTO(1, "Carlos", "Perez", "12345678", "carlos.perez@correo.com", LocalDate.of(2000, 5, 10), true);
    AlumnoDTO ALUMNODTO_2 = new AlumnoDTO(2, "Carla", "Gomez", "87654321", "carla.gomez@correo.com", LocalDate.of(2001, 8, 20), true);
    AlumnoDTO ALUMNODTO_3 = new AlumnoDTO(3, "Carmen", "Ruiz", "11223344", "carmen.ruiz@correo.com", LocalDate.of(1999, 3, 15), true);

    @TestConfiguration
    static class TestConfig {
        @Bean(name = "modelMapper")
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }
    }

    @Test
    void createTest() throws Exception {
        Mockito.when(alumnoService.save(any())).thenReturn(ALUMNO_3);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/alumnos/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ALUMNODTO_3));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nameAlumno", is("Carmen")))
                .andExpect(jsonPath("$.enabledAlumno", is(true)));
    }

    @Test
    void updateTest() throws Exception {
        int ID = 2;

        Mockito.when(alumnoService.update(any(), any())).thenReturn(ALUMNO_2);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/alumnos/update/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ALUMNODTO_2));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameAlumno", is("Carla")))
                .andExpect(jsonPath("$.enabledAlumno", is(true)));
    }

    @Test
    public void deleteTest() throws Exception {
        final int ID_ALUMNO = 1;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/alumnos/delete/" + ID_ALUMNO)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    /**
     * Prueba unitaria para la lectura de un alumno por su ID mediante una solicitud GET.
     * Verifica que la respuesta tenga un estado HTTP 200 (OK) y que el nombre del alumno
     * leido sea correcto.
     */
    @Test
    void readById() throws Exception {
        final int ID = 1;

        Mockito.when(alumnoService.readById(any())).thenReturn(ALUMNO_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/alumnos/readById/" + ID)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameAlumno", is("Carlos")));
    }
}
