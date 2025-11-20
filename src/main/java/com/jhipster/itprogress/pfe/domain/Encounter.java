package com.jhipster.itprogress.pfe.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Encounter.
 */
@Entity
@Table(name = "encounter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Encounter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "encounters_text")
    private String encountersText;

    @Column(name = "encounter_location")
    private String encounterLocation;

    @Column(name = "encounter_provider")
    private String encounterProvider;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "patient_uid")
    private String patientUID;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Encounter id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEncountersText() {
        return this.encountersText;
    }

    public Encounter encountersText(String encountersText) {
        this.setEncountersText(encountersText);
        return this;
    }

    public void setEncountersText(String encountersText) {
        this.encountersText = encountersText;
    }

    public String getEncounterLocation() {
        return this.encounterLocation;
    }

    public Encounter encounterLocation(String encounterLocation) {
        this.setEncounterLocation(encounterLocation);
        return this;
    }

    public void setEncounterLocation(String encounterLocation) {
        this.encounterLocation = encounterLocation;
    }

    public String getEncounterProvider() {
        return this.encounterProvider;
    }

    public Encounter encounterProvider(String encounterProvider) {
        this.setEncounterProvider(encounterProvider);
        return this;
    }

    public void setEncounterProvider(String encounterProvider) {
        this.encounterProvider = encounterProvider;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Encounter date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPatientUID() {
        return this.patientUID;
    }

    public Encounter patientUID(String patientUID) {
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
        if (!(o instanceof Encounter)) {
            return false;
        }
        return id != null && id.equals(((Encounter) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Encounter{" +
            "id=" + getId() +
            ", encountersText='" + getEncountersText() + "'" +
            ", encounterLocation='" + getEncounterLocation() + "'" +
            ", encounterProvider='" + getEncounterProvider() + "'" +
            ", date='" + getDate() + "'" +
            ", patientUID='" + getPatientUID() + "'" +
            "}";
    }
}
