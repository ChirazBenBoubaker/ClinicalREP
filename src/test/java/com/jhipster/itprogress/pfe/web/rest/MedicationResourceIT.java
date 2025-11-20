package com.jhipster.itprogress.pfe.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.itprogress.pfe.IntegrationTest;
import com.jhipster.itprogress.pfe.domain.Medication;
import com.jhipster.itprogress.pfe.repository.MedicationRepository;
import com.jhipster.itprogress.pfe.service.criteria.MedicationCriteria;
import com.jhipster.itprogress.pfe.service.dto.MedicationDTO;
import com.jhipster.itprogress.pfe.service.mapper.MedicationMapper;
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
 * Integration tests for the {@link MedicationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MedicationResourceIT {

    private static final String DEFAULT_MEDICATION_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_MEDICATION_TEXT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PATIENT_UID = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_UID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/medications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private MedicationMapper medicationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicationMockMvc;

    private Medication medication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medication createEntity(EntityManager em) {
        Medication medication = new Medication().medicationText(DEFAULT_MEDICATION_TEXT).date(DEFAULT_DATE).patientUID(DEFAULT_PATIENT_UID);
        return medication;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medication createUpdatedEntity(EntityManager em) {
        Medication medication = new Medication().medicationText(UPDATED_MEDICATION_TEXT).date(UPDATED_DATE).patientUID(UPDATED_PATIENT_UID);
        return medication;
    }

    @BeforeEach
    public void initTest() {
        medication = createEntity(em);
    }

    @Test
    @Transactional
    void createMedication() throws Exception {
        int databaseSizeBeforeCreate = medicationRepository.findAll().size();
        // Create the Medication
        MedicationDTO medicationDTO = medicationMapper.toDto(medication);
        restMedicationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medicationDTO)))
            .andExpect(status().isCreated());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeCreate + 1);
        Medication testMedication = medicationList.get(medicationList.size() - 1);
        assertThat(testMedication.getMedicationText()).isEqualTo(DEFAULT_MEDICATION_TEXT);
        assertThat(testMedication.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMedication.getPatientUID()).isEqualTo(DEFAULT_PATIENT_UID);
    }

    @Test
    @Transactional
    void createMedicationWithExistingId() throws Exception {
        // Create the Medication with an existing ID
        medication.setId(1L);
        MedicationDTO medicationDTO = medicationMapper.toDto(medication);

        int databaseSizeBeforeCreate = medicationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMedications() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList
        restMedicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medication.getId().intValue())))
            .andExpect(jsonPath("$.[*].medicationText").value(hasItem(DEFAULT_MEDICATION_TEXT)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].patientUID").value(hasItem(DEFAULT_PATIENT_UID)));
    }

    @Test
    @Transactional
    void getMedication() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get the medication
        restMedicationMockMvc
            .perform(get(ENTITY_API_URL_ID, medication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medication.getId().intValue()))
            .andExpect(jsonPath("$.medicationText").value(DEFAULT_MEDICATION_TEXT))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.patientUID").value(DEFAULT_PATIENT_UID));
    }

    @Test
    @Transactional
    void getMedicationsByIdFiltering() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        Long id = medication.getId();

        defaultMedicationShouldBeFound("id.equals=" + id);
        defaultMedicationShouldNotBeFound("id.notEquals=" + id);

        defaultMedicationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMedicationShouldNotBeFound("id.greaterThan=" + id);

        defaultMedicationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMedicationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMedicationsByMedicationTextIsEqualToSomething() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList where medicationText equals to DEFAULT_MEDICATION_TEXT
        defaultMedicationShouldBeFound("medicationText.equals=" + DEFAULT_MEDICATION_TEXT);

        // Get all the medicationList where medicationText equals to UPDATED_MEDICATION_TEXT
        defaultMedicationShouldNotBeFound("medicationText.equals=" + UPDATED_MEDICATION_TEXT);
    }

    @Test
    @Transactional
    void getAllMedicationsByMedicationTextIsInShouldWork() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList where medicationText in DEFAULT_MEDICATION_TEXT or UPDATED_MEDICATION_TEXT
        defaultMedicationShouldBeFound("medicationText.in=" + DEFAULT_MEDICATION_TEXT + "," + UPDATED_MEDICATION_TEXT);

        // Get all the medicationList where medicationText equals to UPDATED_MEDICATION_TEXT
        defaultMedicationShouldNotBeFound("medicationText.in=" + UPDATED_MEDICATION_TEXT);
    }

    @Test
    @Transactional
    void getAllMedicationsByMedicationTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList where medicationText is not null
        defaultMedicationShouldBeFound("medicationText.specified=true");

        // Get all the medicationList where medicationText is null
        defaultMedicationShouldNotBeFound("medicationText.specified=false");
    }

    @Test
    @Transactional
    void getAllMedicationsByMedicationTextContainsSomething() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList where medicationText contains DEFAULT_MEDICATION_TEXT
        defaultMedicationShouldBeFound("medicationText.contains=" + DEFAULT_MEDICATION_TEXT);

        // Get all the medicationList where medicationText contains UPDATED_MEDICATION_TEXT
        defaultMedicationShouldNotBeFound("medicationText.contains=" + UPDATED_MEDICATION_TEXT);
    }

    @Test
    @Transactional
    void getAllMedicationsByMedicationTextNotContainsSomething() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList where medicationText does not contain DEFAULT_MEDICATION_TEXT
        defaultMedicationShouldNotBeFound("medicationText.doesNotContain=" + DEFAULT_MEDICATION_TEXT);

        // Get all the medicationList where medicationText does not contain UPDATED_MEDICATION_TEXT
        defaultMedicationShouldBeFound("medicationText.doesNotContain=" + UPDATED_MEDICATION_TEXT);
    }

    @Test
    @Transactional
    void getAllMedicationsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList where date equals to DEFAULT_DATE
        defaultMedicationShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the medicationList where date equals to UPDATED_DATE
        defaultMedicationShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllMedicationsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList where date in DEFAULT_DATE or UPDATED_DATE
        defaultMedicationShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the medicationList where date equals to UPDATED_DATE
        defaultMedicationShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllMedicationsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList where date is not null
        defaultMedicationShouldBeFound("date.specified=true");

        // Get all the medicationList where date is null
        defaultMedicationShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllMedicationsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList where date is greater than or equal to DEFAULT_DATE
        defaultMedicationShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the medicationList where date is greater than or equal to UPDATED_DATE
        defaultMedicationShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllMedicationsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList where date is less than or equal to DEFAULT_DATE
        defaultMedicationShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the medicationList where date is less than or equal to SMALLER_DATE
        defaultMedicationShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllMedicationsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList where date is less than DEFAULT_DATE
        defaultMedicationShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the medicationList where date is less than UPDATED_DATE
        defaultMedicationShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllMedicationsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList where date is greater than DEFAULT_DATE
        defaultMedicationShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the medicationList where date is greater than SMALLER_DATE
        defaultMedicationShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllMedicationsByPatientUIDIsEqualToSomething() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList where patientUID equals to DEFAULT_PATIENT_UID
        defaultMedicationShouldBeFound("patientUID.equals=" + DEFAULT_PATIENT_UID);

        // Get all the medicationList where patientUID equals to UPDATED_PATIENT_UID
        defaultMedicationShouldNotBeFound("patientUID.equals=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllMedicationsByPatientUIDIsInShouldWork() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList where patientUID in DEFAULT_PATIENT_UID or UPDATED_PATIENT_UID
        defaultMedicationShouldBeFound("patientUID.in=" + DEFAULT_PATIENT_UID + "," + UPDATED_PATIENT_UID);

        // Get all the medicationList where patientUID equals to UPDATED_PATIENT_UID
        defaultMedicationShouldNotBeFound("patientUID.in=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllMedicationsByPatientUIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList where patientUID is not null
        defaultMedicationShouldBeFound("patientUID.specified=true");

        // Get all the medicationList where patientUID is null
        defaultMedicationShouldNotBeFound("patientUID.specified=false");
    }

    @Test
    @Transactional
    void getAllMedicationsByPatientUIDContainsSomething() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList where patientUID contains DEFAULT_PATIENT_UID
        defaultMedicationShouldBeFound("patientUID.contains=" + DEFAULT_PATIENT_UID);

        // Get all the medicationList where patientUID contains UPDATED_PATIENT_UID
        defaultMedicationShouldNotBeFound("patientUID.contains=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllMedicationsByPatientUIDNotContainsSomething() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList where patientUID does not contain DEFAULT_PATIENT_UID
        defaultMedicationShouldNotBeFound("patientUID.doesNotContain=" + DEFAULT_PATIENT_UID);

        // Get all the medicationList where patientUID does not contain UPDATED_PATIENT_UID
        defaultMedicationShouldBeFound("patientUID.doesNotContain=" + UPDATED_PATIENT_UID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMedicationShouldBeFound(String filter) throws Exception {
        restMedicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medication.getId().intValue())))
            .andExpect(jsonPath("$.[*].medicationText").value(hasItem(DEFAULT_MEDICATION_TEXT)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].patientUID").value(hasItem(DEFAULT_PATIENT_UID)));

        // Check, that the count call also returns 1
        restMedicationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMedicationShouldNotBeFound(String filter) throws Exception {
        restMedicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMedicationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMedication() throws Exception {
        // Get the medication
        restMedicationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMedication() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        int databaseSizeBeforeUpdate = medicationRepository.findAll().size();

        // Update the medication
        Medication updatedMedication = medicationRepository.findById(medication.getId()).get();
        // Disconnect from session so that the updates on updatedMedication are not directly saved in db
        em.detach(updatedMedication);
        updatedMedication.medicationText(UPDATED_MEDICATION_TEXT).date(UPDATED_DATE).patientUID(UPDATED_PATIENT_UID);
        MedicationDTO medicationDTO = medicationMapper.toDto(updatedMedication);

        restMedicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medicationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeUpdate);
        Medication testMedication = medicationList.get(medicationList.size() - 1);
        assertThat(testMedication.getMedicationText()).isEqualTo(UPDATED_MEDICATION_TEXT);
        assertThat(testMedication.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMedication.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void putNonExistingMedication() throws Exception {
        int databaseSizeBeforeUpdate = medicationRepository.findAll().size();
        medication.setId(count.incrementAndGet());

        // Create the Medication
        MedicationDTO medicationDTO = medicationMapper.toDto(medication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medicationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMedication() throws Exception {
        int databaseSizeBeforeUpdate = medicationRepository.findAll().size();
        medication.setId(count.incrementAndGet());

        // Create the Medication
        MedicationDTO medicationDTO = medicationMapper.toDto(medication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMedication() throws Exception {
        int databaseSizeBeforeUpdate = medicationRepository.findAll().size();
        medication.setId(count.incrementAndGet());

        // Create the Medication
        MedicationDTO medicationDTO = medicationMapper.toDto(medication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medicationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMedicationWithPatch() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        int databaseSizeBeforeUpdate = medicationRepository.findAll().size();

        // Update the medication using partial update
        Medication partialUpdatedMedication = new Medication();
        partialUpdatedMedication.setId(medication.getId());

        partialUpdatedMedication.medicationText(UPDATED_MEDICATION_TEXT).patientUID(UPDATED_PATIENT_UID);

        restMedicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedication))
            )
            .andExpect(status().isOk());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeUpdate);
        Medication testMedication = medicationList.get(medicationList.size() - 1);
        assertThat(testMedication.getMedicationText()).isEqualTo(UPDATED_MEDICATION_TEXT);
        assertThat(testMedication.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMedication.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void fullUpdateMedicationWithPatch() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        int databaseSizeBeforeUpdate = medicationRepository.findAll().size();

        // Update the medication using partial update
        Medication partialUpdatedMedication = new Medication();
        partialUpdatedMedication.setId(medication.getId());

        partialUpdatedMedication.medicationText(UPDATED_MEDICATION_TEXT).date(UPDATED_DATE).patientUID(UPDATED_PATIENT_UID);

        restMedicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedication))
            )
            .andExpect(status().isOk());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeUpdate);
        Medication testMedication = medicationList.get(medicationList.size() - 1);
        assertThat(testMedication.getMedicationText()).isEqualTo(UPDATED_MEDICATION_TEXT);
        assertThat(testMedication.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMedication.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void patchNonExistingMedication() throws Exception {
        int databaseSizeBeforeUpdate = medicationRepository.findAll().size();
        medication.setId(count.incrementAndGet());

        // Create the Medication
        MedicationDTO medicationDTO = medicationMapper.toDto(medication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, medicationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMedication() throws Exception {
        int databaseSizeBeforeUpdate = medicationRepository.findAll().size();
        medication.setId(count.incrementAndGet());

        // Create the Medication
        MedicationDTO medicationDTO = medicationMapper.toDto(medication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMedication() throws Exception {
        int databaseSizeBeforeUpdate = medicationRepository.findAll().size();
        medication.setId(count.incrementAndGet());

        // Create the Medication
        MedicationDTO medicationDTO = medicationMapper.toDto(medication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(medicationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMedication() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        int databaseSizeBeforeDelete = medicationRepository.findAll().size();

        // Delete the medication
        restMedicationMockMvc
            .perform(delete(ENTITY_API_URL_ID, medication.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
