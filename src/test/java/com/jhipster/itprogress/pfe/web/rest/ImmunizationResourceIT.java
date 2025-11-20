package com.jhipster.itprogress.pfe.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.itprogress.pfe.IntegrationTest;
import com.jhipster.itprogress.pfe.domain.Immunization;
import com.jhipster.itprogress.pfe.repository.ImmunizationRepository;
import com.jhipster.itprogress.pfe.service.criteria.ImmunizationCriteria;
import com.jhipster.itprogress.pfe.service.dto.ImmunizationDTO;
import com.jhipster.itprogress.pfe.service.mapper.ImmunizationMapper;
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
 * Integration tests for the {@link ImmunizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ImmunizationResourceIT {

    private static final String DEFAULT_IMMUNIZATION = "AAAAAAAAAA";
    private static final String UPDATED_IMMUNIZATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PATIENT_UID = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_UID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/immunizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ImmunizationRepository immunizationRepository;

    @Autowired
    private ImmunizationMapper immunizationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImmunizationMockMvc;

    private Immunization immunization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Immunization createEntity(EntityManager em) {
        Immunization immunization = new Immunization()
            .immunization(DEFAULT_IMMUNIZATION)
            .date(DEFAULT_DATE)
            .patientUID(DEFAULT_PATIENT_UID);
        return immunization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Immunization createUpdatedEntity(EntityManager em) {
        Immunization immunization = new Immunization()
            .immunization(UPDATED_IMMUNIZATION)
            .date(UPDATED_DATE)
            .patientUID(UPDATED_PATIENT_UID);
        return immunization;
    }

    @BeforeEach
    public void initTest() {
        immunization = createEntity(em);
    }

    @Test
    @Transactional
    void createImmunization() throws Exception {
        int databaseSizeBeforeCreate = immunizationRepository.findAll().size();
        // Create the Immunization
        ImmunizationDTO immunizationDTO = immunizationMapper.toDto(immunization);
        restImmunizationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(immunizationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Immunization in the database
        List<Immunization> immunizationList = immunizationRepository.findAll();
        assertThat(immunizationList).hasSize(databaseSizeBeforeCreate + 1);
        Immunization testImmunization = immunizationList.get(immunizationList.size() - 1);
        assertThat(testImmunization.getImmunization()).isEqualTo(DEFAULT_IMMUNIZATION);
        assertThat(testImmunization.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testImmunization.getPatientUID()).isEqualTo(DEFAULT_PATIENT_UID);
    }

    @Test
    @Transactional
    void createImmunizationWithExistingId() throws Exception {
        // Create the Immunization with an existing ID
        immunization.setId(1L);
        ImmunizationDTO immunizationDTO = immunizationMapper.toDto(immunization);

        int databaseSizeBeforeCreate = immunizationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restImmunizationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(immunizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Immunization in the database
        List<Immunization> immunizationList = immunizationRepository.findAll();
        assertThat(immunizationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllImmunizations() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList
        restImmunizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(immunization.getId().intValue())))
            .andExpect(jsonPath("$.[*].immunization").value(hasItem(DEFAULT_IMMUNIZATION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].patientUID").value(hasItem(DEFAULT_PATIENT_UID)));
    }

    @Test
    @Transactional
    void getImmunization() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get the immunization
        restImmunizationMockMvc
            .perform(get(ENTITY_API_URL_ID, immunization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(immunization.getId().intValue()))
            .andExpect(jsonPath("$.immunization").value(DEFAULT_IMMUNIZATION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.patientUID").value(DEFAULT_PATIENT_UID));
    }

    @Test
    @Transactional
    void getImmunizationsByIdFiltering() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        Long id = immunization.getId();

        defaultImmunizationShouldBeFound("id.equals=" + id);
        defaultImmunizationShouldNotBeFound("id.notEquals=" + id);

        defaultImmunizationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultImmunizationShouldNotBeFound("id.greaterThan=" + id);

        defaultImmunizationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultImmunizationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllImmunizationsByImmunizationIsEqualToSomething() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList where immunization equals to DEFAULT_IMMUNIZATION
        defaultImmunizationShouldBeFound("immunization.equals=" + DEFAULT_IMMUNIZATION);

        // Get all the immunizationList where immunization equals to UPDATED_IMMUNIZATION
        defaultImmunizationShouldNotBeFound("immunization.equals=" + UPDATED_IMMUNIZATION);
    }

    @Test
    @Transactional
    void getAllImmunizationsByImmunizationIsInShouldWork() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList where immunization in DEFAULT_IMMUNIZATION or UPDATED_IMMUNIZATION
        defaultImmunizationShouldBeFound("immunization.in=" + DEFAULT_IMMUNIZATION + "," + UPDATED_IMMUNIZATION);

        // Get all the immunizationList where immunization equals to UPDATED_IMMUNIZATION
        defaultImmunizationShouldNotBeFound("immunization.in=" + UPDATED_IMMUNIZATION);
    }

    @Test
    @Transactional
    void getAllImmunizationsByImmunizationIsNullOrNotNull() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList where immunization is not null
        defaultImmunizationShouldBeFound("immunization.specified=true");

        // Get all the immunizationList where immunization is null
        defaultImmunizationShouldNotBeFound("immunization.specified=false");
    }

    @Test
    @Transactional
    void getAllImmunizationsByImmunizationContainsSomething() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList where immunization contains DEFAULT_IMMUNIZATION
        defaultImmunizationShouldBeFound("immunization.contains=" + DEFAULT_IMMUNIZATION);

        // Get all the immunizationList where immunization contains UPDATED_IMMUNIZATION
        defaultImmunizationShouldNotBeFound("immunization.contains=" + UPDATED_IMMUNIZATION);
    }

    @Test
    @Transactional
    void getAllImmunizationsByImmunizationNotContainsSomething() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList where immunization does not contain DEFAULT_IMMUNIZATION
        defaultImmunizationShouldNotBeFound("immunization.doesNotContain=" + DEFAULT_IMMUNIZATION);

        // Get all the immunizationList where immunization does not contain UPDATED_IMMUNIZATION
        defaultImmunizationShouldBeFound("immunization.doesNotContain=" + UPDATED_IMMUNIZATION);
    }

    @Test
    @Transactional
    void getAllImmunizationsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList where date equals to DEFAULT_DATE
        defaultImmunizationShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the immunizationList where date equals to UPDATED_DATE
        defaultImmunizationShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllImmunizationsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList where date in DEFAULT_DATE or UPDATED_DATE
        defaultImmunizationShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the immunizationList where date equals to UPDATED_DATE
        defaultImmunizationShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllImmunizationsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList where date is not null
        defaultImmunizationShouldBeFound("date.specified=true");

        // Get all the immunizationList where date is null
        defaultImmunizationShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllImmunizationsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList where date is greater than or equal to DEFAULT_DATE
        defaultImmunizationShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the immunizationList where date is greater than or equal to UPDATED_DATE
        defaultImmunizationShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllImmunizationsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList where date is less than or equal to DEFAULT_DATE
        defaultImmunizationShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the immunizationList where date is less than or equal to SMALLER_DATE
        defaultImmunizationShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllImmunizationsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList where date is less than DEFAULT_DATE
        defaultImmunizationShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the immunizationList where date is less than UPDATED_DATE
        defaultImmunizationShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllImmunizationsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList where date is greater than DEFAULT_DATE
        defaultImmunizationShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the immunizationList where date is greater than SMALLER_DATE
        defaultImmunizationShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllImmunizationsByPatientUIDIsEqualToSomething() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList where patientUID equals to DEFAULT_PATIENT_UID
        defaultImmunizationShouldBeFound("patientUID.equals=" + DEFAULT_PATIENT_UID);

        // Get all the immunizationList where patientUID equals to UPDATED_PATIENT_UID
        defaultImmunizationShouldNotBeFound("patientUID.equals=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllImmunizationsByPatientUIDIsInShouldWork() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList where patientUID in DEFAULT_PATIENT_UID or UPDATED_PATIENT_UID
        defaultImmunizationShouldBeFound("patientUID.in=" + DEFAULT_PATIENT_UID + "," + UPDATED_PATIENT_UID);

        // Get all the immunizationList where patientUID equals to UPDATED_PATIENT_UID
        defaultImmunizationShouldNotBeFound("patientUID.in=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllImmunizationsByPatientUIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList where patientUID is not null
        defaultImmunizationShouldBeFound("patientUID.specified=true");

        // Get all the immunizationList where patientUID is null
        defaultImmunizationShouldNotBeFound("patientUID.specified=false");
    }

    @Test
    @Transactional
    void getAllImmunizationsByPatientUIDContainsSomething() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList where patientUID contains DEFAULT_PATIENT_UID
        defaultImmunizationShouldBeFound("patientUID.contains=" + DEFAULT_PATIENT_UID);

        // Get all the immunizationList where patientUID contains UPDATED_PATIENT_UID
        defaultImmunizationShouldNotBeFound("patientUID.contains=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllImmunizationsByPatientUIDNotContainsSomething() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        // Get all the immunizationList where patientUID does not contain DEFAULT_PATIENT_UID
        defaultImmunizationShouldNotBeFound("patientUID.doesNotContain=" + DEFAULT_PATIENT_UID);

        // Get all the immunizationList where patientUID does not contain UPDATED_PATIENT_UID
        defaultImmunizationShouldBeFound("patientUID.doesNotContain=" + UPDATED_PATIENT_UID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultImmunizationShouldBeFound(String filter) throws Exception {
        restImmunizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(immunization.getId().intValue())))
            .andExpect(jsonPath("$.[*].immunization").value(hasItem(DEFAULT_IMMUNIZATION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].patientUID").value(hasItem(DEFAULT_PATIENT_UID)));

        // Check, that the count call also returns 1
        restImmunizationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultImmunizationShouldNotBeFound(String filter) throws Exception {
        restImmunizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restImmunizationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingImmunization() throws Exception {
        // Get the immunization
        restImmunizationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingImmunization() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        int databaseSizeBeforeUpdate = immunizationRepository.findAll().size();

        // Update the immunization
        Immunization updatedImmunization = immunizationRepository.findById(immunization.getId()).get();
        // Disconnect from session so that the updates on updatedImmunization are not directly saved in db
        em.detach(updatedImmunization);
        updatedImmunization.immunization(UPDATED_IMMUNIZATION).date(UPDATED_DATE).patientUID(UPDATED_PATIENT_UID);
        ImmunizationDTO immunizationDTO = immunizationMapper.toDto(updatedImmunization);

        restImmunizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, immunizationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(immunizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Immunization in the database
        List<Immunization> immunizationList = immunizationRepository.findAll();
        assertThat(immunizationList).hasSize(databaseSizeBeforeUpdate);
        Immunization testImmunization = immunizationList.get(immunizationList.size() - 1);
        assertThat(testImmunization.getImmunization()).isEqualTo(UPDATED_IMMUNIZATION);
        assertThat(testImmunization.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testImmunization.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void putNonExistingImmunization() throws Exception {
        int databaseSizeBeforeUpdate = immunizationRepository.findAll().size();
        immunization.setId(count.incrementAndGet());

        // Create the Immunization
        ImmunizationDTO immunizationDTO = immunizationMapper.toDto(immunization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImmunizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, immunizationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(immunizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Immunization in the database
        List<Immunization> immunizationList = immunizationRepository.findAll();
        assertThat(immunizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchImmunization() throws Exception {
        int databaseSizeBeforeUpdate = immunizationRepository.findAll().size();
        immunization.setId(count.incrementAndGet());

        // Create the Immunization
        ImmunizationDTO immunizationDTO = immunizationMapper.toDto(immunization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImmunizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(immunizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Immunization in the database
        List<Immunization> immunizationList = immunizationRepository.findAll();
        assertThat(immunizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamImmunization() throws Exception {
        int databaseSizeBeforeUpdate = immunizationRepository.findAll().size();
        immunization.setId(count.incrementAndGet());

        // Create the Immunization
        ImmunizationDTO immunizationDTO = immunizationMapper.toDto(immunization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImmunizationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(immunizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Immunization in the database
        List<Immunization> immunizationList = immunizationRepository.findAll();
        assertThat(immunizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateImmunizationWithPatch() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        int databaseSizeBeforeUpdate = immunizationRepository.findAll().size();

        // Update the immunization using partial update
        Immunization partialUpdatedImmunization = new Immunization();
        partialUpdatedImmunization.setId(immunization.getId());

        partialUpdatedImmunization.date(UPDATED_DATE).patientUID(UPDATED_PATIENT_UID);

        restImmunizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImmunization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImmunization))
            )
            .andExpect(status().isOk());

        // Validate the Immunization in the database
        List<Immunization> immunizationList = immunizationRepository.findAll();
        assertThat(immunizationList).hasSize(databaseSizeBeforeUpdate);
        Immunization testImmunization = immunizationList.get(immunizationList.size() - 1);
        assertThat(testImmunization.getImmunization()).isEqualTo(DEFAULT_IMMUNIZATION);
        assertThat(testImmunization.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testImmunization.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void fullUpdateImmunizationWithPatch() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        int databaseSizeBeforeUpdate = immunizationRepository.findAll().size();

        // Update the immunization using partial update
        Immunization partialUpdatedImmunization = new Immunization();
        partialUpdatedImmunization.setId(immunization.getId());

        partialUpdatedImmunization.immunization(UPDATED_IMMUNIZATION).date(UPDATED_DATE).patientUID(UPDATED_PATIENT_UID);

        restImmunizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImmunization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedImmunization))
            )
            .andExpect(status().isOk());

        // Validate the Immunization in the database
        List<Immunization> immunizationList = immunizationRepository.findAll();
        assertThat(immunizationList).hasSize(databaseSizeBeforeUpdate);
        Immunization testImmunization = immunizationList.get(immunizationList.size() - 1);
        assertThat(testImmunization.getImmunization()).isEqualTo(UPDATED_IMMUNIZATION);
        assertThat(testImmunization.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testImmunization.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void patchNonExistingImmunization() throws Exception {
        int databaseSizeBeforeUpdate = immunizationRepository.findAll().size();
        immunization.setId(count.incrementAndGet());

        // Create the Immunization
        ImmunizationDTO immunizationDTO = immunizationMapper.toDto(immunization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImmunizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, immunizationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(immunizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Immunization in the database
        List<Immunization> immunizationList = immunizationRepository.findAll();
        assertThat(immunizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchImmunization() throws Exception {
        int databaseSizeBeforeUpdate = immunizationRepository.findAll().size();
        immunization.setId(count.incrementAndGet());

        // Create the Immunization
        ImmunizationDTO immunizationDTO = immunizationMapper.toDto(immunization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImmunizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(immunizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Immunization in the database
        List<Immunization> immunizationList = immunizationRepository.findAll();
        assertThat(immunizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamImmunization() throws Exception {
        int databaseSizeBeforeUpdate = immunizationRepository.findAll().size();
        immunization.setId(count.incrementAndGet());

        // Create the Immunization
        ImmunizationDTO immunizationDTO = immunizationMapper.toDto(immunization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImmunizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(immunizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Immunization in the database
        List<Immunization> immunizationList = immunizationRepository.findAll();
        assertThat(immunizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteImmunization() throws Exception {
        // Initialize the database
        immunizationRepository.saveAndFlush(immunization);

        int databaseSizeBeforeDelete = immunizationRepository.findAll().size();

        // Delete the immunization
        restImmunizationMockMvc
            .perform(delete(ENTITY_API_URL_ID, immunization.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Immunization> immunizationList = immunizationRepository.findAll();
        assertThat(immunizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
