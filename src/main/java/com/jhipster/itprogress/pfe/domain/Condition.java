package com.jhipster.itprogress.pfe.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Condition.
 */
@Entity
@Table(name = "condition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Condition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "condition_text")
    private String conditionText;

    @Column(name = "condition_onset_dates")
    private LocalDate conditionOnsetDates;

    @Column(name = "patient_uid")
    private String patientUID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Condition id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConditionText() {
        return this.conditionText;
    }

    public Condition conditionText(String conditionText) {
        this.setConditionText(conditionText);
        return this;
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
    }

    public LocalDate getConditionOnsetDates() {
        return this.conditionOnsetDates;
    }

    public Condition conditionOnsetDates(LocalDate conditionOnsetDates) {
        this.setConditionOnsetDates(conditionOnsetDates);
        return this;
    }

    public void setConditionOnsetDates(LocalDate conditionOnsetDates) {
        this.conditionOnsetDates = conditionOnsetDates;
    }

    public String getPatientUID() {
        return this.patientUID;
    }

    public Condition patientUID(String patientUID) {
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
        if (!(o instanceof Condition)) {
            return false;
        }
        return id != null && id.equals(((Condition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Condition{" +
            "id=" + getId() +
            ", conditionText='" + getConditionText() + "'" +
            ", conditionOnsetDates='" + getConditionOnsetDates() + "'" +
            ", patientUID='" + getPatientUID() + "'" +
            "}";
    }
}
