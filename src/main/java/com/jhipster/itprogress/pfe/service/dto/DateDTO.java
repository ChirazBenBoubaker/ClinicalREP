package com.jhipster.itprogress.pfe.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.jhipster.itprogress.pfe.domain.Date} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DateDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private Integer year;

    private Integer month;

    private Integer day;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DateDTO)) {
            return false;
        }

        DateDTO dateDTO = (DateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DateDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", year=" + getYear() +
            ", month=" + getMonth() +
            ", day=" + getDay() +
            "}";
    }
}
