package com.jhipster.itprogress.pfe.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.itprogress.pfe.IntegrationTest;
import com.jhipster.itprogress.pfe.domain.Factclinical;
import com.jhipster.itprogress.pfe.repository.FactclinicalRepository;
import com.jhipster.itprogress.pfe.service.criteria.FactclinicalCriteria;
import com.jhipster.itprogress.pfe.service.dto.FactclinicalDTO;
import com.jhipster.itprogress.pfe.service.mapper.FactclinicalMapper;
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
 * Integration tests for the {@link FactclinicalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FactclinicalResourceIT {

    private static final String DEFAULT_PATIENT_UID = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_UID = "BBBBBBBBBB";

    private static final Integer DEFAULT_ENCOUNTER_ID = 1;
    private static final Integer UPDATED_ENCOUNTER_ID = 2;
    private static final Integer SMALLER_ENCOUNTER_ID = 1 - 1;

    private static final Integer DEFAULT_OBSERVATION_ID = 1;
    private static final Integer UPDATED_OBSERVATION_ID = 2;
    private static final Integer SMALLER_OBSERVATION_ID = 1 - 1;

    private static final Integer DEFAULT_PROCEDURE_ID = 1;
    private static final Integer UPDATED_PROCEDURE_ID = 2;
    private static final Integer SMALLER_PROCEDURE_ID = 1 - 1;

    private static final Integer DEFAULT_IMMUNIZATION_ID = 1;
    private static final Integer UPDATED_IMMUNIZATION_ID = 2;
    private static final Integer SMALLER_IMMUNIZATION_ID = 1 - 1;

    private static final Integer DEFAULT_MEDICATION_ID = 1;
    private static final Integer UPDATED_MEDICATION_ID = 2;
    private static final Integer SMALLER_MEDICATION_ID = 1 - 1;

    private static final Integer DEFAULT_CONDITION_ID = 1;
    private static final Integer UPDATED_CONDITION_ID = 2;
    private static final Integer SMALLER_CONDITION_ID = 1 - 1;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/factclinicals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FactclinicalRepository factclinicalRepository;

    @Autowired
    private FactclinicalMapper factclinicalMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFactclinicalMockMvc;

    private Factclinical factclinical;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Factclinical createEntity(EntityManager em) {
        Factclinical factclinical = new Factclinical()
            .patientUID(DEFAULT_PATIENT_UID)
            .encounterID(DEFAULT_ENCOUNTER_ID)
            .observationID(DEFAULT_OBSERVATION_ID)
            .procedureID(DEFAULT_PROCEDURE_ID)
            .immunizationID(DEFAULT_IMMUNIZATION_ID)
            .medicationID(DEFAULT_MEDICATION_ID)
            .conditionID(DEFAULT_CONDITION_ID)
            .date(DEFAULT_DATE);
        return factclinical;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Factclinical createUpdatedEntity(EntityManager em) {
        Factclinical factclinical = new Factclinical()
            .patientUID(UPDATED_PATIENT_UID)
            .encounterID(UPDATED_ENCOUNTER_ID)
            .observationID(UPDATED_OBSERVATION_ID)
            .procedureID(UPDATED_PROCEDURE_ID)
            .immunizationID(UPDATED_IMMUNIZATION_ID)
            .medicationID(UPDATED_MEDICATION_ID)
            .conditionID(UPDATED_CONDITION_ID)
            .date(UPDATED_DATE);
        return factclinical;
    }

    @BeforeEach
    public void initTest() {
        factclinical = createEntity(em);
    }

    @Test
    @Transactional
    void createFactclinical() throws Exception {
        int databaseSizeBeforeCreate = factclinicalRepository.findAll().size();
        // Create the Factclinical
        FactclinicalDTO factclinicalDTO = factclinicalMapper.toDto(factclinical);
        restFactclinicalMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factclinicalDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Factclinical in the database
        List<Factclinical> factclinicalList = factclinicalRepository.findAll();
        assertThat(factclinicalList).hasSize(databaseSizeBeforeCreate + 1);
        Factclinical testFactclinical = factclinicalList.get(factclinicalList.size() - 1);
        assertThat(testFactclinical.getPatientUID()).isEqualTo(DEFAULT_PATIENT_UID);
        assertThat(testFactclinical.getEncounterID()).isEqualTo(DEFAULT_ENCOUNTER_ID);
        assertThat(testFactclinical.getObservationID()).isEqualTo(DEFAULT_OBSERVATION_ID);
        assertThat(testFactclinical.getProcedureID()).isEqualTo(DEFAULT_PROCEDURE_ID);
        assertThat(testFactclinical.getImmunizationID()).isEqualTo(DEFAULT_IMMUNIZATION_ID);
        assertThat(testFactclinical.getMedicationID()).isEqualTo(DEFAULT_MEDICATION_ID);
        assertThat(testFactclinical.getConditionID()).isEqualTo(DEFAULT_CONDITION_ID);
        assertThat(testFactclinical.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createFactclinicalWithExistingId() throws Exception {
        // Create the Factclinical with an existing ID
        factclinical.setId(1L);
        FactclinicalDTO factclinicalDTO = factclinicalMapper.toDto(factclinical);

        int databaseSizeBeforeCreate = factclinicalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactclinicalMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factclinicalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Factclinical in the database
        List<Factclinical> factclinicalList = factclinicalRepository.findAll();
        assertThat(factclinicalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFactclinicals() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList
        restFactclinicalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factclinical.getId().intValue())))
            .andExpect(jsonPath("$.[*].patientUID").value(hasItem(DEFAULT_PATIENT_UID)))
            .andExpect(jsonPath("$.[*].encounterID").value(hasItem(DEFAULT_ENCOUNTER_ID)))
            .andExpect(jsonPath("$.[*].observationID").value(hasItem(DEFAULT_OBSERVATION_ID)))
            .andExpect(jsonPath("$.[*].procedureID").value(hasItem(DEFAULT_PROCEDURE_ID)))
            .andExpect(jsonPath("$.[*].immunizationID").value(hasItem(DEFAULT_IMMUNIZATION_ID)))
            .andExpect(jsonPath("$.[*].medicationID").value(hasItem(DEFAULT_MEDICATION_ID)))
            .andExpect(jsonPath("$.[*].conditionID").value(hasItem(DEFAULT_CONDITION_ID)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getFactclinical() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get the factclinical
        restFactclinicalMockMvc
            .perform(get(ENTITY_API_URL_ID, factclinical.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(factclinical.getId().intValue()))
            .andExpect(jsonPath("$.patientUID").value(DEFAULT_PATIENT_UID))
            .andExpect(jsonPath("$.encounterID").value(DEFAULT_ENCOUNTER_ID))
            .andExpect(jsonPath("$.observationID").value(DEFAULT_OBSERVATION_ID))
            .andExpect(jsonPath("$.procedureID").value(DEFAULT_PROCEDURE_ID))
            .andExpect(jsonPath("$.immunizationID").value(DEFAULT_IMMUNIZATION_ID))
            .andExpect(jsonPath("$.medicationID").value(DEFAULT_MEDICATION_ID))
            .andExpect(jsonPath("$.conditionID").value(DEFAULT_CONDITION_ID))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getFactclinicalsByIdFiltering() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        Long id = factclinical.getId();

        defaultFactclinicalShouldBeFound("id.equals=" + id);
        defaultFactclinicalShouldNotBeFound("id.notEquals=" + id);

        defaultFactclinicalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFactclinicalShouldNotBeFound("id.greaterThan=" + id);

        defaultFactclinicalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFactclinicalShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByPatientUIDIsEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where patientUID equals to DEFAULT_PATIENT_UID
        defaultFactclinicalShouldBeFound("patientUID.equals=" + DEFAULT_PATIENT_UID);

        // Get all the factclinicalList where patientUID equals to UPDATED_PATIENT_UID
        defaultFactclinicalShouldNotBeFound("patientUID.equals=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByPatientUIDIsInShouldWork() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where patientUID in DEFAULT_PATIENT_UID or UPDATED_PATIENT_UID
        defaultFactclinicalShouldBeFound("patientUID.in=" + DEFAULT_PATIENT_UID + "," + UPDATED_PATIENT_UID);

        // Get all the factclinicalList where patientUID equals to UPDATED_PATIENT_UID
        defaultFactclinicalShouldNotBeFound("patientUID.in=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByPatientUIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where patientUID is not null
        defaultFactclinicalShouldBeFound("patientUID.specified=true");

        // Get all the factclinicalList where patientUID is null
        defaultFactclinicalShouldNotBeFound("patientUID.specified=false");
    }

    @Test
    @Transactional
    void getAllFactclinicalsByPatientUIDContainsSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where patientUID contains DEFAULT_PATIENT_UID
        defaultFactclinicalShouldBeFound("patientUID.contains=" + DEFAULT_PATIENT_UID);

        // Get all the factclinicalList where patientUID contains UPDATED_PATIENT_UID
        defaultFactclinicalShouldNotBeFound("patientUID.contains=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByPatientUIDNotContainsSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where patientUID does not contain DEFAULT_PATIENT_UID
        defaultFactclinicalShouldNotBeFound("patientUID.doesNotContain=" + DEFAULT_PATIENT_UID);

        // Get all the factclinicalList where patientUID does not contain UPDATED_PATIENT_UID
        defaultFactclinicalShouldBeFound("patientUID.doesNotContain=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByEncounterIDIsEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where encounterID equals to DEFAULT_ENCOUNTER_ID
        defaultFactclinicalShouldBeFound("encounterID.equals=" + DEFAULT_ENCOUNTER_ID);

        // Get all the factclinicalList where encounterID equals to UPDATED_ENCOUNTER_ID
        defaultFactclinicalShouldNotBeFound("encounterID.equals=" + UPDATED_ENCOUNTER_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByEncounterIDIsInShouldWork() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where encounterID in DEFAULT_ENCOUNTER_ID or UPDATED_ENCOUNTER_ID
        defaultFactclinicalShouldBeFound("encounterID.in=" + DEFAULT_ENCOUNTER_ID + "," + UPDATED_ENCOUNTER_ID);

        // Get all the factclinicalList where encounterID equals to UPDATED_ENCOUNTER_ID
        defaultFactclinicalShouldNotBeFound("encounterID.in=" + UPDATED_ENCOUNTER_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByEncounterIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where encounterID is not null
        defaultFactclinicalShouldBeFound("encounterID.specified=true");

        // Get all the factclinicalList where encounterID is null
        defaultFactclinicalShouldNotBeFound("encounterID.specified=false");
    }

    @Test
    @Transactional
    void getAllFactclinicalsByEncounterIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where encounterID is greater than or equal to DEFAULT_ENCOUNTER_ID
        defaultFactclinicalShouldBeFound("encounterID.greaterThanOrEqual=" + DEFAULT_ENCOUNTER_ID);

        // Get all the factclinicalList where encounterID is greater than or equal to UPDATED_ENCOUNTER_ID
        defaultFactclinicalShouldNotBeFound("encounterID.greaterThanOrEqual=" + UPDATED_ENCOUNTER_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByEncounterIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where encounterID is less than or equal to DEFAULT_ENCOUNTER_ID
        defaultFactclinicalShouldBeFound("encounterID.lessThanOrEqual=" + DEFAULT_ENCOUNTER_ID);

        // Get all the factclinicalList where encounterID is less than or equal to SMALLER_ENCOUNTER_ID
        defaultFactclinicalShouldNotBeFound("encounterID.lessThanOrEqual=" + SMALLER_ENCOUNTER_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByEncounterIDIsLessThanSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where encounterID is less than DEFAULT_ENCOUNTER_ID
        defaultFactclinicalShouldNotBeFound("encounterID.lessThan=" + DEFAULT_ENCOUNTER_ID);

        // Get all the factclinicalList where encounterID is less than UPDATED_ENCOUNTER_ID
        defaultFactclinicalShouldBeFound("encounterID.lessThan=" + UPDATED_ENCOUNTER_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByEncounterIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where encounterID is greater than DEFAULT_ENCOUNTER_ID
        defaultFactclinicalShouldNotBeFound("encounterID.greaterThan=" + DEFAULT_ENCOUNTER_ID);

        // Get all the factclinicalList where encounterID is greater than SMALLER_ENCOUNTER_ID
        defaultFactclinicalShouldBeFound("encounterID.greaterThan=" + SMALLER_ENCOUNTER_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByObservationIDIsEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where observationID equals to DEFAULT_OBSERVATION_ID
        defaultFactclinicalShouldBeFound("observationID.equals=" + DEFAULT_OBSERVATION_ID);

        // Get all the factclinicalList where observationID equals to UPDATED_OBSERVATION_ID
        defaultFactclinicalShouldNotBeFound("observationID.equals=" + UPDATED_OBSERVATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByObservationIDIsInShouldWork() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where observationID in DEFAULT_OBSERVATION_ID or UPDATED_OBSERVATION_ID
        defaultFactclinicalShouldBeFound("observationID.in=" + DEFAULT_OBSERVATION_ID + "," + UPDATED_OBSERVATION_ID);

        // Get all the factclinicalList where observationID equals to UPDATED_OBSERVATION_ID
        defaultFactclinicalShouldNotBeFound("observationID.in=" + UPDATED_OBSERVATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByObservationIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where observationID is not null
        defaultFactclinicalShouldBeFound("observationID.specified=true");

        // Get all the factclinicalList where observationID is null
        defaultFactclinicalShouldNotBeFound("observationID.specified=false");
    }

    @Test
    @Transactional
    void getAllFactclinicalsByObservationIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where observationID is greater than or equal to DEFAULT_OBSERVATION_ID
        defaultFactclinicalShouldBeFound("observationID.greaterThanOrEqual=" + DEFAULT_OBSERVATION_ID);

        // Get all the factclinicalList where observationID is greater than or equal to UPDATED_OBSERVATION_ID
        defaultFactclinicalShouldNotBeFound("observationID.greaterThanOrEqual=" + UPDATED_OBSERVATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByObservationIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where observationID is less than or equal to DEFAULT_OBSERVATION_ID
        defaultFactclinicalShouldBeFound("observationID.lessThanOrEqual=" + DEFAULT_OBSERVATION_ID);

        // Get all the factclinicalList where observationID is less than or equal to SMALLER_OBSERVATION_ID
        defaultFactclinicalShouldNotBeFound("observationID.lessThanOrEqual=" + SMALLER_OBSERVATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByObservationIDIsLessThanSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where observationID is less than DEFAULT_OBSERVATION_ID
        defaultFactclinicalShouldNotBeFound("observationID.lessThan=" + DEFAULT_OBSERVATION_ID);

        // Get all the factclinicalList where observationID is less than UPDATED_OBSERVATION_ID
        defaultFactclinicalShouldBeFound("observationID.lessThan=" + UPDATED_OBSERVATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByObservationIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where observationID is greater than DEFAULT_OBSERVATION_ID
        defaultFactclinicalShouldNotBeFound("observationID.greaterThan=" + DEFAULT_OBSERVATION_ID);

        // Get all the factclinicalList where observationID is greater than SMALLER_OBSERVATION_ID
        defaultFactclinicalShouldBeFound("observationID.greaterThan=" + SMALLER_OBSERVATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByProcedureIDIsEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where procedureID equals to DEFAULT_PROCEDURE_ID
        defaultFactclinicalShouldBeFound("procedureID.equals=" + DEFAULT_PROCEDURE_ID);

        // Get all the factclinicalList where procedureID equals to UPDATED_PROCEDURE_ID
        defaultFactclinicalShouldNotBeFound("procedureID.equals=" + UPDATED_PROCEDURE_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByProcedureIDIsInShouldWork() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where procedureID in DEFAULT_PROCEDURE_ID or UPDATED_PROCEDURE_ID
        defaultFactclinicalShouldBeFound("procedureID.in=" + DEFAULT_PROCEDURE_ID + "," + UPDATED_PROCEDURE_ID);

        // Get all the factclinicalList where procedureID equals to UPDATED_PROCEDURE_ID
        defaultFactclinicalShouldNotBeFound("procedureID.in=" + UPDATED_PROCEDURE_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByProcedureIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where procedureID is not null
        defaultFactclinicalShouldBeFound("procedureID.specified=true");

        // Get all the factclinicalList where procedureID is null
        defaultFactclinicalShouldNotBeFound("procedureID.specified=false");
    }

    @Test
    @Transactional
    void getAllFactclinicalsByProcedureIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where procedureID is greater than or equal to DEFAULT_PROCEDURE_ID
        defaultFactclinicalShouldBeFound("procedureID.greaterThanOrEqual=" + DEFAULT_PROCEDURE_ID);

        // Get all the factclinicalList where procedureID is greater than or equal to UPDATED_PROCEDURE_ID
        defaultFactclinicalShouldNotBeFound("procedureID.greaterThanOrEqual=" + UPDATED_PROCEDURE_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByProcedureIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where procedureID is less than or equal to DEFAULT_PROCEDURE_ID
        defaultFactclinicalShouldBeFound("procedureID.lessThanOrEqual=" + DEFAULT_PROCEDURE_ID);

        // Get all the factclinicalList where procedureID is less than or equal to SMALLER_PROCEDURE_ID
        defaultFactclinicalShouldNotBeFound("procedureID.lessThanOrEqual=" + SMALLER_PROCEDURE_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByProcedureIDIsLessThanSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where procedureID is less than DEFAULT_PROCEDURE_ID
        defaultFactclinicalShouldNotBeFound("procedureID.lessThan=" + DEFAULT_PROCEDURE_ID);

        // Get all the factclinicalList where procedureID is less than UPDATED_PROCEDURE_ID
        defaultFactclinicalShouldBeFound("procedureID.lessThan=" + UPDATED_PROCEDURE_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByProcedureIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where procedureID is greater than DEFAULT_PROCEDURE_ID
        defaultFactclinicalShouldNotBeFound("procedureID.greaterThan=" + DEFAULT_PROCEDURE_ID);

        // Get all the factclinicalList where procedureID is greater than SMALLER_PROCEDURE_ID
        defaultFactclinicalShouldBeFound("procedureID.greaterThan=" + SMALLER_PROCEDURE_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByImmunizationIDIsEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where immunizationID equals to DEFAULT_IMMUNIZATION_ID
        defaultFactclinicalShouldBeFound("immunizationID.equals=" + DEFAULT_IMMUNIZATION_ID);

        // Get all the factclinicalList where immunizationID equals to UPDATED_IMMUNIZATION_ID
        defaultFactclinicalShouldNotBeFound("immunizationID.equals=" + UPDATED_IMMUNIZATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByImmunizationIDIsInShouldWork() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where immunizationID in DEFAULT_IMMUNIZATION_ID or UPDATED_IMMUNIZATION_ID
        defaultFactclinicalShouldBeFound("immunizationID.in=" + DEFAULT_IMMUNIZATION_ID + "," + UPDATED_IMMUNIZATION_ID);

        // Get all the factclinicalList where immunizationID equals to UPDATED_IMMUNIZATION_ID
        defaultFactclinicalShouldNotBeFound("immunizationID.in=" + UPDATED_IMMUNIZATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByImmunizationIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where immunizationID is not null
        defaultFactclinicalShouldBeFound("immunizationID.specified=true");

        // Get all the factclinicalList where immunizationID is null
        defaultFactclinicalShouldNotBeFound("immunizationID.specified=false");
    }

    @Test
    @Transactional
    void getAllFactclinicalsByImmunizationIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where immunizationID is greater than or equal to DEFAULT_IMMUNIZATION_ID
        defaultFactclinicalShouldBeFound("immunizationID.greaterThanOrEqual=" + DEFAULT_IMMUNIZATION_ID);

        // Get all the factclinicalList where immunizationID is greater than or equal to UPDATED_IMMUNIZATION_ID
        defaultFactclinicalShouldNotBeFound("immunizationID.greaterThanOrEqual=" + UPDATED_IMMUNIZATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByImmunizationIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where immunizationID is less than or equal to DEFAULT_IMMUNIZATION_ID
        defaultFactclinicalShouldBeFound("immunizationID.lessThanOrEqual=" + DEFAULT_IMMUNIZATION_ID);

        // Get all the factclinicalList where immunizationID is less than or equal to SMALLER_IMMUNIZATION_ID
        defaultFactclinicalShouldNotBeFound("immunizationID.lessThanOrEqual=" + SMALLER_IMMUNIZATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByImmunizationIDIsLessThanSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where immunizationID is less than DEFAULT_IMMUNIZATION_ID
        defaultFactclinicalShouldNotBeFound("immunizationID.lessThan=" + DEFAULT_IMMUNIZATION_ID);

        // Get all the factclinicalList where immunizationID is less than UPDATED_IMMUNIZATION_ID
        defaultFactclinicalShouldBeFound("immunizationID.lessThan=" + UPDATED_IMMUNIZATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByImmunizationIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where immunizationID is greater than DEFAULT_IMMUNIZATION_ID
        defaultFactclinicalShouldNotBeFound("immunizationID.greaterThan=" + DEFAULT_IMMUNIZATION_ID);

        // Get all the factclinicalList where immunizationID is greater than SMALLER_IMMUNIZATION_ID
        defaultFactclinicalShouldBeFound("immunizationID.greaterThan=" + SMALLER_IMMUNIZATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByMedicationIDIsEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where medicationID equals to DEFAULT_MEDICATION_ID
        defaultFactclinicalShouldBeFound("medicationID.equals=" + DEFAULT_MEDICATION_ID);

        // Get all the factclinicalList where medicationID equals to UPDATED_MEDICATION_ID
        defaultFactclinicalShouldNotBeFound("medicationID.equals=" + UPDATED_MEDICATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByMedicationIDIsInShouldWork() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where medicationID in DEFAULT_MEDICATION_ID or UPDATED_MEDICATION_ID
        defaultFactclinicalShouldBeFound("medicationID.in=" + DEFAULT_MEDICATION_ID + "," + UPDATED_MEDICATION_ID);

        // Get all the factclinicalList where medicationID equals to UPDATED_MEDICATION_ID
        defaultFactclinicalShouldNotBeFound("medicationID.in=" + UPDATED_MEDICATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByMedicationIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where medicationID is not null
        defaultFactclinicalShouldBeFound("medicationID.specified=true");

        // Get all the factclinicalList where medicationID is null
        defaultFactclinicalShouldNotBeFound("medicationID.specified=false");
    }

    @Test
    @Transactional
    void getAllFactclinicalsByMedicationIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where medicationID is greater than or equal to DEFAULT_MEDICATION_ID
        defaultFactclinicalShouldBeFound("medicationID.greaterThanOrEqual=" + DEFAULT_MEDICATION_ID);

        // Get all the factclinicalList where medicationID is greater than or equal to UPDATED_MEDICATION_ID
        defaultFactclinicalShouldNotBeFound("medicationID.greaterThanOrEqual=" + UPDATED_MEDICATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByMedicationIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where medicationID is less than or equal to DEFAULT_MEDICATION_ID
        defaultFactclinicalShouldBeFound("medicationID.lessThanOrEqual=" + DEFAULT_MEDICATION_ID);

        // Get all the factclinicalList where medicationID is less than or equal to SMALLER_MEDICATION_ID
        defaultFactclinicalShouldNotBeFound("medicationID.lessThanOrEqual=" + SMALLER_MEDICATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByMedicationIDIsLessThanSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where medicationID is less than DEFAULT_MEDICATION_ID
        defaultFactclinicalShouldNotBeFound("medicationID.lessThan=" + DEFAULT_MEDICATION_ID);

        // Get all the factclinicalList where medicationID is less than UPDATED_MEDICATION_ID
        defaultFactclinicalShouldBeFound("medicationID.lessThan=" + UPDATED_MEDICATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByMedicationIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where medicationID is greater than DEFAULT_MEDICATION_ID
        defaultFactclinicalShouldNotBeFound("medicationID.greaterThan=" + DEFAULT_MEDICATION_ID);

        // Get all the factclinicalList where medicationID is greater than SMALLER_MEDICATION_ID
        defaultFactclinicalShouldBeFound("medicationID.greaterThan=" + SMALLER_MEDICATION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByConditionIDIsEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where conditionID equals to DEFAULT_CONDITION_ID
        defaultFactclinicalShouldBeFound("conditionID.equals=" + DEFAULT_CONDITION_ID);

        // Get all the factclinicalList where conditionID equals to UPDATED_CONDITION_ID
        defaultFactclinicalShouldNotBeFound("conditionID.equals=" + UPDATED_CONDITION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByConditionIDIsInShouldWork() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where conditionID in DEFAULT_CONDITION_ID or UPDATED_CONDITION_ID
        defaultFactclinicalShouldBeFound("conditionID.in=" + DEFAULT_CONDITION_ID + "," + UPDATED_CONDITION_ID);

        // Get all the factclinicalList where conditionID equals to UPDATED_CONDITION_ID
        defaultFactclinicalShouldNotBeFound("conditionID.in=" + UPDATED_CONDITION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByConditionIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where conditionID is not null
        defaultFactclinicalShouldBeFound("conditionID.specified=true");

        // Get all the factclinicalList where conditionID is null
        defaultFactclinicalShouldNotBeFound("conditionID.specified=false");
    }

    @Test
    @Transactional
    void getAllFactclinicalsByConditionIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where conditionID is greater than or equal to DEFAULT_CONDITION_ID
        defaultFactclinicalShouldBeFound("conditionID.greaterThanOrEqual=" + DEFAULT_CONDITION_ID);

        // Get all the factclinicalList where conditionID is greater than or equal to UPDATED_CONDITION_ID
        defaultFactclinicalShouldNotBeFound("conditionID.greaterThanOrEqual=" + UPDATED_CONDITION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByConditionIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where conditionID is less than or equal to DEFAULT_CONDITION_ID
        defaultFactclinicalShouldBeFound("conditionID.lessThanOrEqual=" + DEFAULT_CONDITION_ID);

        // Get all the factclinicalList where conditionID is less than or equal to SMALLER_CONDITION_ID
        defaultFactclinicalShouldNotBeFound("conditionID.lessThanOrEqual=" + SMALLER_CONDITION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByConditionIDIsLessThanSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where conditionID is less than DEFAULT_CONDITION_ID
        defaultFactclinicalShouldNotBeFound("conditionID.lessThan=" + DEFAULT_CONDITION_ID);

        // Get all the factclinicalList where conditionID is less than UPDATED_CONDITION_ID
        defaultFactclinicalShouldBeFound("conditionID.lessThan=" + UPDATED_CONDITION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByConditionIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where conditionID is greater than DEFAULT_CONDITION_ID
        defaultFactclinicalShouldNotBeFound("conditionID.greaterThan=" + DEFAULT_CONDITION_ID);

        // Get all the factclinicalList where conditionID is greater than SMALLER_CONDITION_ID
        defaultFactclinicalShouldBeFound("conditionID.greaterThan=" + SMALLER_CONDITION_ID);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where date equals to DEFAULT_DATE
        defaultFactclinicalShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the factclinicalList where date equals to UPDATED_DATE
        defaultFactclinicalShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where date in DEFAULT_DATE or UPDATED_DATE
        defaultFactclinicalShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the factclinicalList where date equals to UPDATED_DATE
        defaultFactclinicalShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where date is not null
        defaultFactclinicalShouldBeFound("date.specified=true");

        // Get all the factclinicalList where date is null
        defaultFactclinicalShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllFactclinicalsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where date is greater than or equal to DEFAULT_DATE
        defaultFactclinicalShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the factclinicalList where date is greater than or equal to UPDATED_DATE
        defaultFactclinicalShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where date is less than or equal to DEFAULT_DATE
        defaultFactclinicalShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the factclinicalList where date is less than or equal to SMALLER_DATE
        defaultFactclinicalShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where date is less than DEFAULT_DATE
        defaultFactclinicalShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the factclinicalList where date is less than UPDATED_DATE
        defaultFactclinicalShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllFactclinicalsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        // Get all the factclinicalList where date is greater than DEFAULT_DATE
        defaultFactclinicalShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the factclinicalList where date is greater than SMALLER_DATE
        defaultFactclinicalShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFactclinicalShouldBeFound(String filter) throws Exception {
        restFactclinicalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factclinical.getId().intValue())))
            .andExpect(jsonPath("$.[*].patientUID").value(hasItem(DEFAULT_PATIENT_UID)))
            .andExpect(jsonPath("$.[*].encounterID").value(hasItem(DEFAULT_ENCOUNTER_ID)))
            .andExpect(jsonPath("$.[*].observationID").value(hasItem(DEFAULT_OBSERVATION_ID)))
            .andExpect(jsonPath("$.[*].procedureID").value(hasItem(DEFAULT_PROCEDURE_ID)))
            .andExpect(jsonPath("$.[*].immunizationID").value(hasItem(DEFAULT_IMMUNIZATION_ID)))
            .andExpect(jsonPath("$.[*].medicationID").value(hasItem(DEFAULT_MEDICATION_ID)))
            .andExpect(jsonPath("$.[*].conditionID").value(hasItem(DEFAULT_CONDITION_ID)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));

        // Check, that the count call also returns 1
        restFactclinicalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFactclinicalShouldNotBeFound(String filter) throws Exception {
        restFactclinicalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFactclinicalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFactclinical() throws Exception {
        // Get the factclinical
        restFactclinicalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFactclinical() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        int databaseSizeBeforeUpdate = factclinicalRepository.findAll().size();

        // Update the factclinical
        Factclinical updatedFactclinical = factclinicalRepository.findById(factclinical.getId()).get();
        // Disconnect from session so that the updates on updatedFactclinical are not directly saved in db
        em.detach(updatedFactclinical);
        updatedFactclinical
            .patientUID(UPDATED_PATIENT_UID)
            .encounterID(UPDATED_ENCOUNTER_ID)
            .observationID(UPDATED_OBSERVATION_ID)
            .procedureID(UPDATED_PROCEDURE_ID)
            .immunizationID(UPDATED_IMMUNIZATION_ID)
            .medicationID(UPDATED_MEDICATION_ID)
            .conditionID(UPDATED_CONDITION_ID)
            .date(UPDATED_DATE);
        FactclinicalDTO factclinicalDTO = factclinicalMapper.toDto(updatedFactclinical);

        restFactclinicalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, factclinicalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factclinicalDTO))
            )
            .andExpect(status().isOk());

        // Validate the Factclinical in the database
        List<Factclinical> factclinicalList = factclinicalRepository.findAll();
        assertThat(factclinicalList).hasSize(databaseSizeBeforeUpdate);
        Factclinical testFactclinical = factclinicalList.get(factclinicalList.size() - 1);
        assertThat(testFactclinical.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
        assertThat(testFactclinical.getEncounterID()).isEqualTo(UPDATED_ENCOUNTER_ID);
        assertThat(testFactclinical.getObservationID()).isEqualTo(UPDATED_OBSERVATION_ID);
        assertThat(testFactclinical.getProcedureID()).isEqualTo(UPDATED_PROCEDURE_ID);
        assertThat(testFactclinical.getImmunizationID()).isEqualTo(UPDATED_IMMUNIZATION_ID);
        assertThat(testFactclinical.getMedicationID()).isEqualTo(UPDATED_MEDICATION_ID);
        assertThat(testFactclinical.getConditionID()).isEqualTo(UPDATED_CONDITION_ID);
        assertThat(testFactclinical.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingFactclinical() throws Exception {
        int databaseSizeBeforeUpdate = factclinicalRepository.findAll().size();
        factclinical.setId(count.incrementAndGet());

        // Create the Factclinical
        FactclinicalDTO factclinicalDTO = factclinicalMapper.toDto(factclinical);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactclinicalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, factclinicalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factclinicalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Factclinical in the database
        List<Factclinical> factclinicalList = factclinicalRepository.findAll();
        assertThat(factclinicalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFactclinical() throws Exception {
        int databaseSizeBeforeUpdate = factclinicalRepository.findAll().size();
        factclinical.setId(count.incrementAndGet());

        // Create the Factclinical
        FactclinicalDTO factclinicalDTO = factclinicalMapper.toDto(factclinical);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactclinicalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(factclinicalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Factclinical in the database
        List<Factclinical> factclinicalList = factclinicalRepository.findAll();
        assertThat(factclinicalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFactclinical() throws Exception {
        int databaseSizeBeforeUpdate = factclinicalRepository.findAll().size();
        factclinical.setId(count.incrementAndGet());

        // Create the Factclinical
        FactclinicalDTO factclinicalDTO = factclinicalMapper.toDto(factclinical);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactclinicalMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(factclinicalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Factclinical in the database
        List<Factclinical> factclinicalList = factclinicalRepository.findAll();
        assertThat(factclinicalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFactclinicalWithPatch() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        int databaseSizeBeforeUpdate = factclinicalRepository.findAll().size();

        // Update the factclinical using partial update
        Factclinical partialUpdatedFactclinical = new Factclinical();
        partialUpdatedFactclinical.setId(factclinical.getId());

        partialUpdatedFactclinical
            .patientUID(UPDATED_PATIENT_UID)
            .encounterID(UPDATED_ENCOUNTER_ID)
            .immunizationID(UPDATED_IMMUNIZATION_ID)
            .conditionID(UPDATED_CONDITION_ID)
            .date(UPDATED_DATE);

        restFactclinicalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactclinical.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFactclinical))
            )
            .andExpect(status().isOk());

        // Validate the Factclinical in the database
        List<Factclinical> factclinicalList = factclinicalRepository.findAll();
        assertThat(factclinicalList).hasSize(databaseSizeBeforeUpdate);
        Factclinical testFactclinical = factclinicalList.get(factclinicalList.size() - 1);
        assertThat(testFactclinical.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
        assertThat(testFactclinical.getEncounterID()).isEqualTo(UPDATED_ENCOUNTER_ID);
        assertThat(testFactclinical.getObservationID()).isEqualTo(DEFAULT_OBSERVATION_ID);
        assertThat(testFactclinical.getProcedureID()).isEqualTo(DEFAULT_PROCEDURE_ID);
        assertThat(testFactclinical.getImmunizationID()).isEqualTo(UPDATED_IMMUNIZATION_ID);
        assertThat(testFactclinical.getMedicationID()).isEqualTo(DEFAULT_MEDICATION_ID);
        assertThat(testFactclinical.getConditionID()).isEqualTo(UPDATED_CONDITION_ID);
        assertThat(testFactclinical.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateFactclinicalWithPatch() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        int databaseSizeBeforeUpdate = factclinicalRepository.findAll().size();

        // Update the factclinical using partial update
        Factclinical partialUpdatedFactclinical = new Factclinical();
        partialUpdatedFactclinical.setId(factclinical.getId());

        partialUpdatedFactclinical
            .patientUID(UPDATED_PATIENT_UID)
            .encounterID(UPDATED_ENCOUNTER_ID)
            .observationID(UPDATED_OBSERVATION_ID)
            .procedureID(UPDATED_PROCEDURE_ID)
            .immunizationID(UPDATED_IMMUNIZATION_ID)
            .medicationID(UPDATED_MEDICATION_ID)
            .conditionID(UPDATED_CONDITION_ID)
            .date(UPDATED_DATE);

        restFactclinicalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFactclinical.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFactclinical))
            )
            .andExpect(status().isOk());

        // Validate the Factclinical in the database
        List<Factclinical> factclinicalList = factclinicalRepository.findAll();
        assertThat(factclinicalList).hasSize(databaseSizeBeforeUpdate);
        Factclinical testFactclinical = factclinicalList.get(factclinicalList.size() - 1);
        assertThat(testFactclinical.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
        assertThat(testFactclinical.getEncounterID()).isEqualTo(UPDATED_ENCOUNTER_ID);
        assertThat(testFactclinical.getObservationID()).isEqualTo(UPDATED_OBSERVATION_ID);
        assertThat(testFactclinical.getProcedureID()).isEqualTo(UPDATED_PROCEDURE_ID);
        assertThat(testFactclinical.getImmunizationID()).isEqualTo(UPDATED_IMMUNIZATION_ID);
        assertThat(testFactclinical.getMedicationID()).isEqualTo(UPDATED_MEDICATION_ID);
        assertThat(testFactclinical.getConditionID()).isEqualTo(UPDATED_CONDITION_ID);
        assertThat(testFactclinical.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingFactclinical() throws Exception {
        int databaseSizeBeforeUpdate = factclinicalRepository.findAll().size();
        factclinical.setId(count.incrementAndGet());

        // Create the Factclinical
        FactclinicalDTO factclinicalDTO = factclinicalMapper.toDto(factclinical);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactclinicalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, factclinicalDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(factclinicalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Factclinical in the database
        List<Factclinical> factclinicalList = factclinicalRepository.findAll();
        assertThat(factclinicalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFactclinical() throws Exception {
        int databaseSizeBeforeUpdate = factclinicalRepository.findAll().size();
        factclinical.setId(count.incrementAndGet());

        // Create the Factclinical
        FactclinicalDTO factclinicalDTO = factclinicalMapper.toDto(factclinical);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactclinicalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(factclinicalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Factclinical in the database
        List<Factclinical> factclinicalList = factclinicalRepository.findAll();
        assertThat(factclinicalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFactclinical() throws Exception {
        int databaseSizeBeforeUpdate = factclinicalRepository.findAll().size();
        factclinical.setId(count.incrementAndGet());

        // Create the Factclinical
        FactclinicalDTO factclinicalDTO = factclinicalMapper.toDto(factclinical);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFactclinicalMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(factclinicalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Factclinical in the database
        List<Factclinical> factclinicalList = factclinicalRepository.findAll();
        assertThat(factclinicalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFactclinical() throws Exception {
        // Initialize the database
        factclinicalRepository.saveAndFlush(factclinical);

        int databaseSizeBeforeDelete = factclinicalRepository.findAll().size();

        // Delete the factclinical
        restFactclinicalMockMvc
            .perform(delete(ENTITY_API_URL_ID, factclinical.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Factclinical> factclinicalList = factclinicalRepository.findAll();
        assertThat(factclinicalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
