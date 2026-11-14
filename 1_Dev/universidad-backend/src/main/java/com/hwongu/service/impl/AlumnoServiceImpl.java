package com.hwongu.service.impl;

import com.hwongu.model.Alumno;
import com.hwongu.repo.IAlumnoRepo;
import com.hwongu.repo.IPostgreRepo;
import com.hwongu.service.IAlumnoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoServiceImpl extends CRUDImpl<Alumno, Integer> implements IAlumnoService {

    private final IAlumnoRepo alumnoRepository;

    public AlumnoServiceImpl(IAlumnoRepo alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    @Override
    protected IPostgreRepo<Alumno, Integer> getPostgreRepo() {
        return this.alumnoRepository;
    }

    @Override
    public List<Alumno> findByNameLike(String alumnoName) {
        return this.alumnoRepository.findByNameLikeOrderByName("%" + alumnoName + "%");
    }

    @Override
    public List<Alumno> findAlumnoByNameIsEnabled(String alumnoName) {
        return this.alumnoRepository.findAlumnoByNameEnabled("%" + alumnoName + "%", true);
    }
}
