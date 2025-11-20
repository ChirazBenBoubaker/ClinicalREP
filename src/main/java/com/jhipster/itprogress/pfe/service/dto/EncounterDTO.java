package com.jhipster.itprogress.pfe.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.jhipster.itprogress.pfe.domain.Encounter} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EncounterDTO implements Serializable {

    private Long id;

    private String encountersText;

    private String encounterLocation;

    private String encounterProvider;

    private LocalDate date;

    private String patientUID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEncountersText() {
        return encountersText;
    }

    public void setEncountersText(String encountersText) {
        this.encountersText = encountersText;
    }

    public String getEncounterLocation() {
        return encounterLocation;
    }

    public void setEncounterLocation(String encounterLocation) {
        this.encounterLocation = encounterLocation;
    }

    public String getEncounterProvider() {
        return encounterProvider;
    }

    public void setEncounterProvider(String encounterProvider) {
        this.encounterProvider = encounterProvider;
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
        if (!(o instanceof EncounterDTO)) {
            return false;
        }

        EncounterDTO encounterDTO = (EncounterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, encounterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EncounterDTO{" +
            "id=" + getId() +
            ", encountersText='" + getEncountersText() + "'" +
            ", encounterLocation='" + getEncounterLocation() + "'" +
            ", encounterProvider='" + getEncounterProvider() + "'" +
            ", date='" + getDate() + "'" +
            ", patientUID='" + getPatientUID() + "'" +
            "}";
    }
}
