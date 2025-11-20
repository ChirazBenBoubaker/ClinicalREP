package com.jhipster.itprogress.pfe.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.jhipster.itprogress.pfe.domain.Observation} entity. This class is used
 * in {@link com.jhipster.itprogress.pfe.web.rest.ObservationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /observations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ObservationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FloatFilter bodyHeight;

    private FloatFilter bodyWeight;

    private FloatFilter bodyMass;

    private FloatFilter painseverity;

    private FloatFilter bloodPressure;

    private FloatFilter tobaccosmokingstatusNHIS;

    private FloatFilter creatinine;

    private FloatFilter calcium;

    private FloatFilter sodium;

    private FloatFilter potassium;

    private FloatFilter chloride;

    private FloatFilter carbonDioxide;

    private FloatFilter glucose;

    private FloatFilter ureaNitrogen;

    private LocalDateFilter date;

    private StringFilter patientUID;

    private Boolean distinct;

    public ObservationCriteria() {}

    public ObservationCriteria(ObservationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.bodyHeight = other.bodyHeight == null ? null : other.bodyHeight.copy();
        this.bodyWeight = other.bodyWeight == null ? null : other.bodyWeight.copy();
        this.bodyMass = other.bodyMass == null ? null : other.bodyMass.copy();
        this.painseverity = other.painseverity == null ? null : other.painseverity.copy();
        this.bloodPressure = other.bloodPressure == null ? null : other.bloodPressure.copy();
        this.tobaccosmokingstatusNHIS = other.tobaccosmokingstatusNHIS == null ? null : other.tobaccosmokingstatusNHIS.copy();
        this.creatinine = other.creatinine == null ? null : other.creatinine.copy();
        this.calcium = other.calcium == null ? null : other.calcium.copy();
        this.sodium = other.sodium == null ? null : other.sodium.copy();
        this.potassium = other.potassium == null ? null : other.potassium.copy();
        this.chloride = other.chloride == null ? null : other.chloride.copy();
        this.carbonDioxide = other.carbonDioxide == null ? null : other.carbonDioxide.copy();
        this.glucose = other.glucose == null ? null : other.glucose.copy();
        this.ureaNitrogen = other.ureaNitrogen == null ? null : other.ureaNitrogen.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.patientUID = other.patientUID == null ? null : other.patientUID.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ObservationCriteria copy() {
        return new ObservationCriteria(this);
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

    public FloatFilter getBodyHeight() {
        return bodyHeight;
    }

    public FloatFilter bodyHeight() {
        if (bodyHeight == null) {
            bodyHeight = new FloatFilter();
        }
        return bodyHeight;
    }

    public void setBodyHeight(FloatFilter bodyHeight) {
        this.bodyHeight = bodyHeight;
    }

    public FloatFilter getBodyWeight() {
        return bodyWeight;
    }

    public FloatFilter bodyWeight() {
        if (bodyWeight == null) {
            bodyWeight = new FloatFilter();
        }
        return bodyWeight;
    }

    public void setBodyWeight(FloatFilter bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public FloatFilter getBodyMass() {
        return bodyMass;
    }

    public FloatFilter bodyMass() {
        if (bodyMass == null) {
            bodyMass = new FloatFilter();
        }
        return bodyMass;
    }

    public void setBodyMass(FloatFilter bodyMass) {
        this.bodyMass = bodyMass;
    }

    public FloatFilter getPainseverity() {
        return painseverity;
    }

    public FloatFilter painseverity() {
        if (painseverity == null) {
            painseverity = new FloatFilter();
        }
        return painseverity;
    }

    public void setPainseverity(FloatFilter painseverity) {
        this.painseverity = painseverity;
    }

    public FloatFilter getBloodPressure() {
        return bloodPressure;
    }

    public FloatFilter bloodPressure() {
        if (bloodPressure == null) {
            bloodPressure = new FloatFilter();
        }
        return bloodPressure;
    }

    public void setBloodPressure(FloatFilter bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public FloatFilter getTobaccosmokingstatusNHIS() {
        return tobaccosmokingstatusNHIS;
    }

    public FloatFilter tobaccosmokingstatusNHIS() {
        if (tobaccosmokingstatusNHIS == null) {
            tobaccosmokingstatusNHIS = new FloatFilter();
        }
        return tobaccosmokingstatusNHIS;
    }

    public void setTobaccosmokingstatusNHIS(FloatFilter tobaccosmokingstatusNHIS) {
        this.tobaccosmokingstatusNHIS = tobaccosmokingstatusNHIS;
    }

    public FloatFilter getCreatinine() {
        return creatinine;
    }

    public FloatFilter creatinine() {
        if (creatinine == null) {
            creatinine = new FloatFilter();
        }
        return creatinine;
    }

    public void setCreatinine(FloatFilter creatinine) {
        this.creatinine = creatinine;
    }

    public FloatFilter getCalcium() {
        return calcium;
    }

    public FloatFilter calcium() {
        if (calcium == null) {
            calcium = new FloatFilter();
        }
        return calcium;
    }

    public void setCalcium(FloatFilter calcium) {
        this.calcium = calcium;
    }

    public FloatFilter getSodium() {
        return sodium;
    }

    public FloatFilter sodium() {
        if (sodium == null) {
            sodium = new FloatFilter();
        }
        return sodium;
    }

    public void setSodium(FloatFilter sodium) {
        this.sodium = sodium;
    }

    public FloatFilter getPotassium() {
        return potassium;
    }

    public FloatFilter potassium() {
        if (potassium == null) {
            potassium = new FloatFilter();
        }
        return potassium;
    }

    public void setPotassium(FloatFilter potassium) {
        this.potassium = potassium;
    }

    public FloatFilter getChloride() {
        return chloride;
    }

    public FloatFilter chloride() {
        if (chloride == null) {
            chloride = new FloatFilter();
        }
        return chloride;
    }

    public void setChloride(FloatFilter chloride) {
        this.chloride = chloride;
    }

    public FloatFilter getCarbonDioxide() {
        return carbonDioxide;
    }

    public FloatFilter carbonDioxide() {
        if (carbonDioxide == null) {
            carbonDioxide = new FloatFilter();
        }
        return carbonDioxide;
    }

    public void setCarbonDioxide(FloatFilter carbonDioxide) {
        this.carbonDioxide = carbonDioxide;
    }

    public FloatFilter getGlucose() {
        return glucose;
    }

    public FloatFilter glucose() {
        if (glucose == null) {
            glucose = new FloatFilter();
        }
        return glucose;
    }

    public void setGlucose(FloatFilter glucose) {
        this.glucose = glucose;
    }

    public FloatFilter getUreaNitrogen() {
        return ureaNitrogen;
    }

    public FloatFilter ureaNitrogen() {
        if (ureaNitrogen == null) {
            ureaNitrogen = new FloatFilter();
        }
        return ureaNitrogen;
    }

    public void setUreaNitrogen(FloatFilter ureaNitrogen) {
        this.ureaNitrogen = ureaNitrogen;
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
        final ObservationCriteria that = (ObservationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(bodyHeight, that.bodyHeight) &&
            Objects.equals(bodyWeight, that.bodyWeight) &&
            Objects.equals(bodyMass, that.bodyMass) &&
            Objects.equals(painseverity, that.painseverity) &&
            Objects.equals(bloodPressure, that.bloodPressure) &&
            Objects.equals(tobaccosmokingstatusNHIS, that.tobaccosmokingstatusNHIS) &&
            Objects.equals(creatinine, that.creatinine) &&
            Objects.equals(calcium, that.calcium) &&
            Objects.equals(sodium, that.sodium) &&
            Objects.equals(potassium, that.potassium) &&
            Objects.equals(chloride, that.chloride) &&
            Objects.equals(carbonDioxide, that.carbonDioxide) &&
            Objects.equals(glucose, that.glucose) &&
            Objects.equals(ureaNitrogen, that.ureaNitrogen) &&
            Objects.equals(date, that.date) &&
            Objects.equals(patientUID, that.patientUID) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            bodyHeight,
            bodyWeight,
            bodyMass,
            painseverity,
            bloodPressure,
            tobaccosmokingstatusNHIS,
            creatinine,
            calcium,
            sodium,
            potassium,
            chloride,
            carbonDioxide,
            glucose,
            ureaNitrogen,
            date,
            patientUID,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObservationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (bodyHeight != null ? "bodyHeight=" + bodyHeight + ", " : "") +
            (bodyWeight != null ? "bodyWeight=" + bodyWeight + ", " : "") +
            (bodyMass != null ? "bodyMass=" + bodyMass + ", " : "") +
            (painseverity != null ? "painseverity=" + painseverity + ", " : "") +
            (bloodPressure != null ? "bloodPressure=" + bloodPressure + ", " : "") +
            (tobaccosmokingstatusNHIS != null ? "tobaccosmokingstatusNHIS=" + tobaccosmokingstatusNHIS + ", " : "") +
            (creatinine != null ? "creatinine=" + creatinine + ", " : "") +
            (calcium != null ? "calcium=" + calcium + ", " : "") +
            (sodium != null ? "sodium=" + sodium + ", " : "") +
            (potassium != null ? "potassium=" + potassium + ", " : "") +
            (chloride != null ? "chloride=" + chloride + ", " : "") +
            (carbonDioxide != null ? "carbonDioxide=" + carbonDioxide + ", " : "") +
            (glucose != null ? "glucose=" + glucose + ", " : "") +
            (ureaNitrogen != null ? "ureaNitrogen=" + ureaNitrogen + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (patientUID != null ? "patientUID=" + patientUID + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
