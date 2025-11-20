package com.jhipster.itprogress.pfe.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Factclinical.
 */
@Entity
@Table(name = "factclinical")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Factclinical implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "patient_uid")
    private String patientUID;

    @Column(name = "encounter_id")
    private Integer encounterID;

    @Column(name = "observation_id")
    private Integer observationID;

    @Column(name = "procedure_id")
    private Integer procedureID;

    @Column(name = "immunization_id")
    private Integer immunizationID;

    @Column(name = "medication_id")
    private Integer medicationID;

    @Column(name = "condition_id")
    private Integer conditionID;

    @Column(name = "date")
    private LocalDate date;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Factclinical id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientUID() {
        return this.patientUID;
    }

    public Factclinical patientUID(String patientUID) {
        this.setPatientUID(patientUID);
        return this;
    }

    public void setPatientUID(String patientUID) {
        this.patientUID = patientUID;
    }

    public Integer getEncounterID() {
        return this.encounterID;
    }

    public Factclinical encounterID(Integer encounterID) {
        this.setEncounterID(encounterID);
        return this;
    }

    public void setEncounterID(Integer encounterID) {
        this.encounterID = encounterID;
    }

    public Integer getObservationID() {
        return this.observationID;
    }

    public Factclinical observationID(Integer observationID) {
        this.setObservationID(observationID);
        return this;
    }

    public void setObservationID(Integer observationID) {
        this.observationID = observationID;
    }

    public Integer getProcedureID() {
        return this.procedureID;
    }

    public Factclinical procedureID(Integer procedureID) {
        this.setProcedureID(procedureID);
        return this;
    }

    public void setProcedureID(Integer procedureID) {
        this.procedureID = procedureID;
    }

    public Integer getImmunizationID() {
        return this.immunizationID;
    }

    public Factclinical immunizationID(Integer immunizationID) {
        this.setImmunizationID(immunizationID);
        return this;
    }

    public void setImmunizationID(Integer immunizationID) {
        this.immunizationID = immunizationID;
    }

    public Integer getMedicationID() {
        return this.medicationID;
    }

    public Factclinical medicationID(Integer medicationID) {
        this.setMedicationID(medicationID);
        return this;
    }

    public void setMedicationID(Integer medicationID) {
        this.medicationID = medicationID;
    }

    public Integer getConditionID() {
        return this.conditionID;
    }

    public Factclinical conditionID(Integer conditionID) {
        this.setConditionID(conditionID);
        return this;
    }

    public void setConditionID(Integer conditionID) {
        this.conditionID = conditionID;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Factclinical date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Factclinical)) {
            return false;
        }
        return id != null && id.equals(((Factclinical) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Factclinical{" +
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
