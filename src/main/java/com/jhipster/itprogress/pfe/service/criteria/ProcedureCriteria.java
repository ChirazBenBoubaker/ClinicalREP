package com.jhipster.itprogress.pfe.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.jhipster.itprogress.pfe.domain.Procedure} entity. This class is used
 * in {@link com.jhipster.itprogress.pfe.web.rest.ProcedureResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /procedures?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProcedureCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter procedureText;

    private LocalDateFilter date;

    private StringFilter patientUID;

    private Boolean distinct;

    public ProcedureCriteria() {}

    public ProcedureCriteria(ProcedureCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.procedureText = other.procedureText == null ? null : other.procedureText.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.patientUID = other.patientUID == null ? null : other.patientUID.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProcedureCriteria copy() {
        return new ProcedureCriteria(this);
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

    public StringFilter getProcedureText() {
        return procedureText;
    }

    public StringFilter procedureText() {
        if (procedureText == null) {
            procedureText = new StringFilter();
        }
        return procedureText;
    }

    public void setProcedureText(StringFilter procedureText) {
        this.procedureText = procedureText;
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
        final ProcedureCriteria that = (ProcedureCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(procedureText, that.procedureText) &&
            Objects.equals(date, that.date) &&
            Objects.equals(patientUID, that.patientUID) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, procedureText, date, patientUID, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcedureCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (procedureText != null ? "procedureText=" + procedureText + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (patientUID != null ? "patientUID=" + patientUID + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
