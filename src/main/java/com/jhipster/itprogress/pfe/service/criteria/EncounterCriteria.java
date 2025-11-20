package com.jhipster.itprogress.pfe.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.jhipster.itprogress.pfe.domain.Encounter} entity. This class is used
 * in {@link com.jhipster.itprogress.pfe.web.rest.EncounterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /encounters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EncounterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter encountersText;

    private StringFilter encounterLocation;

    private StringFilter encounterProvider;

    private LocalDateFilter date;

    private StringFilter patientUID;

    private Boolean distinct;

    public EncounterCriteria() {}

    public EncounterCriteria(EncounterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.encountersText = other.encountersText == null ? null : other.encountersText.copy();
        this.encounterLocation = other.encounterLocation == null ? null : other.encounterLocation.copy();
        this.encounterProvider = other.encounterProvider == null ? null : other.encounterProvider.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.patientUID = other.patientUID == null ? null : other.patientUID.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EncounterCriteria copy() {
        return new EncounterCriteria(this);
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

    public StringFilter getEncountersText() {
        return encountersText;
    }

    public StringFilter encountersText() {
        if (encountersText == null) {
            encountersText = new StringFilter();
        }
        return encountersText;
    }

    public void setEncountersText(StringFilter encountersText) {
        this.encountersText = encountersText;
    }

    public StringFilter getEncounterLocation() {
        return encounterLocation;
    }

    public StringFilter encounterLocation() {
        if (encounterLocation == null) {
            encounterLocation = new StringFilter();
        }
        return encounterLocation;
    }

    public void setEncounterLocation(StringFilter encounterLocation) {
        this.encounterLocation = encounterLocation;
    }

    public StringFilter getEncounterProvider() {
        return encounterProvider;
    }

    public StringFilter encounterProvider() {
        if (encounterProvider == null) {
            encounterProvider = new StringFilter();
        }
        return encounterProvider;
    }

    public void setEncounterProvider(StringFilter encounterProvider) {
        this.encounterProvider = encounterProvider;
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
        final EncounterCriteria that = (EncounterCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(encountersText, that.encountersText) &&
            Objects.equals(encounterLocation, that.encounterLocation) &&
            Objects.equals(encounterProvider, that.encounterProvider) &&
            Objects.equals(date, that.date) &&
            Objects.equals(patientUID, that.patientUID) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, encountersText, encounterLocation, encounterProvider, date, patientUID, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EncounterCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (encountersText != null ? "encountersText=" + encountersText + ", " : "") +
            (encounterLocation != null ? "encounterLocation=" + encounterLocation + ", " : "") +
            (encounterProvider != null ? "encounterProvider=" + encounterProvider + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (patientUID != null ? "patientUID=" + patientUID + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
