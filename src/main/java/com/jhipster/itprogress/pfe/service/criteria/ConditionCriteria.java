package com.jhipster.itprogress.pfe.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.jhipster.itprogress.pfe.domain.Condition} entity. This class is used
 * in {@link com.jhipster.itprogress.pfe.web.rest.ConditionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /conditions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConditionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter conditionText;

    private LocalDateFilter conditionOnsetDates;

    private StringFilter patientUID;

    private Boolean distinct;

    public ConditionCriteria() {}

    public ConditionCriteria(ConditionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.conditionText = other.conditionText == null ? null : other.conditionText.copy();
        this.conditionOnsetDates = other.conditionOnsetDates == null ? null : other.conditionOnsetDates.copy();
        this.patientUID = other.patientUID == null ? null : other.patientUID.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ConditionCriteria copy() {
        return new ConditionCriteria(this);
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

    public StringFilter getConditionText() {
        return conditionText;
    }

    public StringFilter conditionText() {
        if (conditionText == null) {
            conditionText = new StringFilter();
        }
        return conditionText;
    }

    public void setConditionText(StringFilter conditionText) {
        this.conditionText = conditionText;
    }

    public LocalDateFilter getConditionOnsetDates() {
        return conditionOnsetDates;
    }

    public LocalDateFilter conditionOnsetDates() {
        if (conditionOnsetDates == null) {
            conditionOnsetDates = new LocalDateFilter();
        }
        return conditionOnsetDates;
    }

    public void setConditionOnsetDates(LocalDateFilter conditionOnsetDates) {
        this.conditionOnsetDates = conditionOnsetDates;
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
        final ConditionCriteria that = (ConditionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(conditionText, that.conditionText) &&
            Objects.equals(conditionOnsetDates, that.conditionOnsetDates) &&
            Objects.equals(patientUID, that.patientUID) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, conditionText, conditionOnsetDates, patientUID, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConditionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (conditionText != null ? "conditionText=" + conditionText + ", " : "") +
            (conditionOnsetDates != null ? "conditionOnsetDates=" + conditionOnsetDates + ", " : "") +
            (patientUID != null ? "patientUID=" + patientUID + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
