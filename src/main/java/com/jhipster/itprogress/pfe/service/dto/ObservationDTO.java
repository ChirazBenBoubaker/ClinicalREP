package com.jhipster.itprogress.pfe.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.jhipster.itprogress.pfe.domain.Observation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ObservationDTO implements Serializable {

    private Long id;

    private Float bodyHeight;

    private Float bodyWeight;

    private Float bodyMass;

    private Float painseverity;

    private Float bloodPressure;

    private Float tobaccosmokingstatusNHIS;

    private Float creatinine;

    private Float calcium;

    private Float sodium;

    private Float potassium;

    private Float chloride;

    private Float carbonDioxide;

    private Float glucose;

    private Float ureaNitrogen;

    private LocalDate date;

    private String patientUID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getBodyHeight() {
        return bodyHeight;
    }

    public void setBodyHeight(Float bodyHeight) {
        this.bodyHeight = bodyHeight;
    }

    public Float getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(Float bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public Float getBodyMass() {
        return bodyMass;
    }

    public void setBodyMass(Float bodyMass) {
        this.bodyMass = bodyMass;
    }

    public Float getPainseverity() {
        return painseverity;
    }

    public void setPainseverity(Float painseverity) {
        this.painseverity = painseverity;
    }

    public Float getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(Float bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public Float getTobaccosmokingstatusNHIS() {
        return tobaccosmokingstatusNHIS;
    }

    public void setTobaccosmokingstatusNHIS(Float tobaccosmokingstatusNHIS) {
        this.tobaccosmokingstatusNHIS = tobaccosmokingstatusNHIS;
    }

    public Float getCreatinine() {
        return creatinine;
    }

    public void setCreatinine(Float creatinine) {
        this.creatinine = creatinine;
    }

    public Float getCalcium() {
        return calcium;
    }

    public void setCalcium(Float calcium) {
        this.calcium = calcium;
    }

    public Float getSodium() {
        return sodium;
    }

    public void setSodium(Float sodium) {
        this.sodium = sodium;
    }

    public Float getPotassium() {
        return potassium;
    }

    public void setPotassium(Float potassium) {
        this.potassium = potassium;
    }

    public Float getChloride() {
        return chloride;
    }

    public void setChloride(Float chloride) {
        this.chloride = chloride;
    }

    public Float getCarbonDioxide() {
        return carbonDioxide;
    }

    public void setCarbonDioxide(Float carbonDioxide) {
        this.carbonDioxide = carbonDioxide;
    }

    public Float getGlucose() {
        return glucose;
    }

    public void setGlucose(Float glucose) {
        this.glucose = glucose;
    }

    public Float getUreaNitrogen() {
        return ureaNitrogen;
    }

    public void setUreaNitrogen(Float ureaNitrogen) {
        this.ureaNitrogen = ureaNitrogen;
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
        if (!(o instanceof ObservationDTO)) {
            return false;
        }

        ObservationDTO observationDTO = (ObservationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, observationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObservationDTO{" +
            "id=" + getId() +
            ", bodyHeight=" + getBodyHeight() +
            ", bodyWeight=" + getBodyWeight() +
            ", bodyMass=" + getBodyMass() +
            ", painseverity=" + getPainseverity() +
            ", bloodPressure=" + getBloodPressure() +
            ", tobaccosmokingstatusNHIS=" + getTobaccosmokingstatusNHIS() +
            ", creatinine=" + getCreatinine() +
            ", calcium=" + getCalcium() +
            ", sodium=" + getSodium() +
            ", potassium=" + getPotassium() +
            ", chloride=" + getChloride() +
            ", carbonDioxide=" + getCarbonDioxide() +
            ", glucose=" + getGlucose() +
            ", ureaNitrogen=" + getUreaNitrogen() +
            ", date='" + getDate() + "'" +
            ", patientUID='" + getPatientUID() + "'" +
            "}";
    }
}
