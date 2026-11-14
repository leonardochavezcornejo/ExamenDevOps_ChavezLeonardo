/**
 * Autor: Henry Wong
 * GitHub: hwongu
 *
 * Esta clase contiene pruebas unitarias para el servicio AlumnoServiceImpl, que maneja
 * la lógica de negocio para la entidad Alumno. Se utiliza Mockito para simular el comportamiento
 * del repositorio IAlumnoRepo y verificar las operaciones CRUD y búsquedas personalizadas.
 */
package com.hwongu.unit.service;

import com.hwongu.model.Alumno;
import com.hwongu.repo.IAlumnoRepo;
import com.hwongu.service.impl.AlumnoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AlumnoServiceTest {

    private AlumnoServiceImpl service;

    @Mock
    private IAlumnoRepo repo;

    private Alumno ALUMNO_1;
    private Alumno ALUMNO_2;
    private Alumno ALUMNO_3;

    /**
     * Metodo de configuración inicial que se ejecuta antes de cada prueba.
     * Configura los objetos de prueba, simula respuestas del repositorio y asigna
     * la instancia del servicio a probar.
     */
    @BeforeEach
    public void init() {
        this.service = new AlumnoServiceImpl(repo);

        ALUMNO_1 = new Alumno(1, "Carlos", "Pérez", "12345678", "carlos.perez@correo.com", LocalDate.of(2000, 5, 10), true);
        ALUMNO_2 = new Alumno(2, "Carla", "Gómez", "87654321", "carla.gomez@correo.com", LocalDate.of(2001, 8, 20), true);
        ALUMNO_3 = new Alumno(3, "Carmen", "Ruiz", "11223344", "carmen.ruiz@correo.com", LocalDate.of(1999, 3, 15), true);

        List<Alumno> alumnos = List.of(ALUMNO_1, ALUMNO_2, ALUMNO_3);
        Mockito.when(repo.findAll()).thenReturn(alumnos);
        Mockito.when(repo.findById(any())).thenReturn(Optional.of(ALUMNO_1));
        Mockito.when(repo.save(any())).thenReturn(ALUMNO_1);
        Mockito.when(repo.findByNameLikeOrderByName(any())).thenReturn(alumnos);
        Mockito.when(repo.findAlumnoByNameEnabled(any(), any(Boolean.class))).thenReturn(alumnos);
    }

    /**
     * Prueba unitaria para el metodo save, que verifica que el alumno guardado
     * no sea nulo.
     */
    @Test
    void save() throws Exception {
        Alumno response = service.save(ALUMNO_1);
        assertNotNull(response);
    }

    /**
     * Prueba unitaria para el metodo update, que verifica que el alumno actualizado
     * no sea nulo.
     */
    @Test
    void update() throws Exception {
        Alumno response = service.update(ALUMNO_1, 1);
        assertNotNull(response);
    }

    /**
     * Prueba unitaria para el metodo delete, que verifica que el metodo deleteById
     * del repositorio se haya invocado una vez.
     */
    @Test
    void delete() throws Exception {
        service.delete(1);
        verify(repo, times(1)).deleteById(1);
    }

    /**
     * Prueba unitaria para el metodo readAll, que verifica que la cantidad de alumnos
     * leídos sea igual a 3.
     */
    @Test
    void readAllTest() throws Exception {
        List<Alumno> response = service.readAll();
        assertEquals(3, response.size());
    }

    /**
     * Prueba unitaria para el metodo readById, que verifica que el alumno leído
     * no sea nulo.
     */
    @Test
    void readByIdTest() throws Exception {
        final int id = 1;
        Alumno response = service.readById(id);
        assertNotNull(response);
    }

    /**
     * Prueba unitaria para el metodo findByNameLike, que verifica que la búsqueda
     * por nombre retorne resultados.
     */
    @Test
    void findByNameLikeTest() {
        List<Alumno> response = service.findByNameLike("Car");
        assertEquals(3, response.size());
    }

    /**
     * Prueba unitaria para el metodo findAlumnoByNameIsEnabled, que verifica que
     * la búsqueda de alumnos habilitados retorne resultados.
     */
    @Test
    void findAlumnoByNameIsEnabledTest() {
        List<Alumno> response = service.findAlumnoByNameIsEnabled("Car");
        assertEquals(3, response.size());
    }
}
