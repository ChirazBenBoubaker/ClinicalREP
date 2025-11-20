package com.jhipster.itprogress.pfe.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.jhipster.itprogress.pfe.domain.Factclinical} entity. This class is used
 * in {@link com.jhipster.itprogress.pfe.web.rest.FactclinicalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /factclinicals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FactclinicalCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter patientUID;

    private IntegerFilter encounterID;

    private IntegerFilter observationID;

    private IntegerFilter procedureID;

    private IntegerFilter immunizationID;

    private IntegerFilter medicationID;

    private IntegerFilter conditionID;

    private LocalDateFilter date;

    private Boolean distinct;

    public FactclinicalCriteria() {}

    public FactclinicalCriteria(FactclinicalCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.patientUID = other.patientUID == null ? null : other.patientUID.copy();
        this.encounterID = other.encounterID == null ? null : other.encounterID.copy();
        this.observationID = other.observationID == null ? null : other.observationID.copy();
        this.procedureID = other.procedureID == null ? null : other.procedureID.copy();
        this.immunizationID = other.immunizationID == null ? null : other.immunizationID.copy();
        this.medicationID = other.medicationID == null ? null : other.medicationID.copy();
        this.conditionID = other.conditionID == null ? null : other.conditionID.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FactclinicalCriteria copy() {
        return new FactclinicalCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPatientUID() {
        return patientUID;
    }

    public StringFilter patientUID() {
        if (patientUID == null) {
            patientUID = new StringFilter();
        }
        return patientUID;
    }

    public void setPatientUID(StringFilter patientUID) {
        this.patientUID = patientUID;
    }

    public IntegerFilter getEncounterID() {
        return encounterID;
    }

    public IntegerFilter encounterID() {
        if (encounterID == null) {
            encounterID = new IntegerFilter();
        }
        return encounterID;
    }

    public void setEncounterID(IntegerFilter encounterID) {
        this.encounterID = encounterID;
    }

    public IntegerFilter getObservationID() {
        return observationID;
    }

    public IntegerFilter observationID() {
        if (observationID == null) {
            observationID = new IntegerFilter();
        }
        return observationID;
    }

    public void setObservationID(IntegerFilter observationID) {
        this.observationID = observationID;
    }

    public IntegerFilter getProcedureID() {
        return procedureID;
    }

    public IntegerFilter procedureID() {
        if (procedureID == null) {
            procedureID = new IntegerFilter();
        }
        return procedureID;
    }

    public void setProcedureID(IntegerFilter procedureID) {
        this.procedureID = procedureID;
    }

    public IntegerFilter getImmunizationID() {
        return immunizationID;
    }

    public IntegerFilter immunizationID() {
        if (immunizationID == null) {
            immunizationID = new IntegerFilter();
        }
        return immunizationID;
    }

    public void setImmunizationID(IntegerFilter immunizationID) {
        this.immunizationID = immunizationID;
    }

    public IntegerFilter getMedicationID() {
        return medicationID;
    }

    public IntegerFilter medicationID() {
        if (medicationID == null) {
            medicationID = new IntegerFilter();
        }
        return medicationID;
    }

    public void setMedicationID(IntegerFilter medicationID) {
        this.medicationID = medicationID;
    }

    public IntegerFilter getConditionID() {
        return conditionID;
    }

    public IntegerFilter conditionID() {
        if (conditionID == null) {
            conditionID = new IntegerFilter();
        }
        return conditionID;
    }

    public void setConditionID(IntegerFilter conditionID) {
        this.conditionID = conditionID;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public LocalDateFilter date() {
        if (date == null) {
            date = new LocalDateFilter();
        }
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FactclinicalCriteria that = (FactclinicalCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(patientUID, that.patientUID) &&
            Objects.equals(encounterID, that.encounterID) &&
            Objects.equals(observationID, that.observationID) &&
            Objects.equals(procedureID, that.procedureID) &&
            Objects.equals(immunizationID, that.immunizationID) &&
            Objects.equals(medicationID, that.medicationID) &&
            Objects.equals(conditionID, that.conditionID) &&
            Objects.equals(date, that.date) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            patientUID,
            encounterID,
            observationID,
            procedureID,
            immunizationID,
            medicationID,
            conditionID,
            date,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactclinicalCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (patientUID != null ? "patientUID=" + patientUID + ", " : "") +
            (encounterID != null ? "encounterID=" + encounterID + ", " : "") +
            (observationID != null ? "observationID=" + observationID + ", " : "") +
            (procedureID != null ? "procedureID=" + procedureID + ", " : "") +
            (immunizationID != null ? "immunizationID=" + immunizationID + ", " : "") +
            (medicationID != null ? "medicationID=" + medicationID + ", " : "") +
            (conditionID != null ? "conditionID=" + conditionID + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
