package com.jhipster.itprogress.pfe.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.itprogress.pfe.IntegrationTest;
import com.jhipster.itprogress.pfe.domain.Patient;
import com.jhipster.itprogress.pfe.repository.PatientRepository;
import com.jhipster.itprogress.pfe.service.criteria.PatientCriteria;
import com.jhipster.itprogress.pfe.service.dto.PatientDTO;
import com.jhipster.itprogress.pfe.service.mapper.PatientMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PatientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PatientResourceIT {

    private static final String DEFAULT_PATIENT_UID = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_UID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_FAMILY = "AAAAAAAAAA";
    private static final String UPDATED_NAME_FAMILY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_GIVEN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_GIVEN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BIRTHDATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_LINE = "AAAAAAAAAA";
    private static final String UPDATED_LINE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_POSTALCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTALCODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/patients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatientMockMvc;

    private Patient patient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patient createEntity(EntityManager em) {
        Patient patient = new Patient()
            .patientUID(DEFAULT_PATIENT_UID)
            .nameFamily(DEFAULT_NAME_FAMILY)
            .nameGiven(DEFAULT_NAME_GIVEN)
            .birthdate(DEFAULT_BIRTHDATE)
            .gender(DEFAULT_GENDER)
            .contact(DEFAULT_CONTACT)
            .line(DEFAULT_LINE)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY)
            .state(DEFAULT_STATE)
            .postalcode(DEFAULT_POSTALCODE);
        return patient;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patient createUpdatedEntity(EntityManager em) {
        Patient patient = new Patient()
            .patientUID(UPDATED_PATIENT_UID)
            .nameFamily(UPDATED_NAME_FAMILY)
            .nameGiven(UPDATED_NAME_GIVEN)
            .birthdate(UPDATED_BIRTHDATE)
            .gender(UPDATED_GENDER)
            .contact(UPDATED_CONTACT)
            .line(UPDATED_LINE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .state(UPDATED_STATE)
            .postalcode(UPDATED_POSTALCODE);
        return patient;
    }

    @BeforeEach
    public void initTest() {
        patient = createEntity(em);
    }

    @Test
    @Transactional
    void createPatient() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();
        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);
        restPatientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isCreated());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate + 1);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getPatientUID()).isEqualTo(DEFAULT_PATIENT_UID);
        assertThat(testPatient.getNameFamily()).isEqualTo(DEFAULT_NAME_FAMILY);
        assertThat(testPatient.getNameGiven()).isEqualTo(DEFAULT_NAME_GIVEN);
        assertThat(testPatient.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
        assertThat(testPatient.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPatient.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testPatient.getLine()).isEqualTo(DEFAULT_LINE);
        assertThat(testPatient.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testPatient.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testPatient.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testPatient.getPostalcode()).isEqualTo(DEFAULT_POSTALCODE);
    }

    @Test
    @Transactional
    void createPatientWithExistingId() throws Exception {
        // Create the Patient with an existing ID
        patient.setId(1L);
        PatientDTO patientDTO = patientMapper.toDto(patient);

        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPatientUIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setPatientUID(null);

        // Create the Patient, which fails.
        PatientDTO patientDTO = patientMapper.toDto(patient);

        restPatientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPatients() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList
        restPatientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patient.getId().intValue())))
            .andExpect(jsonPath("$.[*].patientUID").value(hasItem(DEFAULT_PATIENT_UID)))
            .andExpect(jsonPath("$.[*].nameFamily").value(hasItem(DEFAULT_NAME_FAMILY)))
            .andExpect(jsonPath("$.[*].nameGiven").value(hasItem(DEFAULT_NAME_GIVEN)))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].line").value(hasItem(DEFAULT_LINE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].postalcode").value(hasItem(DEFAULT_POSTALCODE)));
    }

    @Test
    @Transactional
    void getPatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the patient
        restPatientMockMvc
            .perform(get(ENTITY_API_URL_ID, patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patient.getId().intValue()))
            .andExpect(jsonPath("$.patientUID").value(DEFAULT_PATIENT_UID))
            .andExpect(jsonPath("$.nameFamily").value(DEFAULT_NAME_FAMILY))
            .andExpect(jsonPath("$.nameGiven").value(DEFAULT_NAME_GIVEN))
            .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.line").value(DEFAULT_LINE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.postalcode").value(DEFAULT_POSTALCODE));
    }

    @Test
    @Transactional
    void getPatientsByIdFiltering() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        Long id = patient.getId();

        defaultPatientShouldBeFound("id.equals=" + id);
        defaultPatientShouldNotBeFound("id.notEquals=" + id);

        defaultPatientShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPatientShouldNotBeFound("id.greaterThan=" + id);

        defaultPatientShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPatientShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPatientsByPatientUIDIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where patientUID equals to DEFAULT_PATIENT_UID
        defaultPatientShouldBeFound("patientUID.equals=" + DEFAULT_PATIENT_UID);

        // Get all the patientList where patientUID equals to UPDATED_PATIENT_UID
        defaultPatientShouldNotBeFound("patientUID.equals=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllPatientsByPatientUIDIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where patientUID in DEFAULT_PATIENT_UID or UPDATED_PATIENT_UID
        defaultPatientShouldBeFound("patientUID.in=" + DEFAULT_PATIENT_UID + "," + UPDATED_PATIENT_UID);

        // Get all the patientList where patientUID equals to UPDATED_PATIENT_UID
        defaultPatientShouldNotBeFound("patientUID.in=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllPatientsByPatientUIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where patientUID is not null
        defaultPatientShouldBeFound("patientUID.specified=true");

        // Get all the patientList where patientUID is null
        defaultPatientShouldNotBeFound("patientUID.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByPatientUIDContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where patientUID contains DEFAULT_PATIENT_UID
        defaultPatientShouldBeFound("patientUID.contains=" + DEFAULT_PATIENT_UID);

        // Get all the patientList where patientUID contains UPDATED_PATIENT_UID
        defaultPatientShouldNotBeFound("patientUID.contains=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllPatientsByPatientUIDNotContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where patientUID does not contain DEFAULT_PATIENT_UID
        defaultPatientShouldNotBeFound("patientUID.doesNotContain=" + DEFAULT_PATIENT_UID);

        // Get all the patientList where patientUID does not contain UPDATED_PATIENT_UID
        defaultPatientShouldBeFound("patientUID.doesNotContain=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllPatientsByNameFamilyIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where nameFamily equals to DEFAULT_NAME_FAMILY
        defaultPatientShouldBeFound("nameFamily.equals=" + DEFAULT_NAME_FAMILY);

        // Get all the patientList where nameFamily equals to UPDATED_NAME_FAMILY
        defaultPatientShouldNotBeFound("nameFamily.equals=" + UPDATED_NAME_FAMILY);
    }

    @Test
    @Transactional
    void getAllPatientsByNameFamilyIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where nameFamily in DEFAULT_NAME_FAMILY or UPDATED_NAME_FAMILY
        defaultPatientShouldBeFound("nameFamily.in=" + DEFAULT_NAME_FAMILY + "," + UPDATED_NAME_FAMILY);

        // Get all the patientList where nameFamily equals to UPDATED_NAME_FAMILY
        defaultPatientShouldNotBeFound("nameFamily.in=" + UPDATED_NAME_FAMILY);
    }

    @Test
    @Transactional
    void getAllPatientsByNameFamilyIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where nameFamily is not null
        defaultPatientShouldBeFound("nameFamily.specified=true");

        // Get all the patientList where nameFamily is null
        defaultPatientShouldNotBeFound("nameFamily.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByNameFamilyContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where nameFamily contains DEFAULT_NAME_FAMILY
        defaultPatientShouldBeFound("nameFamily.contains=" + DEFAULT_NAME_FAMILY);

        // Get all the patientList where nameFamily contains UPDATED_NAME_FAMILY
        defaultPatientShouldNotBeFound("nameFamily.contains=" + UPDATED_NAME_FAMILY);
    }

    @Test
    @Transactional
    void getAllPatientsByNameFamilyNotContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where nameFamily does not contain DEFAULT_NAME_FAMILY
        defaultPatientShouldNotBeFound("nameFamily.doesNotContain=" + DEFAULT_NAME_FAMILY);

        // Get all the patientList where nameFamily does not contain UPDATED_NAME_FAMILY
        defaultPatientShouldBeFound("nameFamily.doesNotContain=" + UPDATED_NAME_FAMILY);
    }

    @Test
    @Transactional
    void getAllPatientsByNameGivenIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where nameGiven equals to DEFAULT_NAME_GIVEN
        defaultPatientShouldBeFound("nameGiven.equals=" + DEFAULT_NAME_GIVEN);

        // Get all the patientList where nameGiven equals to UPDATED_NAME_GIVEN
        defaultPatientShouldNotBeFound("nameGiven.equals=" + UPDATED_NAME_GIVEN);
    }

    @Test
    @Transactional
    void getAllPatientsByNameGivenIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where nameGiven in DEFAULT_NAME_GIVEN or UPDATED_NAME_GIVEN
        defaultPatientShouldBeFound("nameGiven.in=" + DEFAULT_NAME_GIVEN + "," + UPDATED_NAME_GIVEN);

        // Get all the patientList where nameGiven equals to UPDATED_NAME_GIVEN
        defaultPatientShouldNotBeFound("nameGiven.in=" + UPDATED_NAME_GIVEN);
    }

    @Test
    @Transactional
    void getAllPatientsByNameGivenIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where nameGiven is not null
        defaultPatientShouldBeFound("nameGiven.specified=true");

        // Get all the patientList where nameGiven is null
        defaultPatientShouldNotBeFound("nameGiven.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByNameGivenContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where nameGiven contains DEFAULT_NAME_GIVEN
        defaultPatientShouldBeFound("nameGiven.contains=" + DEFAULT_NAME_GIVEN);

        // Get all the patientList where nameGiven contains UPDATED_NAME_GIVEN
        defaultPatientShouldNotBeFound("nameGiven.contains=" + UPDATED_NAME_GIVEN);
    }

    @Test
    @Transactional
    void getAllPatientsByNameGivenNotContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where nameGiven does not contain DEFAULT_NAME_GIVEN
        defaultPatientShouldNotBeFound("nameGiven.doesNotContain=" + DEFAULT_NAME_GIVEN);

        // Get all the patientList where nameGiven does not contain UPDATED_NAME_GIVEN
        defaultPatientShouldBeFound("nameGiven.doesNotContain=" + UPDATED_NAME_GIVEN);
    }

    @Test
    @Transactional
    void getAllPatientsByBirthdateIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where birthdate equals to DEFAULT_BIRTHDATE
        defaultPatientShouldBeFound("birthdate.equals=" + DEFAULT_BIRTHDATE);

        // Get all the patientList where birthdate equals to UPDATED_BIRTHDATE
        defaultPatientShouldNotBeFound("birthdate.equals=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    void getAllPatientsByBirthdateIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where birthdate in DEFAULT_BIRTHDATE or UPDATED_BIRTHDATE
        defaultPatientShouldBeFound("birthdate.in=" + DEFAULT_BIRTHDATE + "," + UPDATED_BIRTHDATE);

        // Get all the patientList where birthdate equals to UPDATED_BIRTHDATE
        defaultPatientShouldNotBeFound("birthdate.in=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    void getAllPatientsByBirthdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where birthdate is not null
        defaultPatientShouldBeFound("birthdate.specified=true");

        // Get all the patientList where birthdate is null
        defaultPatientShouldNotBeFound("birthdate.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByBirthdateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where birthdate is greater than or equal to DEFAULT_BIRTHDATE
        defaultPatientShouldBeFound("birthdate.greaterThanOrEqual=" + DEFAULT_BIRTHDATE);

        // Get all the patientList where birthdate is greater than or equal to UPDATED_BIRTHDATE
        defaultPatientShouldNotBeFound("birthdate.greaterThanOrEqual=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    void getAllPatientsByBirthdateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where birthdate is less than or equal to DEFAULT_BIRTHDATE
        defaultPatientShouldBeFound("birthdate.lessThanOrEqual=" + DEFAULT_BIRTHDATE);

        // Get all the patientList where birthdate is less than or equal to SMALLER_BIRTHDATE
        defaultPatientShouldNotBeFound("birthdate.lessThanOrEqual=" + SMALLER_BIRTHDATE);
    }

    @Test
    @Transactional
    void getAllPatientsByBirthdateIsLessThanSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where birthdate is less than DEFAULT_BIRTHDATE
        defaultPatientShouldNotBeFound("birthdate.lessThan=" + DEFAULT_BIRTHDATE);

        // Get all the patientList where birthdate is less than UPDATED_BIRTHDATE
        defaultPatientShouldBeFound("birthdate.lessThan=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    void getAllPatientsByBirthdateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where birthdate is greater than DEFAULT_BIRTHDATE
        defaultPatientShouldNotBeFound("birthdate.greaterThan=" + DEFAULT_BIRTHDATE);

        // Get all the patientList where birthdate is greater than SMALLER_BIRTHDATE
        defaultPatientShouldBeFound("birthdate.greaterThan=" + SMALLER_BIRTHDATE);
    }

    @Test
    @Transactional
    void getAllPatientsByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where gender equals to DEFAULT_GENDER
        defaultPatientShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the patientList where gender equals to UPDATED_GENDER
        defaultPatientShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllPatientsByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultPatientShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the patientList where gender equals to UPDATED_GENDER
        defaultPatientShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllPatientsByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where gender is not null
        defaultPatientShouldBeFound("gender.specified=true");

        // Get all the patientList where gender is null
        defaultPatientShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByGenderContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where gender contains DEFAULT_GENDER
        defaultPatientShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the patientList where gender contains UPDATED_GENDER
        defaultPatientShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllPatientsByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where gender does not contain DEFAULT_GENDER
        defaultPatientShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the patientList where gender does not contain UPDATED_GENDER
        defaultPatientShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllPatientsByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where contact equals to DEFAULT_CONTACT
        defaultPatientShouldBeFound("contact.equals=" + DEFAULT_CONTACT);

        // Get all the patientList where contact equals to UPDATED_CONTACT
        defaultPatientShouldNotBeFound("contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllPatientsByContactIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where contact in DEFAULT_CONTACT or UPDATED_CONTACT
        defaultPatientShouldBeFound("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT);

        // Get all the patientList where contact equals to UPDATED_CONTACT
        defaultPatientShouldNotBeFound("contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllPatientsByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where contact is not null
        defaultPatientShouldBeFound("contact.specified=true");

        // Get all the patientList where contact is null
        defaultPatientShouldNotBeFound("contact.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByContactContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where contact contains DEFAULT_CONTACT
        defaultPatientShouldBeFound("contact.contains=" + DEFAULT_CONTACT);

        // Get all the patientList where contact contains UPDATED_CONTACT
        defaultPatientShouldNotBeFound("contact.contains=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllPatientsByContactNotContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where contact does not contain DEFAULT_CONTACT
        defaultPatientShouldNotBeFound("contact.doesNotContain=" + DEFAULT_CONTACT);

        // Get all the patientList where contact does not contain UPDATED_CONTACT
        defaultPatientShouldBeFound("contact.doesNotContain=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllPatientsByLineIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where line equals to DEFAULT_LINE
        defaultPatientShouldBeFound("line.equals=" + DEFAULT_LINE);

        // Get all the patientList where line equals to UPDATED_LINE
        defaultPatientShouldNotBeFound("line.equals=" + UPDATED_LINE);
    }

    @Test
    @Transactional
    void getAllPatientsByLineIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where line in DEFAULT_LINE or UPDATED_LINE
        defaultPatientShouldBeFound("line.in=" + DEFAULT_LINE + "," + UPDATED_LINE);

        // Get all the patientList where line equals to UPDATED_LINE
        defaultPatientShouldNotBeFound("line.in=" + UPDATED_LINE);
    }

    @Test
    @Transactional
    void getAllPatientsByLineIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where line is not null
        defaultPatientShouldBeFound("line.specified=true");

        // Get all the patientList where line is null
        defaultPatientShouldNotBeFound("line.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByLineContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where line contains DEFAULT_LINE
        defaultPatientShouldBeFound("line.contains=" + DEFAULT_LINE);

        // Get all the patientList where line contains UPDATED_LINE
        defaultPatientShouldNotBeFound("line.contains=" + UPDATED_LINE);
    }

    @Test
    @Transactional
    void getAllPatientsByLineNotContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where line does not contain DEFAULT_LINE
        defaultPatientShouldNotBeFound("line.doesNotContain=" + DEFAULT_LINE);

        // Get all the patientList where line does not contain UPDATED_LINE
        defaultPatientShouldBeFound("line.doesNotContain=" + UPDATED_LINE);
    }

    @Test
    @Transactional
    void getAllPatientsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where city equals to DEFAULT_CITY
        defaultPatientShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the patientList where city equals to UPDATED_CITY
        defaultPatientShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllPatientsByCityIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where city in DEFAULT_CITY or UPDATED_CITY
        defaultPatientShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the patientList where city equals to UPDATED_CITY
        defaultPatientShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllPatientsByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where city is not null
        defaultPatientShouldBeFound("city.specified=true");

        // Get all the patientList where city is null
        defaultPatientShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByCityContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where city contains DEFAULT_CITY
        defaultPatientShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the patientList where city contains UPDATED_CITY
        defaultPatientShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllPatientsByCityNotContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where city does not contain DEFAULT_CITY
        defaultPatientShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the patientList where city does not contain UPDATED_CITY
        defaultPatientShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllPatientsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where country equals to DEFAULT_COUNTRY
        defaultPatientShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the patientList where country equals to UPDATED_COUNTRY
        defaultPatientShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllPatientsByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultPatientShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the patientList where country equals to UPDATED_COUNTRY
        defaultPatientShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllPatientsByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where country is not null
        defaultPatientShouldBeFound("country.specified=true");

        // Get all the patientList where country is null
        defaultPatientShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByCountryContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where country contains DEFAULT_COUNTRY
        defaultPatientShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the patientList where country contains UPDATED_COUNTRY
        defaultPatientShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllPatientsByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where country does not contain DEFAULT_COUNTRY
        defaultPatientShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the patientList where country does not contain UPDATED_COUNTRY
        defaultPatientShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllPatientsByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where state equals to DEFAULT_STATE
        defaultPatientShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the patientList where state equals to UPDATED_STATE
        defaultPatientShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllPatientsByStateIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where state in DEFAULT_STATE or UPDATED_STATE
        defaultPatientShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the patientList where state equals to UPDATED_STATE
        defaultPatientShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllPatientsByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where state is not null
        defaultPatientShouldBeFound("state.specified=true");

        // Get all the patientList where state is null
        defaultPatientShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByStateContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where state contains DEFAULT_STATE
        defaultPatientShouldBeFound("state.contains=" + DEFAULT_STATE);

        // Get all the patientList where state contains UPDATED_STATE
        defaultPatientShouldNotBeFound("state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllPatientsByStateNotContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where state does not contain DEFAULT_STATE
        defaultPatientShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

        // Get all the patientList where state does not contain UPDATED_STATE
        defaultPatientShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllPatientsByPostalcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where postalcode equals to DEFAULT_POSTALCODE
        defaultPatientShouldBeFound("postalcode.equals=" + DEFAULT_POSTALCODE);

        // Get all the patientList where postalcode equals to UPDATED_POSTALCODE
        defaultPatientShouldNotBeFound("postalcode.equals=" + UPDATED_POSTALCODE);
    }

    @Test
    @Transactional
    void getAllPatientsByPostalcodeIsInShouldWork() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where postalcode in DEFAULT_POSTALCODE or UPDATED_POSTALCODE
        defaultPatientShouldBeFound("postalcode.in=" + DEFAULT_POSTALCODE + "," + UPDATED_POSTALCODE);

        // Get all the patientList where postalcode equals to UPDATED_POSTALCODE
        defaultPatientShouldNotBeFound("postalcode.in=" + UPDATED_POSTALCODE);
    }

    @Test
    @Transactional
    void getAllPatientsByPostalcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where postalcode is not null
        defaultPatientShouldBeFound("postalcode.specified=true");

        // Get all the patientList where postalcode is null
        defaultPatientShouldNotBeFound("postalcode.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientsByPostalcodeContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where postalcode contains DEFAULT_POSTALCODE
        defaultPatientShouldBeFound("postalcode.contains=" + DEFAULT_POSTALCODE);

        // Get all the patientList where postalcode contains UPDATED_POSTALCODE
        defaultPatientShouldNotBeFound("postalcode.contains=" + UPDATED_POSTALCODE);
    }

    @Test
    @Transactional
    void getAllPatientsByPostalcodeNotContainsSomething() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList where postalcode does not contain DEFAULT_POSTALCODE
        defaultPatientShouldNotBeFound("postalcode.doesNotContain=" + DEFAULT_POSTALCODE);

        // Get all the patientList where postalcode does not contain UPDATED_POSTALCODE
        defaultPatientShouldBeFound("postalcode.doesNotContain=" + UPDATED_POSTALCODE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPatientShouldBeFound(String filter) throws Exception {
        restPatientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patient.getId().intValue())))
            .andExpect(jsonPath("$.[*].patientUID").value(hasItem(DEFAULT_PATIENT_UID)))
            .andExpect(jsonPath("$.[*].nameFamily").value(hasItem(DEFAULT_NAME_FAMILY)))
            .andExpect(jsonPath("$.[*].nameGiven").value(hasItem(DEFAULT_NAME_GIVEN)))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].line").value(hasItem(DEFAULT_LINE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].postalcode").value(hasItem(DEFAULT_POSTALCODE)));

        // Check, that the count call also returns 1
        restPatientMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPatientShouldNotBeFound(String filter) throws Exception {
        restPatientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPatientMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPatient() throws Exception {
        // Get the patient
        restPatientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient
        Patient updatedPatient = patientRepository.findById(patient.getId()).get();
        // Disconnect from session so that the updates on updatedPatient are not directly saved in db
        em.detach(updatedPatient);
        updatedPatient
            .patientUID(UPDATED_PATIENT_UID)
            .nameFamily(UPDATED_NAME_FAMILY)
            .nameGiven(UPDATED_NAME_GIVEN)
            .birthdate(UPDATED_BIRTHDATE)
            .gender(UPDATED_GENDER)
            .contact(UPDATED_CONTACT)
            .line(UPDATED_LINE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .state(UPDATED_STATE)
            .postalcode(UPDATED_POSTALCODE);
        PatientDTO patientDTO = patientMapper.toDto(updatedPatient);

        restPatientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientDTO))
            )
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
        assertThat(testPatient.getNameFamily()).isEqualTo(UPDATED_NAME_FAMILY);
        assertThat(testPatient.getNameGiven()).isEqualTo(UPDATED_NAME_GIVEN);
        assertThat(testPatient.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testPatient.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPatient.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testPatient.getLine()).isEqualTo(UPDATED_LINE);
        assertThat(testPatient.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testPatient.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testPatient.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testPatient.getPostalcode()).isEqualTo(UPDATED_POSTALCODE);
    }

    @Test
    @Transactional
    void putNonExistingPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePatientWithPatch() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient using partial update
        Patient partialUpdatedPatient = new Patient();
        partialUpdatedPatient.setId(patient.getId());

        partialUpdatedPatient
            .patientUID(UPDATED_PATIENT_UID)
            .nameFamily(UPDATED_NAME_FAMILY)
            .nameGiven(UPDATED_NAME_GIVEN)
            .birthdate(UPDATED_BIRTHDATE)
            .contact(UPDATED_CONTACT)
            .postalcode(UPDATED_POSTALCODE);

        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatient))
            )
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
        assertThat(testPatient.getNameFamily()).isEqualTo(UPDATED_NAME_FAMILY);
        assertThat(testPatient.getNameGiven()).isEqualTo(UPDATED_NAME_GIVEN);
        assertThat(testPatient.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testPatient.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPatient.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testPatient.getLine()).isEqualTo(DEFAULT_LINE);
        assertThat(testPatient.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testPatient.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testPatient.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testPatient.getPostalcode()).isEqualTo(UPDATED_POSTALCODE);
    }

    @Test
    @Transactional
    void fullUpdatePatientWithPatch() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient using partial update
        Patient partialUpdatedPatient = new Patient();
        partialUpdatedPatient.setId(patient.getId());

        partialUpdatedPatient
            .patientUID(UPDATED_PATIENT_UID)
            .nameFamily(UPDATED_NAME_FAMILY)
            .nameGiven(UPDATED_NAME_GIVEN)
            .birthdate(UPDATED_BIRTHDATE)
            .gender(UPDATED_GENDER)
            .contact(UPDATED_CONTACT)
            .line(UPDATED_LINE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .state(UPDATED_STATE)
            .postalcode(UPDATED_POSTALCODE);

        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatient))
            )
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
        assertThat(testPatient.getNameFamily()).isEqualTo(UPDATED_NAME_FAMILY);
        assertThat(testPatient.getNameGiven()).isEqualTo(UPDATED_NAME_GIVEN);
        assertThat(testPatient.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testPatient.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPatient.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testPatient.getLine()).isEqualTo(UPDATED_LINE);
        assertThat(testPatient.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testPatient.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testPatient.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testPatient.getPostalcode()).isEqualTo(UPDATED_POSTALCODE);
    }

    @Test
    @Transactional
    void patchNonExistingPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, patientDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(patientDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeDelete = patientRepository.findAll().size();

        // Delete the patient
        restPatientMockMvc
            .perform(delete(ENTITY_API_URL_ID, patient.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
