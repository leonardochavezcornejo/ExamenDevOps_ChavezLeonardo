package com.hwongu.service;

import com.hwongu.model.Alumno;

import java.util.List;

public interface IAlumnoService extends ICRUDService<Alumno, Integer> {

    List<Alumno> findByNameLike(String alumnoName);

    List<Alumno> findAlumnoByNameIsEnabled(String alumnoName);
}
