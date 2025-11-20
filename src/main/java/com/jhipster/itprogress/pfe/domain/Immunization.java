package com.jhipster.itprogress.pfe.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Immunization.
 */
@Entity
@Table(name = "immunization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Immunization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "immunization")
    private String immunization;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "patient_uid")
    private String patientUID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Immunization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImmunization() {
        return this.immunization;
    }

    public Immunization immunization(String immunization) {
        this.setImmunization(immunization);
        return this;
    }

    public void setImmunization(String immunization) {
        this.immunization = immunization;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Immunization date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPatientUID() {
        return this.patientUID;
    }

    public Immunization patientUID(String patientUID) {
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
        if (!(o instanceof Immunization)) {
            return false;
        }
        return id != null && id.equals(((Immunization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Immunization{" +
            "id=" + getId() +
            ", immunization='" + getImmunization() + "'" +
            ", date='" + getDate() + "'" +
            ", patientUID='" + getPatientUID() + "'" +
            "}";
    }
}
