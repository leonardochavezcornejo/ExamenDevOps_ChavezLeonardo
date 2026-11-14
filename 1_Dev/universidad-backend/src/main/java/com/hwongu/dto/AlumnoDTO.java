package com.hwongu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlumnoDTO {

    private Integer idAlumno;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50, message = "{name.size}")
    private String nameAlumno;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 100, message = "{lastname.size}")
    private String lastNameAlumno;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[0-9]{8}$", message = "{dni.format}")
    private String dniAlumno;

    @NotNull
    @NotEmpty
    @Email(message = "{email.format}")
    @Size(min = 5, max = 120, message = "{email.size}")
    private String emailAlumno;

    @NotNull
    private LocalDate birthDateAlumno;

    @NotNull
    private boolean enabledAlumno;

    public AlumnoDTO() {
    }

    public AlumnoDTO(Integer idAlumno, String nameAlumno, String lastNameAlumno, String dniAlumno, String emailAlumno, LocalDate birthDateAlumno, boolean enabledAlumno) {
        this.idAlumno = idAlumno;
        this.nameAlumno = nameAlumno;
        this.lastNameAlumno = lastNameAlumno;
        this.dniAlumno = dniAlumno;
        this.emailAlumno = emailAlumno;
        this.birthDateAlumno = birthDateAlumno;
        this.enabledAlumno = enabledAlumno;
    }

    public Integer getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNameAlumno() {
        return nameAlumno;
    }

    public void setNameAlumno(String nameAlumno) {
        this.nameAlumno = nameAlumno;
    }

    public String getLastNameAlumno() {
        return lastNameAlumno;
    }

    public void setLastNameAlumno(String lastNameAlumno) {
        this.lastNameAlumno = lastNameAlumno;
    }

    public String getDniAlumno() {
        return dniAlumno;
    }

    public void setDniAlumno(String dniAlumno) {
        this.dniAlumno = dniAlumno;
    }

    public String getEmailAlumno() {
        return emailAlumno;
    }

    public void setEmailAlumno(String emailAlumno) {
        this.emailAlumno = emailAlumno;
    }

    public LocalDate getBirthDateAlumno() {
        return birthDateAlumno;
    }

    public void setBirthDateAlumno(LocalDate birthDateAlumno) {
        this.birthDateAlumno = birthDateAlumno;
    }

    public boolean isEnabledAlumno() {
        return enabledAlumno;
    }

    public void setEnabledAlumno(boolean enabledAlumno) {
        this.enabledAlumno = enabledAlumno;
    }
}
