package com.hwongu.repo;

import com.hwongu.model.Alumno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IAlumnoRepo extends IPostgreRepo<Alumno, Integer> {

    List<Alumno> findByNameLikeOrderByName(String alumnoName);

    @Query("FROM Alumno a where a.name like :alumnoName and a.enabled = :alumnoEnabled")
    List<Alumno> findAlumnoByNameEnabled(@Param("alumnoName") String alumnoName, @Param("alumnoEnabled") boolean alumnoEnabled);
}
