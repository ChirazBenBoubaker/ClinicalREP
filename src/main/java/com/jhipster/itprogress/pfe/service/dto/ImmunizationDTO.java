package com.jhipster.itprogress.pfe.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.jhipster.itprogress.pfe.domain.Immunization} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImmunizationDTO implements Serializable {

    private Long id;

    private String immunization;

    private LocalDate date;

    private String patientUID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImmunization() {
        return immunization;
    }

    public void setImmunization(String immunization) {
        this.immunization = immunization;
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
        if (!(o instanceof ImmunizationDTO)) {
            return false;
        }

        ImmunizationDTO immunizationDTO = (ImmunizationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, immunizationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImmunizationDTO{" +
            "id=" + getId() +
            ", immunization='" + getImmunization() + "'" +
            ", date='" + getDate() + "'" +
            ", patientUID='" + getPatientUID() + "'" +
            "}";
    }
}
