package com.jhipster.itprogress.pfe.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.jhipster.itprogress.pfe.domain.Medication} entity. This class is used
 * in {@link com.jhipster.itprogress.pfe.web.rest.MedicationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /medications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MedicationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter medicationText;

    private LocalDateFilter date;

    private StringFilter patientUID;

    private Boolean distinct;

    public MedicationCriteria() {}

    public MedicationCriteria(MedicationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.medicationText = other.medicationText == null ? null : other.medicationText.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.patientUID = other.patientUID == null ? null : other.patientUID.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MedicationCriteria copy() {
        return new MedicationCriteria(this);
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

    public StringFilter getMedicationText() {
        return medicationText;
    }

    public StringFilter medicationText() {
        if (medicationText == null) {
            medicationText = new StringFilter();
        }
        return medicationText;
    }

    public void setMedicationText(StringFilter medicationText) {
        this.medicationText = medicationText;
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
        final MedicationCriteria that = (MedicationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(medicationText, that.medicationText) &&
            Objects.equals(date, that.date) &&
            Objects.equals(patientUID, that.patientUID) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, medicationText, date, patientUID, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (medicationText != null ? "medicationText=" + medicationText + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (patientUID != null ? "patientUID=" + patientUID + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
