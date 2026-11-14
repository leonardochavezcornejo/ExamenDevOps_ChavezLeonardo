package com.hwongu.controller;

import com.hwongu.dto.AlumnoDTO;
import com.hwongu.model.Alumno;
import com.hwongu.service.IAlumnoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    private final IAlumnoService alumnoService;
    private final ModelMapper modelMapper;

    public AlumnoController(IAlumnoService alumnoService, @Qualifier("modelMapper") ModelMapper modelMapper) {
        this.alumnoService = alumnoService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<AlumnoDTO> createAlumno(@Valid @RequestBody AlumnoDTO dto) throws Exception{
        Alumno objectCreated = this.alumnoService.save(this.convertToEntity(dto));
        AlumnoDTO object = this.convertToDto(objectCreated);
        return new ResponseEntity<>(object, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AlumnoDTO> updateAlumno(@PathVariable("id") Integer id, @Valid @RequestBody AlumnoDTO dto) throws Exception{
        dto.setIdAlumno(id);
        Alumno objectUpdated = this.alumnoService.update(this.convertToEntity(dto), id);
        AlumnoDTO object = this.convertToDto(objectUpdated);
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        this.alumnoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/readAll")
    public ResponseEntity<List<AlumnoDTO>> readAll() throws Exception{
        List<AlumnoDTO> list = this.alumnoService.readAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/readById/{id}")
    public ResponseEntity<AlumnoDTO> readById(@PathVariable("id") Integer id) throws Exception{
        AlumnoDTO object = this.convertToDto(this.alumnoService.readById(id));
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    @GetMapping("/find/byName")
    public ResponseEntity<List<AlumnoDTO>> findByName(@RequestParam("name") String name) throws Exception{
        List<AlumnoDTO> list = this.alumnoService.findByNameLike(name).stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/find/byNameIsEnabled")
    public ResponseEntity<List<AlumnoDTO>> findAlumnoByNameIsEnabled(@RequestParam("name") String name) throws Exception{
        List<AlumnoDTO> list = this.alumnoService.findAlumnoByNameIsEnabled(name).stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private AlumnoDTO convertToDto(Alumno obj){
        return this.modelMapper.map(obj, AlumnoDTO.class);
    }

    private Alumno convertToEntity(AlumnoDTO dto){
        return this.modelMapper.map(dto, Alumno.class);
    }
}
