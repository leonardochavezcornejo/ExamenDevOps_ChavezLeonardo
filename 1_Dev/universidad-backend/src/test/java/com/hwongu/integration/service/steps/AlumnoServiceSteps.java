/**
 * Autor: Henry Wong
 * GitHub: hwongu
 *
 * Esta clase define los pasos de prueba de integracion para la entidad Alumno usando Cucumber.
 * Cada metodo representa un paso de una prueba BDD para verificar operaciones CRUD.
 */
package com.hwongu.integration.service.steps;

import com.hwongu.model.Alumno;
import com.hwongu.repo.IAlumnoRepo;
import com.hwongu.service.impl.AlumnoServiceImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AlumnoServiceSteps {

    @Autowired
    private AlumnoServiceImpl alumnoService;

    @Autowired
    private IAlumnoRepo alumnoRepo;

    private Alumno alumno;

    /**
     * Crea un nuevo alumno en memoria con el nombre especificado en el paso.
     *
     * @param name nombre del alumno.
     */
    @Given("a new alumno with name {string}")
    public void a_new_alumno_with_name(String name) {
        int suffix = ThreadLocalRandom.current().nextInt(10000000, 99999999);
        alumno = new Alumno();
        alumno.setName(name);
        alumno.setLastName("Test");
        alumno.setDni(String.valueOf(suffix));
        alumno.setEmail("alumno" + suffix + "@mail.com");
        alumno.setBirthDate(LocalDate.of(2000, 1, 1));
        alumno.setEnabled(true);
    }

    /**
     * Guarda el alumno previamente creado en base de datos usando el servicio.
     */
    @When("I save the alumno")
    public void i_save_the_alumno() {
        try {
            alumno = alumnoService.save(alumno);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Verifica que el alumno fue guardado con el nombre esperado.
     *
     * @param expectedName nombre esperado.
     */
    @Then("the alumno should be saved with name {string}")
    public void the_alumno_should_be_saved_with_name(String expectedName) {
        if (alumno.getIdAlumno() == null) {
            i_save_the_alumno();
        }
        List<Alumno> alumnos = alumnoRepo.findByNameLikeOrderByName("%" + expectedName + "%");
        assertFalse(alumnos.isEmpty());
        assertTrue(alumnos.stream().anyMatch(a -> expectedName.equals(a.getName())));
    }

    /**
     * Actualiza el nombre del alumno con un nuevo valor.
     *
     * @param updatedName nuevo nombre.
     */
    @When("I update the alumno name to {string}")
    public void i_update_the_alumno_name_to(String updatedName) {
        alumno.setName(updatedName);
        try {
            alumno = alumnoService.update(alumno, alumno.getIdAlumno());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Verifica que el alumno fue actualizado con el nuevo nombre.
     *
     * @param updatedName nombre actualizado esperado.
     */
    @Then("the alumno should be updated with the name {string}")
    public void the_alumno_should_be_updated_with_the_name(String updatedName) {
        Alumno updatedAlumno = alumnoRepo.findById(alumno.getIdAlumno()).orElse(null);
        assertNotNull(updatedAlumno, "The updated alumno was not found in the database.");
        assertEquals(updatedName, updatedAlumno.getName());
    }

    /**
     * Verifica que existen alumnos en el sistema.
     */
    @Given("there are alumnos in the system")
    public void there_are_alumnos_in_the_system() {
        if (alumnoRepo.findAll().isEmpty()) {
            a_new_alumno_with_name("Base");
            i_save_the_alumno();
        }
        assertFalse(alumnoRepo.findAll().isEmpty());
    }

    /**
     * Verifica que la lista de alumnos leida no este vacia.
     */
    @Then("I should see a non-empty list of alumnos")
    public void i_should_see_a_non_empty_list_of_alumnos() {
        try {
            assertFalse(alumnoService.readAll().isEmpty());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Elimina el alumno previamente cargado en memoria.
     */
    @When("I delete the alumno")
    public void i_delete_the_alumno() {
        try {
            alumnoService.delete(alumno.getIdAlumno());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Verifica que el alumno eliminado ya no exista en base de datos.
     */
    @Then("the alumno should no longer exist in the system")
    public void the_alumno_should_no_longer_exist_in_the_system() {
        assertFalse(alumnoRepo.existsById(alumno.getIdAlumno()));
    }
}
