package com.jhipster.itprogress.pfe.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.jhipster.itprogress.pfe.domain.Patient} entity. This class is used
 * in {@link com.jhipster.itprogress.pfe.web.rest.PatientResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /patients?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter patientUID;

    private StringFilter nameFamily;

    private StringFilter nameGiven;

    private LocalDateFilter birthdate;

    private StringFilter gender;

    private StringFilter contact;

    private StringFilter line;

    private StringFilter city;

    private StringFilter country;

    private StringFilter state;

    private StringFilter postalcode;

    private Boolean distinct;

    public PatientCriteria() {}

    public PatientCriteria(PatientCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.patientUID = other.patientUID == null ? null : other.patientUID.copy();
        this.nameFamily = other.nameFamily == null ? null : other.nameFamily.copy();
        this.nameGiven = other.nameGiven == null ? null : other.nameGiven.copy();
        this.birthdate = other.birthdate == null ? null : other.birthdate.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.contact = other.contact == null ? null : other.contact.copy();
        this.line = other.line == null ? null : other.line.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.postalcode = other.postalcode == null ? null : other.postalcode.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PatientCriteria copy() {
        return new PatientCriteria(this);
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

    public StringFilter getNameFamily() {
        return nameFamily;
    }

    public StringFilter nameFamily() {
        if (nameFamily == null) {
            nameFamily = new StringFilter();
        }
        return nameFamily;
    }

    public void setNameFamily(StringFilter nameFamily) {
        this.nameFamily = nameFamily;
    }

    public StringFilter getNameGiven() {
        return nameGiven;
    }

    public StringFilter nameGiven() {
        if (nameGiven == null) {
            nameGiven = new StringFilter();
        }
        return nameGiven;
    }

    public void setNameGiven(StringFilter nameGiven) {
        this.nameGiven = nameGiven;
    }

    public LocalDateFilter getBirthdate() {
        return birthdate;
    }

    public LocalDateFilter birthdate() {
        if (birthdate == null) {
            birthdate = new LocalDateFilter();
        }
        return birthdate;
    }

    public void setBirthdate(LocalDateFilter birthdate) {
        this.birthdate = birthdate;
    }

    public StringFilter getGender() {
        return gender;
    }

    public StringFilter gender() {
        if (gender == null) {
            gender = new StringFilter();
        }
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public StringFilter getContact() {
        return contact;
    }

    public StringFilter contact() {
        if (contact == null) {
            contact = new StringFilter();
        }
        return contact;
    }

    public void setContact(StringFilter contact) {
        this.contact = contact;
    }

    public StringFilter getLine() {
        return line;
    }

    public StringFilter line() {
        if (line == null) {
            line = new StringFilter();
        }
        return line;
    }

    public void setLine(StringFilter line) {
        this.line = line;
    }

    public StringFilter getCity() {
        return city;
    }

    public StringFilter city() {
        if (city == null) {
            city = new StringFilter();
        }
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getCountry() {
        return country;
    }

    public StringFilter country() {
        if (country == null) {
            country = new StringFilter();
        }
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public StringFilter getState() {
        return state;
    }

    public StringFilter state() {
        if (state == null) {
            state = new StringFilter();
        }
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public StringFilter getPostalcode() {
        return postalcode;
    }

    public StringFilter postalcode() {
        if (postalcode == null) {
            postalcode = new StringFilter();
        }
        return postalcode;
    }

    public void setPostalcode(StringFilter postalcode) {
        this.postalcode = postalcode;
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
        final PatientCriteria that = (PatientCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(patientUID, that.patientUID) &&
            Objects.equals(nameFamily, that.nameFamily) &&
            Objects.equals(nameGiven, that.nameGiven) &&
            Objects.equals(birthdate, that.birthdate) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(contact, that.contact) &&
            Objects.equals(line, that.line) &&
            Objects.equals(city, that.city) &&
            Objects.equals(country, that.country) &&
            Objects.equals(state, that.state) &&
            Objects.equals(postalcode, that.postalcode) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            patientUID,
            nameFamily,
            nameGiven,
            birthdate,
            gender,
            contact,
            line,
            city,
            country,
            state,
            postalcode,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (patientUID != null ? "patientUID=" + patientUID + ", " : "") +
            (nameFamily != null ? "nameFamily=" + nameFamily + ", " : "") +
            (nameGiven != null ? "nameGiven=" + nameGiven + ", " : "") +
            (birthdate != null ? "birthdate=" + birthdate + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (contact != null ? "contact=" + contact + ", " : "") +
            (line != null ? "line=" + line + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (state != null ? "state=" + state + ", " : "") +
            (postalcode != null ? "postalcode=" + postalcode + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
