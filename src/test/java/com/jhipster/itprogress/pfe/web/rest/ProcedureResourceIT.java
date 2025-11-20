package com.jhipster.itprogress.pfe.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.itprogress.pfe.IntegrationTest;
import com.jhipster.itprogress.pfe.domain.Procedure;
import com.jhipster.itprogress.pfe.repository.ProcedureRepository;
import com.jhipster.itprogress.pfe.service.criteria.ProcedureCriteria;
import com.jhipster.itprogress.pfe.service.dto.ProcedureDTO;
import com.jhipster.itprogress.pfe.service.mapper.ProcedureMapper;
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
 * Integration tests for the {@link ProcedureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProcedureResourceIT {

    private static final String DEFAULT_PROCEDURE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDURE_TEXT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PATIENT_UID = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_UID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/procedures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private ProcedureMapper procedureMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcedureMockMvc;

    private Procedure procedure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Procedure createEntity(EntityManager em) {
        Procedure procedure = new Procedure().procedureText(DEFAULT_PROCEDURE_TEXT).date(DEFAULT_DATE).patientUID(DEFAULT_PATIENT_UID);
        return procedure;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Procedure createUpdatedEntity(EntityManager em) {
        Procedure procedure = new Procedure().procedureText(UPDATED_PROCEDURE_TEXT).date(UPDATED_DATE).patientUID(UPDATED_PATIENT_UID);
        return procedure;
    }

    @BeforeEach
    public void initTest() {
        procedure = createEntity(em);
    }

    @Test
    @Transactional
    void createProcedure() throws Exception {
        int databaseSizeBeforeCreate = procedureRepository.findAll().size();
        // Create the Procedure
        ProcedureDTO procedureDTO = procedureMapper.toDto(procedure);
        restProcedureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procedureDTO)))
            .andExpect(status().isCreated());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeCreate + 1);
        Procedure testProcedure = procedureList.get(procedureList.size() - 1);
        assertThat(testProcedure.getProcedureText()).isEqualTo(DEFAULT_PROCEDURE_TEXT);
        assertThat(testProcedure.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testProcedure.getPatientUID()).isEqualTo(DEFAULT_PATIENT_UID);
    }

    @Test
    @Transactional
    void createProcedureWithExistingId() throws Exception {
        // Create the Procedure with an existing ID
        procedure.setId(1L);
        ProcedureDTO procedureDTO = procedureMapper.toDto(procedure);

        int databaseSizeBeforeCreate = procedureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcedureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procedureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProcedures() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList
        restProcedureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedure.getId().intValue())))
            .andExpect(jsonPath("$.[*].procedureText").value(hasItem(DEFAULT_PROCEDURE_TEXT)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].patientUID").value(hasItem(DEFAULT_PATIENT_UID)));
    }

    @Test
    @Transactional
    void getProcedure() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get the procedure
        restProcedureMockMvc
            .perform(get(ENTITY_API_URL_ID, procedure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(procedure.getId().intValue()))
            .andExpect(jsonPath("$.procedureText").value(DEFAULT_PROCEDURE_TEXT))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.patientUID").value(DEFAULT_PATIENT_UID));
    }

    @Test
    @Transactional
    void getProceduresByIdFiltering() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        Long id = procedure.getId();

        defaultProcedureShouldBeFound("id.equals=" + id);
        defaultProcedureShouldNotBeFound("id.notEquals=" + id);

        defaultProcedureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProcedureShouldNotBeFound("id.greaterThan=" + id);

        defaultProcedureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProcedureShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProceduresByProcedureTextIsEqualToSomething() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where procedureText equals to DEFAULT_PROCEDURE_TEXT
        defaultProcedureShouldBeFound("procedureText.equals=" + DEFAULT_PROCEDURE_TEXT);

        // Get all the procedureList where procedureText equals to UPDATED_PROCEDURE_TEXT
        defaultProcedureShouldNotBeFound("procedureText.equals=" + UPDATED_PROCEDURE_TEXT);
    }

    @Test
    @Transactional
    void getAllProceduresByProcedureTextIsInShouldWork() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where procedureText in DEFAULT_PROCEDURE_TEXT or UPDATED_PROCEDURE_TEXT
        defaultProcedureShouldBeFound("procedureText.in=" + DEFAULT_PROCEDURE_TEXT + "," + UPDATED_PROCEDURE_TEXT);

        // Get all the procedureList where procedureText equals to UPDATED_PROCEDURE_TEXT
        defaultProcedureShouldNotBeFound("procedureText.in=" + UPDATED_PROCEDURE_TEXT);
    }

    @Test
    @Transactional
    void getAllProceduresByProcedureTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where procedureText is not null
        defaultProcedureShouldBeFound("procedureText.specified=true");

        // Get all the procedureList where procedureText is null
        defaultProcedureShouldNotBeFound("procedureText.specified=false");
    }

    @Test
    @Transactional
    void getAllProceduresByProcedureTextContainsSomething() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where procedureText contains DEFAULT_PROCEDURE_TEXT
        defaultProcedureShouldBeFound("procedureText.contains=" + DEFAULT_PROCEDURE_TEXT);

        // Get all the procedureList where procedureText contains UPDATED_PROCEDURE_TEXT
        defaultProcedureShouldNotBeFound("procedureText.contains=" + UPDATED_PROCEDURE_TEXT);
    }

    @Test
    @Transactional
    void getAllProceduresByProcedureTextNotContainsSomething() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where procedureText does not contain DEFAULT_PROCEDURE_TEXT
        defaultProcedureShouldNotBeFound("procedureText.doesNotContain=" + DEFAULT_PROCEDURE_TEXT);

        // Get all the procedureList where procedureText does not contain UPDATED_PROCEDURE_TEXT
        defaultProcedureShouldBeFound("procedureText.doesNotContain=" + UPDATED_PROCEDURE_TEXT);
    }

    @Test
    @Transactional
    void getAllProceduresByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where date equals to DEFAULT_DATE
        defaultProcedureShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the procedureList where date equals to UPDATED_DATE
        defaultProcedureShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllProceduresByDateIsInShouldWork() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where date in DEFAULT_DATE or UPDATED_DATE
        defaultProcedureShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the procedureList where date equals to UPDATED_DATE
        defaultProcedureShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllProceduresByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where date is not null
        defaultProcedureShouldBeFound("date.specified=true");

        // Get all the procedureList where date is null
        defaultProcedureShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllProceduresByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where date is greater than or equal to DEFAULT_DATE
        defaultProcedureShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the procedureList where date is greater than or equal to UPDATED_DATE
        defaultProcedureShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllProceduresByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where date is less than or equal to DEFAULT_DATE
        defaultProcedureShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the procedureList where date is less than or equal to SMALLER_DATE
        defaultProcedureShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllProceduresByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where date is less than DEFAULT_DATE
        defaultProcedureShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the procedureList where date is less than UPDATED_DATE
        defaultProcedureShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllProceduresByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where date is greater than DEFAULT_DATE
        defaultProcedureShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the procedureList where date is greater than SMALLER_DATE
        defaultProcedureShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllProceduresByPatientUIDIsEqualToSomething() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where patientUID equals to DEFAULT_PATIENT_UID
        defaultProcedureShouldBeFound("patientUID.equals=" + DEFAULT_PATIENT_UID);

        // Get all the procedureList where patientUID equals to UPDATED_PATIENT_UID
        defaultProcedureShouldNotBeFound("patientUID.equals=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllProceduresByPatientUIDIsInShouldWork() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where patientUID in DEFAULT_PATIENT_UID or UPDATED_PATIENT_UID
        defaultProcedureShouldBeFound("patientUID.in=" + DEFAULT_PATIENT_UID + "," + UPDATED_PATIENT_UID);

        // Get all the procedureList where patientUID equals to UPDATED_PATIENT_UID
        defaultProcedureShouldNotBeFound("patientUID.in=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllProceduresByPatientUIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where patientUID is not null
        defaultProcedureShouldBeFound("patientUID.specified=true");

        // Get all the procedureList where patientUID is null
        defaultProcedureShouldNotBeFound("patientUID.specified=false");
    }

    @Test
    @Transactional
    void getAllProceduresByPatientUIDContainsSomething() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where patientUID contains DEFAULT_PATIENT_UID
        defaultProcedureShouldBeFound("patientUID.contains=" + DEFAULT_PATIENT_UID);

        // Get all the procedureList where patientUID contains UPDATED_PATIENT_UID
        defaultProcedureShouldNotBeFound("patientUID.contains=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllProceduresByPatientUIDNotContainsSomething() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where patientUID does not contain DEFAULT_PATIENT_UID
        defaultProcedureShouldNotBeFound("patientUID.doesNotContain=" + DEFAULT_PATIENT_UID);

        // Get all the procedureList where patientUID does not contain UPDATED_PATIENT_UID
        defaultProcedureShouldBeFound("patientUID.doesNotContain=" + UPDATED_PATIENT_UID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProcedureShouldBeFound(String filter) throws Exception {
        restProcedureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedure.getId().intValue())))
            .andExpect(jsonPath("$.[*].procedureText").value(hasItem(DEFAULT_PROCEDURE_TEXT)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].patientUID").value(hasItem(DEFAULT_PATIENT_UID)));

        // Check, that the count call also returns 1
        restProcedureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProcedureShouldNotBeFound(String filter) throws Exception {
        restProcedureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProcedureMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProcedure() throws Exception {
        // Get the procedure
        restProcedureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProcedure() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();

        // Update the procedure
        Procedure updatedProcedure = procedureRepository.findById(procedure.getId()).get();
        // Disconnect from session so that the updates on updatedProcedure are not directly saved in db
        em.detach(updatedProcedure);
        updatedProcedure.procedureText(UPDATED_PROCEDURE_TEXT).date(UPDATED_DATE).patientUID(UPDATED_PATIENT_UID);
        ProcedureDTO procedureDTO = procedureMapper.toDto(updatedProcedure);

        restProcedureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, procedureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(procedureDTO))
            )
            .andExpect(status().isOk());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
        Procedure testProcedure = procedureList.get(procedureList.size() - 1);
        assertThat(testProcedure.getProcedureText()).isEqualTo(UPDATED_PROCEDURE_TEXT);
        assertThat(testProcedure.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testProcedure.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void putNonExistingProcedure() throws Exception {
        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();
        procedure.setId(count.incrementAndGet());

        // Create the Procedure
        ProcedureDTO procedureDTO = procedureMapper.toDto(procedure);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcedureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, procedureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(procedureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProcedure() throws Exception {
        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();
        procedure.setId(count.incrementAndGet());

        // Create the Procedure
        ProcedureDTO procedureDTO = procedureMapper.toDto(procedure);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcedureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(procedureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProcedure() throws Exception {
        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();
        procedure.setId(count.incrementAndGet());

        // Create the Procedure
        ProcedureDTO procedureDTO = procedureMapper.toDto(procedure);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcedureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procedureDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProcedureWithPatch() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();

        // Update the procedure using partial update
        Procedure partialUpdatedProcedure = new Procedure();
        partialUpdatedProcedure.setId(procedure.getId());

        partialUpdatedProcedure.procedureText(UPDATED_PROCEDURE_TEXT).date(UPDATED_DATE);

        restProcedureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcedure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcedure))
            )
            .andExpect(status().isOk());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
        Procedure testProcedure = procedureList.get(procedureList.size() - 1);
        assertThat(testProcedure.getProcedureText()).isEqualTo(UPDATED_PROCEDURE_TEXT);
        assertThat(testProcedure.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testProcedure.getPatientUID()).isEqualTo(DEFAULT_PATIENT_UID);
    }

    @Test
    @Transactional
    void fullUpdateProcedureWithPatch() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();

        // Update the procedure using partial update
        Procedure partialUpdatedProcedure = new Procedure();
        partialUpdatedProcedure.setId(procedure.getId());

        partialUpdatedProcedure.procedureText(UPDATED_PROCEDURE_TEXT).date(UPDATED_DATE).patientUID(UPDATED_PATIENT_UID);

        restProcedureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcedure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcedure))
            )
            .andExpect(status().isOk());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
        Procedure testProcedure = procedureList.get(procedureList.size() - 1);
        assertThat(testProcedure.getProcedureText()).isEqualTo(UPDATED_PROCEDURE_TEXT);
        assertThat(testProcedure.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testProcedure.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void patchNonExistingProcedure() throws Exception {
        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();
        procedure.setId(count.incrementAndGet());

        // Create the Procedure
        ProcedureDTO procedureDTO = procedureMapper.toDto(procedure);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcedureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, procedureDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(procedureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProcedure() throws Exception {
        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();
        procedure.setId(count.incrementAndGet());

        // Create the Procedure
        ProcedureDTO procedureDTO = procedureMapper.toDto(procedure);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcedureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(procedureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProcedure() throws Exception {
        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();
        procedure.setId(count.incrementAndGet());

        // Create the Procedure
        ProcedureDTO procedureDTO = procedureMapper.toDto(procedure);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcedureMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(procedureDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProcedure() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        int databaseSizeBeforeDelete = procedureRepository.findAll().size();

        // Delete the procedure
        restProcedureMockMvc
            .perform(delete(ENTITY_API_URL_ID, procedure.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
