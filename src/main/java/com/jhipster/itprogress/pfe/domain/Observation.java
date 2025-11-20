package com.jhipster.itprogress.pfe.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Observation.
 */
@Entity
@Table(name = "observation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Observation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "body_height")
    private Float bodyHeight;

    @Column(name = "body_weight")
    private Float bodyWeight;

    @Column(name = "body_mass")
    private Float bodyMass;

    @Column(name = "painseverity")
    private Float painseverity;

    @Column(name = "blood_pressure")
    private Float bloodPressure;

    @Column(name = "tobaccosmokingstatus_nhis")
    private Float tobaccosmokingstatusNHIS;

    @Column(name = "creatinine")
    private Float creatinine;

    @Column(name = "calcium")
    private Float calcium;

    @Column(name = "sodium")
    private Float sodium;

    @Column(name = "potassium")
    private Float potassium;

    @Column(name = "chloride")
    private Float chloride;

    @Column(name = "carbon_dioxide")
    private Float carbonDioxide;

    @Column(name = "glucose")
    private Float glucose;

    @Column(name = "urea_nitrogen")
    private Float ureaNitrogen;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "patient_uid")
    private String patientUID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Observation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getBodyHeight() {
        return this.bodyHeight;
    }

    public Observation bodyHeight(Float bodyHeight) {
        this.setBodyHeight(bodyHeight);
        return this;
    }

    public void setBodyHeight(Float bodyHeight) {
        this.bodyHeight = bodyHeight;
    }

    public Float getBodyWeight() {
        return this.bodyWeight;
    }

    public Observation bodyWeight(Float bodyWeight) {
        this.setBodyWeight(bodyWeight);
        return this;
    }

    public void setBodyWeight(Float bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public Float getBodyMass() {
        return this.bodyMass;
    }

    public Observation bodyMass(Float bodyMass) {
        this.setBodyMass(bodyMass);
        return this;
    }

    public void setBodyMass(Float bodyMass) {
        this.bodyMass = bodyMass;
    }

    public Float getPainseverity() {
        return this.painseverity;
    }

    public Observation painseverity(Float painseverity) {
        this.setPainseverity(painseverity);
        return this;
    }

    public void setPainseverity(Float painseverity) {
        this.painseverity = painseverity;
    }

    public Float getBloodPressure() {
        return this.bloodPressure;
    }

    public Observation bloodPressure(Float bloodPressure) {
        this.setBloodPressure(bloodPressure);
        return this;
    }

    public void setBloodPressure(Float bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public Float getTobaccosmokingstatusNHIS() {
        return this.tobaccosmokingstatusNHIS;
    }

    public Observation tobaccosmokingstatusNHIS(Float tobaccosmokingstatusNHIS) {
        this.setTobaccosmokingstatusNHIS(tobaccosmokingstatusNHIS);
        return this;
    }

    public void setTobaccosmokingstatusNHIS(Float tobaccosmokingstatusNHIS) {
        this.tobaccosmokingstatusNHIS = tobaccosmokingstatusNHIS;
    }

    public Float getCreatinine() {
        return this.creatinine;
    }

    public Observation creatinine(Float creatinine) {
        this.setCreatinine(creatinine);
        return this;
    }

    public void setCreatinine(Float creatinine) {
        this.creatinine = creatinine;
    }

    public Float getCalcium() {
        return this.calcium;
    }

    public Observation calcium(Float calcium) {
        this.setCalcium(calcium);
        return this;
    }

    public void setCalcium(Float calcium) {
        this.calcium = calcium;
    }

    public Float getSodium() {
        return this.sodium;
    }

    public Observation sodium(Float sodium) {
        this.setSodium(sodium);
        return this;
    }

    public void setSodium(Float sodium) {
        this.sodium = sodium;
    }

    public Float getPotassium() {
        return this.potassium;
    }

    public Observation potassium(Float potassium) {
        this.setPotassium(potassium);
        return this;
    }

    public void setPotassium(Float potassium) {
        this.potassium = potassium;
    }

    public Float getChloride() {
        return this.chloride;
    }

    public Observation chloride(Float chloride) {
        this.setChloride(chloride);
        return this;
    }

    public void setChloride(Float chloride) {
        this.chloride = chloride;
    }

    public Float getCarbonDioxide() {
        return this.carbonDioxide;
    }

    public Observation carbonDioxide(Float carbonDioxide) {
        this.setCarbonDioxide(carbonDioxide);
        return this;
    }

    public void setCarbonDioxide(Float carbonDioxide) {
        this.carbonDioxide = carbonDioxide;
    }

    public Float getGlucose() {
        return this.glucose;
    }

    public Observation glucose(Float glucose) {
        this.setGlucose(glucose);
        return this;
    }

    public void setGlucose(Float glucose) {
        this.glucose = glucose;
    }

    public Float getUreaNitrogen() {
        return this.ureaNitrogen;
    }

    public Observation ureaNitrogen(Float ureaNitrogen) {
        this.setUreaNitrogen(ureaNitrogen);
        return this;
    }

    public void setUreaNitrogen(Float ureaNitrogen) {
        this.ureaNitrogen = ureaNitrogen;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Observation date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPatientUID() {
        return this.patientUID;
    }

    public Observation patientUID(String patientUID) {
        this.setPatientUID(patientUID);
        return this;
    }

    public void setPatientUID(String patientUID) {
        this.patientUID = patientUID;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Observation)) {
            return false;
        }
        return id != null && id.equals(((Observation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Observation{" +
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
