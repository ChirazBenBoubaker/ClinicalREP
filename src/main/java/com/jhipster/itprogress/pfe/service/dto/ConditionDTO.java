package com.jhipster.itprogress.pfe.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.jhipster.itprogress.pfe.domain.Condition} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConditionDTO implements Serializable {

    private Long id;

    private String conditionText;

    private LocalDate conditionOnsetDates;

    private String patientUID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConditionText() {
        return conditionText;
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
    }

    public LocalDate getConditionOnsetDates() {
        return conditionOnsetDates;
    }

    public void setConditionOnsetDates(LocalDate conditionOnsetDates) {
        this.conditionOnsetDates = conditionOnsetDates;
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
        if (!(o instanceof ConditionDTO)) {
            return false;
        }

        ConditionDTO conditionDTO = (ConditionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, conditionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConditionDTO{" +
            "id=" + getId() +
            ", conditionText='" + getConditionText() + "'" +
            ", conditionOnsetDates='" + getConditionOnsetDates() + "'" +
            ", patientUID='" + getPatientUID() + "'" +
            "}";
    }
}
