package com.jhipster.itprogress.pfe.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.jhipster.itprogress.pfe.domain.Factclinical} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FactclinicalDTO implements Serializable {

    private Long id;

    private String patientUID;

    private Integer encounterID;

    private Integer observationID;

    private Integer procedureID;

    private Integer immunizationID;

    private Integer medicationID;

    private Integer conditionID;

    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientUID() {
        return patientUID;
    }

    public void setPatientUID(String patientUID) {
        this.patientUID = patientUID;
    }

    public Integer getEncounterID() {
        return encounterID;
    }

    public void setEncounterID(Integer encounterID) {
        this.encounterID = encounterID;
    }

    public Integer getObservationID() {
        return observationID;
    }

    public void setObservationID(Integer observationID) {
        this.observationID = observationID;
    }

    public Integer getProcedureID() {
        return procedureID;
    }

    public void setProcedureID(Integer procedureID) {
        this.procedureID = procedureID;
    }

    public Integer getImmunizationID() {
        return immunizationID;
    }

    public void setImmunizationID(Integer immunizationID) {
        this.immunizationID = immunizationID;
    }

    public Integer getMedicationID() {
        return medicationID;
    }

    public void setMedicationID(Integer medicationID) {
        this.medicationID = medicationID;
    }

    public Integer getConditionID() {
        return conditionID;
    }

    public void setConditionID(Integer conditionID) {
        this.conditionID = conditionID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FactclinicalDTO)) {
            return false;
        }

        FactclinicalDTO factclinicalDTO = (FactclinicalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, factclinicalDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactclinicalDTO{" +
            "id=" + getId() +
            ", patientUID='" + getPatientUID() + "'" +
            ", encounterID=" + getEncounterID() +
            ", observationID=" + getObservationID() +
            ", procedureID=" + getProcedureID() +
            ", immunizationID=" + getImmunizationID() +
            ", medicationID=" + getMedicationID() +
            ", conditionID=" + getConditionID() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
