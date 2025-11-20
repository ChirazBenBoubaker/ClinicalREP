package com.jhipster.itprogress.pfe.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.jhipster.itprogress.pfe.domain.Procedure} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProcedureDTO implements Serializable {

    private Long id;

    private String procedureText;

    private LocalDate date;

    private String patientUID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcedureText() {
        return procedureText;
    }

    public void setProcedureText(String procedureText) {
        this.procedureText = procedureText;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPatientUID() {
        return patientUID;
    }

    public void setPatientUID(String patientUID) {
        this.patientUID = patientUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcedureDTO)) {
            return false;
        }

        ProcedureDTO procedureDTO = (ProcedureDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, procedureDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcedureDTO{" +
            "id=" + getId() +
            ", procedureText='" + getProcedureText() + "'" +
            ", date='" + getDate() + "'" +
            ", patientUID='" + getPatientUID() + "'" +
            "}";
    }
}
