package com.jhipster.itprogress.pfe.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Medication.
 */
@Entity
@Table(name = "medication")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Medication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "medication_text")
    private String medicationText;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "patient_uid")
    private String patientUID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Medication id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedicationText() {
        return this.medicationText;
    }

    public Medication medicationText(String medicationText) {
        this.setMedicationText(medicationText);
        return this;
    }

    public void setMedicationText(String medicationText) {
        this.medicationText = medicationText;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Medication date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPatientUID() {
        return this.patientUID;
    }

    public Medication patientUID(String patientUID) {
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
        if (!(o instanceof Medication)) {
            return false;
        }
        return id != null && id.equals(((Medication) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Medication{" +
            "id=" + getId() +
            ", medicationText='" + getMedicationText() + "'" +
            ", date='" + getDate() + "'" +
            ", patientUID='" + getPatientUID() + "'" +
            "}";
    }
}
