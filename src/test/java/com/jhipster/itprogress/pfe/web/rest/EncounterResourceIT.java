package com.jhipster.itprogress.pfe.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.itprogress.pfe.IntegrationTest;
import com.jhipster.itprogress.pfe.domain.Encounter;
import com.jhipster.itprogress.pfe.repository.EncounterRepository;
import com.jhipster.itprogress.pfe.service.criteria.EncounterCriteria;
import com.jhipster.itprogress.pfe.service.dto.EncounterDTO;
import com.jhipster.itprogress.pfe.service.mapper.EncounterMapper;
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
 * Integration tests for the {@link EncounterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EncounterResourceIT {

    private static final String DEFAULT_ENCOUNTERS_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_ENCOUNTERS_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_ENCOUNTER_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_ENCOUNTER_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_ENCOUNTER_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_ENCOUNTER_PROVIDER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PATIENT_UID = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_UID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/encounters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EncounterRepository encounterRepository;

    @Autowired
    private EncounterMapper encounterMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEncounterMockMvc;

    private Encounter encounter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Encounter createEntity(EntityManager em) {
        Encounter encounter = new Encounter()
            .encountersText(DEFAULT_ENCOUNTERS_TEXT)
            .encounterLocation(DEFAULT_ENCOUNTER_LOCATION)
            .encounterProvider(DEFAULT_ENCOUNTER_PROVIDER)
            .date(DEFAULT_DATE)
            .patientUID(DEFAULT_PATIENT_UID);
        return encounter;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Encounter createUpdatedEntity(EntityManager em) {
        Encounter encounter = new Encounter()
            .encountersText(UPDATED_ENCOUNTERS_TEXT)
            .encounterLocation(UPDATED_ENCOUNTER_LOCATION)
            .encounterProvider(UPDATED_ENCOUNTER_PROVIDER)
            .date(UPDATED_DATE)
            .patientUID(UPDATED_PATIENT_UID);
        return encounter;
    }

    @BeforeEach
    public void initTest() {
        encounter = createEntity(em);
    }

    @Test
    @Transactional
    void createEncounter() throws Exception {
        int databaseSizeBeforeCreate = encounterRepository.findAll().size();
        // Create the Encounter
        EncounterDTO encounterDTO = encounterMapper.toDto(encounter);
        restEncounterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(encounterDTO)))
            .andExpect(status().isCreated());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeCreate + 1);
        Encounter testEncounter = encounterList.get(encounterList.size() - 1);
        assertThat(testEncounter.getEncountersText()).isEqualTo(DEFAULT_ENCOUNTERS_TEXT);
        assertThat(testEncounter.getEncounterLocation()).isEqualTo(DEFAULT_ENCOUNTER_LOCATION);
        assertThat(testEncounter.getEncounterProvider()).isEqualTo(DEFAULT_ENCOUNTER_PROVIDER);
        assertThat(testEncounter.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEncounter.getPatientUID()).isEqualTo(DEFAULT_PATIENT_UID);
    }

    @Test
    @Transactional
    void createEncounterWithExistingId() throws Exception {
        // Create the Encounter with an existing ID
        encounter.setId(1L);
        EncounterDTO encounterDTO = encounterMapper.toDto(encounter);

        int databaseSizeBeforeCreate = encounterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEncounterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(encounterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEncounters() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList
        restEncounterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encounter.getId().intValue())))
            .andExpect(jsonPath("$.[*].encountersText").value(hasItem(DEFAULT_ENCOUNTERS_TEXT)))
            .andExpect(jsonPath("$.[*].encounterLocation").value(hasItem(DEFAULT_ENCOUNTER_LOCATION)))
            .andExpect(jsonPath("$.[*].encounterProvider").value(hasItem(DEFAULT_ENCOUNTER_PROVIDER)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].patientUID").value(hasItem(DEFAULT_PATIENT_UID)));
    }

    @Test
    @Transactional
    void getEncounter() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get the encounter
        restEncounterMockMvc
            .perform(get(ENTITY_API_URL_ID, encounter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(encounter.getId().intValue()))
            .andExpect(jsonPath("$.encountersText").value(DEFAULT_ENCOUNTERS_TEXT))
            .andExpect(jsonPath("$.encounterLocation").value(DEFAULT_ENCOUNTER_LOCATION))
            .andExpect(jsonPath("$.encounterProvider").value(DEFAULT_ENCOUNTER_PROVIDER))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.patientUID").value(DEFAULT_PATIENT_UID));
    }

    @Test
    @Transactional
    void getEncountersByIdFiltering() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        Long id = encounter.getId();

        defaultEncounterShouldBeFound("id.equals=" + id);
        defaultEncounterShouldNotBeFound("id.notEquals=" + id);

        defaultEncounterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEncounterShouldNotBeFound("id.greaterThan=" + id);

        defaultEncounterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEncounterShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEncountersByEncountersTextIsEqualToSomething() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where encountersText equals to DEFAULT_ENCOUNTERS_TEXT
        defaultEncounterShouldBeFound("encountersText.equals=" + DEFAULT_ENCOUNTERS_TEXT);

        // Get all the encounterList where encountersText equals to UPDATED_ENCOUNTERS_TEXT
        defaultEncounterShouldNotBeFound("encountersText.equals=" + UPDATED_ENCOUNTERS_TEXT);
    }

    @Test
    @Transactional
    void getAllEncountersByEncountersTextIsInShouldWork() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where encountersText in DEFAULT_ENCOUNTERS_TEXT or UPDATED_ENCOUNTERS_TEXT
        defaultEncounterShouldBeFound("encountersText.in=" + DEFAULT_ENCOUNTERS_TEXT + "," + UPDATED_ENCOUNTERS_TEXT);

        // Get all the encounterList where encountersText equals to UPDATED_ENCOUNTERS_TEXT
        defaultEncounterShouldNotBeFound("encountersText.in=" + UPDATED_ENCOUNTERS_TEXT);
    }

    @Test
    @Transactional
    void getAllEncountersByEncountersTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where encountersText is not null
        defaultEncounterShouldBeFound("encountersText.specified=true");

        // Get all the encounterList where encountersText is null
        defaultEncounterShouldNotBeFound("encountersText.specified=false");
    }

    @Test
    @Transactional
    void getAllEncountersByEncountersTextContainsSomething() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where encountersText contains DEFAULT_ENCOUNTERS_TEXT
        defaultEncounterShouldBeFound("encountersText.contains=" + DEFAULT_ENCOUNTERS_TEXT);

        // Get all the encounterList where encountersText contains UPDATED_ENCOUNTERS_TEXT
        defaultEncounterShouldNotBeFound("encountersText.contains=" + UPDATED_ENCOUNTERS_TEXT);
    }

    @Test
    @Transactional
    void getAllEncountersByEncountersTextNotContainsSomething() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where encountersText does not contain DEFAULT_ENCOUNTERS_TEXT
        defaultEncounterShouldNotBeFound("encountersText.doesNotContain=" + DEFAULT_ENCOUNTERS_TEXT);

        // Get all the encounterList where encountersText does not contain UPDATED_ENCOUNTERS_TEXT
        defaultEncounterShouldBeFound("encountersText.doesNotContain=" + UPDATED_ENCOUNTERS_TEXT);
    }

    @Test
    @Transactional
    void getAllEncountersByEncounterLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where encounterLocation equals to DEFAULT_ENCOUNTER_LOCATION
        defaultEncounterShouldBeFound("encounterLocation.equals=" + DEFAULT_ENCOUNTER_LOCATION);

        // Get all the encounterList where encounterLocation equals to UPDATED_ENCOUNTER_LOCATION
        defaultEncounterShouldNotBeFound("encounterLocation.equals=" + UPDATED_ENCOUNTER_LOCATION);
    }

    @Test
    @Transactional
    void getAllEncountersByEncounterLocationIsInShouldWork() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where encounterLocation in DEFAULT_ENCOUNTER_LOCATION or UPDATED_ENCOUNTER_LOCATION
        defaultEncounterShouldBeFound("encounterLocation.in=" + DEFAULT_ENCOUNTER_LOCATION + "," + UPDATED_ENCOUNTER_LOCATION);

        // Get all the encounterList where encounterLocation equals to UPDATED_ENCOUNTER_LOCATION
        defaultEncounterShouldNotBeFound("encounterLocation.in=" + UPDATED_ENCOUNTER_LOCATION);
    }

    @Test
    @Transactional
    void getAllEncountersByEncounterLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where encounterLocation is not null
        defaultEncounterShouldBeFound("encounterLocation.specified=true");

        // Get all the encounterList where encounterLocation is null
        defaultEncounterShouldNotBeFound("encounterLocation.specified=false");
    }

    @Test
    @Transactional
    void getAllEncountersByEncounterLocationContainsSomething() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where encounterLocation contains DEFAULT_ENCOUNTER_LOCATION
        defaultEncounterShouldBeFound("encounterLocation.contains=" + DEFAULT_ENCOUNTER_LOCATION);

        // Get all the encounterList where encounterLocation contains UPDATED_ENCOUNTER_LOCATION
        defaultEncounterShouldNotBeFound("encounterLocation.contains=" + UPDATED_ENCOUNTER_LOCATION);
    }

    @Test
    @Transactional
    void getAllEncountersByEncounterLocationNotContainsSomething() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where encounterLocation does not contain DEFAULT_ENCOUNTER_LOCATION
        defaultEncounterShouldNotBeFound("encounterLocation.doesNotContain=" + DEFAULT_ENCOUNTER_LOCATION);

        // Get all the encounterList where encounterLocation does not contain UPDATED_ENCOUNTER_LOCATION
        defaultEncounterShouldBeFound("encounterLocation.doesNotContain=" + UPDATED_ENCOUNTER_LOCATION);
    }

    @Test
    @Transactional
    void getAllEncountersByEncounterProviderIsEqualToSomething() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where encounterProvider equals to DEFAULT_ENCOUNTER_PROVIDER
        defaultEncounterShouldBeFound("encounterProvider.equals=" + DEFAULT_ENCOUNTER_PROVIDER);

        // Get all the encounterList where encounterProvider equals to UPDATED_ENCOUNTER_PROVIDER
        defaultEncounterShouldNotBeFound("encounterProvider.equals=" + UPDATED_ENCOUNTER_PROVIDER);
    }

    @Test
    @Transactional
    void getAllEncountersByEncounterProviderIsInShouldWork() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where encounterProvider in DEFAULT_ENCOUNTER_PROVIDER or UPDATED_ENCOUNTER_PROVIDER
        defaultEncounterShouldBeFound("encounterProvider.in=" + DEFAULT_ENCOUNTER_PROVIDER + "," + UPDATED_ENCOUNTER_PROVIDER);

        // Get all the encounterList where encounterProvider equals to UPDATED_ENCOUNTER_PROVIDER
        defaultEncounterShouldNotBeFound("encounterProvider.in=" + UPDATED_ENCOUNTER_PROVIDER);
    }

    @Test
    @Transactional
    void getAllEncountersByEncounterProviderIsNullOrNotNull() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where encounterProvider is not null
        defaultEncounterShouldBeFound("encounterProvider.specified=true");

        // Get all the encounterList where encounterProvider is null
        defaultEncounterShouldNotBeFound("encounterProvider.specified=false");
    }

    @Test
    @Transactional
    void getAllEncountersByEncounterProviderContainsSomething() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where encounterProvider contains DEFAULT_ENCOUNTER_PROVIDER
        defaultEncounterShouldBeFound("encounterProvider.contains=" + DEFAULT_ENCOUNTER_PROVIDER);

        // Get all the encounterList where encounterProvider contains UPDATED_ENCOUNTER_PROVIDER
        defaultEncounterShouldNotBeFound("encounterProvider.contains=" + UPDATED_ENCOUNTER_PROVIDER);
    }

    @Test
    @Transactional
    void getAllEncountersByEncounterProviderNotContainsSomething() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where encounterProvider does not contain DEFAULT_ENCOUNTER_PROVIDER
        defaultEncounterShouldNotBeFound("encounterProvider.doesNotContain=" + DEFAULT_ENCOUNTER_PROVIDER);

        // Get all the encounterList where encounterProvider does not contain UPDATED_ENCOUNTER_PROVIDER
        defaultEncounterShouldBeFound("encounterProvider.doesNotContain=" + UPDATED_ENCOUNTER_PROVIDER);
    }

    @Test
    @Transactional
    void getAllEncountersByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where date equals to DEFAULT_DATE
        defaultEncounterShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the encounterList where date equals to UPDATED_DATE
        defaultEncounterShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEncountersByDateIsInShouldWork() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where date in DEFAULT_DATE or UPDATED_DATE
        defaultEncounterShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the encounterList where date equals to UPDATED_DATE
        defaultEncounterShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEncountersByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where date is not null
        defaultEncounterShouldBeFound("date.specified=true");

        // Get all the encounterList where date is null
        defaultEncounterShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllEncountersByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where date is greater than or equal to DEFAULT_DATE
        defaultEncounterShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the encounterList where date is greater than or equal to UPDATED_DATE
        defaultEncounterShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEncountersByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where date is less than or equal to DEFAULT_DATE
        defaultEncounterShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the encounterList where date is less than or equal to SMALLER_DATE
        defaultEncounterShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllEncountersByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where date is less than DEFAULT_DATE
        defaultEncounterShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the encounterList where date is less than UPDATED_DATE
        defaultEncounterShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEncountersByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where date is greater than DEFAULT_DATE
        defaultEncounterShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the encounterList where date is greater than SMALLER_DATE
        defaultEncounterShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllEncountersByPatientUIDIsEqualToSomething() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where patientUID equals to DEFAULT_PATIENT_UID
        defaultEncounterShouldBeFound("patientUID.equals=" + DEFAULT_PATIENT_UID);

        // Get all the encounterList where patientUID equals to UPDATED_PATIENT_UID
        defaultEncounterShouldNotBeFound("patientUID.equals=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllEncountersByPatientUIDIsInShouldWork() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where patientUID in DEFAULT_PATIENT_UID or UPDATED_PATIENT_UID
        defaultEncounterShouldBeFound("patientUID.in=" + DEFAULT_PATIENT_UID + "," + UPDATED_PATIENT_UID);

        // Get all the encounterList where patientUID equals to UPDATED_PATIENT_UID
        defaultEncounterShouldNotBeFound("patientUID.in=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllEncountersByPatientUIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where patientUID is not null
        defaultEncounterShouldBeFound("patientUID.specified=true");

        // Get all the encounterList where patientUID is null
        defaultEncounterShouldNotBeFound("patientUID.specified=false");
    }

    @Test
    @Transactional
    void getAllEncountersByPatientUIDContainsSomething() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where patientUID contains DEFAULT_PATIENT_UID
        defaultEncounterShouldBeFound("patientUID.contains=" + DEFAULT_PATIENT_UID);

        // Get all the encounterList where patientUID contains UPDATED_PATIENT_UID
        defaultEncounterShouldNotBeFound("patientUID.contains=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllEncountersByPatientUIDNotContainsSomething() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList where patientUID does not contain DEFAULT_PATIENT_UID
        defaultEncounterShouldNotBeFound("patientUID.doesNotContain=" + DEFAULT_PATIENT_UID);

        // Get all the encounterList where patientUID does not contain UPDATED_PATIENT_UID
        defaultEncounterShouldBeFound("patientUID.doesNotContain=" + UPDATED_PATIENT_UID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEncounterShouldBeFound(String filter) throws Exception {
        restEncounterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encounter.getId().intValue())))
            .andExpect(jsonPath("$.[*].encountersText").value(hasItem(DEFAULT_ENCOUNTERS_TEXT)))
            .andExpect(jsonPath("$.[*].encounterLocation").value(hasItem(DEFAULT_ENCOUNTER_LOCATION)))
            .andExpect(jsonPath("$.[*].encounterProvider").value(hasItem(DEFAULT_ENCOUNTER_PROVIDER)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].patientUID").value(hasItem(DEFAULT_PATIENT_UID)));

        // Check, that the count call also returns 1
        restEncounterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEncounterShouldNotBeFound(String filter) throws Exception {
        restEncounterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEncounterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEncounter() throws Exception {
        // Get the encounter
        restEncounterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEncounter() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();

        // Update the encounter
        Encounter updatedEncounter = encounterRepository.findById(encounter.getId()).get();
        // Disconnect from session so that the updates on updatedEncounter are not directly saved in db
        em.detach(updatedEncounter);
        updatedEncounter
            .encountersText(UPDATED_ENCOUNTERS_TEXT)
            .encounterLocation(UPDATED_ENCOUNTER_LOCATION)
            .encounterProvider(UPDATED_ENCOUNTER_PROVIDER)
            .date(UPDATED_DATE)
            .patientUID(UPDATED_PATIENT_UID);
        EncounterDTO encounterDTO = encounterMapper.toDto(updatedEncounter);

        restEncounterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, encounterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encounterDTO))
            )
            .andExpect(status().isOk());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
        Encounter testEncounter = encounterList.get(encounterList.size() - 1);
        assertThat(testEncounter.getEncountersText()).isEqualTo(UPDATED_ENCOUNTERS_TEXT);
        assertThat(testEncounter.getEncounterLocation()).isEqualTo(UPDATED_ENCOUNTER_LOCATION);
        assertThat(testEncounter.getEncounterProvider()).isEqualTo(UPDATED_ENCOUNTER_PROVIDER);
        assertThat(testEncounter.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEncounter.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void putNonExistingEncounter() throws Exception {
        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();
        encounter.setId(count.incrementAndGet());

        // Create the Encounter
        EncounterDTO encounterDTO = encounterMapper.toDto(encounter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEncounterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, encounterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encounterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEncounter() throws Exception {
        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();
        encounter.setId(count.incrementAndGet());

        // Create the Encounter
        EncounterDTO encounterDTO = encounterMapper.toDto(encounter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncounterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encounterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEncounter() throws Exception {
        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();
        encounter.setId(count.incrementAndGet());

        // Create the Encounter
        EncounterDTO encounterDTO = encounterMapper.toDto(encounter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncounterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(encounterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEncounterWithPatch() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();

        // Update the encounter using partial update
        Encounter partialUpdatedEncounter = new Encounter();
        partialUpdatedEncounter.setId(encounter.getId());

        partialUpdatedEncounter
            .encountersText(UPDATED_ENCOUNTERS_TEXT)
            .encounterLocation(UPDATED_ENCOUNTER_LOCATION)
            .encounterProvider(UPDATED_ENCOUNTER_PROVIDER)
            .date(UPDATED_DATE)
            .patientUID(UPDATED_PATIENT_UID);

        restEncounterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEncounter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEncounter))
            )
            .andExpect(status().isOk());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
        Encounter testEncounter = encounterList.get(encounterList.size() - 1);
        assertThat(testEncounter.getEncountersText()).isEqualTo(UPDATED_ENCOUNTERS_TEXT);
        assertThat(testEncounter.getEncounterLocation()).isEqualTo(UPDATED_ENCOUNTER_LOCATION);
        assertThat(testEncounter.getEncounterProvider()).isEqualTo(UPDATED_ENCOUNTER_PROVIDER);
        assertThat(testEncounter.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEncounter.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void fullUpdateEncounterWithPatch() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();

        // Update the encounter using partial update
        Encounter partialUpdatedEncounter = new Encounter();
        partialUpdatedEncounter.setId(encounter.getId());

        partialUpdatedEncounter
            .encountersText(UPDATED_ENCOUNTERS_TEXT)
            .encounterLocation(UPDATED_ENCOUNTER_LOCATION)
            .encounterProvider(UPDATED_ENCOUNTER_PROVIDER)
            .date(UPDATED_DATE)
            .patientUID(UPDATED_PATIENT_UID);

        restEncounterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEncounter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEncounter))
            )
            .andExpect(status().isOk());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
        Encounter testEncounter = encounterList.get(encounterList.size() - 1);
        assertThat(testEncounter.getEncountersText()).isEqualTo(UPDATED_ENCOUNTERS_TEXT);
        assertThat(testEncounter.getEncounterLocation()).isEqualTo(UPDATED_ENCOUNTER_LOCATION);
        assertThat(testEncounter.getEncounterProvider()).isEqualTo(UPDATED_ENCOUNTER_PROVIDER);
        assertThat(testEncounter.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEncounter.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void patchNonExistingEncounter() throws Exception {
        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();
        encounter.setId(count.incrementAndGet());

        // Create the Encounter
        EncounterDTO encounterDTO = encounterMapper.toDto(encounter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEncounterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, encounterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(encounterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEncounter() throws Exception {
        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();
        encounter.setId(count.incrementAndGet());

        // Create the Encounter
        EncounterDTO encounterDTO = encounterMapper.toDto(encounter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncounterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(encounterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEncounter() throws Exception {
        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();
        encounter.setId(count.incrementAndGet());

        // Create the Encounter
        EncounterDTO encounterDTO = encounterMapper.toDto(encounter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncounterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(encounterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEncounter() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        int databaseSizeBeforeDelete = encounterRepository.findAll().size();

        // Delete the encounter
        restEncounterMockMvc
            .perform(delete(ENTITY_API_URL_ID, encounter.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
