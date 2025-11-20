package com.jhipster.itprogress.pfe.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.itprogress.pfe.IntegrationTest;
import com.jhipster.itprogress.pfe.domain.Condition;
import com.jhipster.itprogress.pfe.repository.ConditionRepository;
import com.jhipster.itprogress.pfe.service.criteria.ConditionCriteria;
import com.jhipster.itprogress.pfe.service.dto.ConditionDTO;
import com.jhipster.itprogress.pfe.service.mapper.ConditionMapper;
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
 * Integration tests for the {@link ConditionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConditionResourceIT {

    private static final String DEFAULT_CONDITION_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_CONDITION_TEXT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CONDITION_ONSET_DATES = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONDITION_ONSET_DATES = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CONDITION_ONSET_DATES = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PATIENT_UID = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_UID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/conditions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConditionRepository conditionRepository;

    @Autowired
    private ConditionMapper conditionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConditionMockMvc;

    private Condition condition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Condition createEntity(EntityManager em) {
        Condition condition = new Condition()
            .conditionText(DEFAULT_CONDITION_TEXT)
            .conditionOnsetDates(DEFAULT_CONDITION_ONSET_DATES)
            .patientUID(DEFAULT_PATIENT_UID);
        return condition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Condition createUpdatedEntity(EntityManager em) {
        Condition condition = new Condition()
            .conditionText(UPDATED_CONDITION_TEXT)
            .conditionOnsetDates(UPDATED_CONDITION_ONSET_DATES)
            .patientUID(UPDATED_PATIENT_UID);
        return condition;
    }

    @BeforeEach
    public void initTest() {
        condition = createEntity(em);
    }

    @Test
    @Transactional
    void createCondition() throws Exception {
        int databaseSizeBeforeCreate = conditionRepository.findAll().size();
        // Create the Condition
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);
        restConditionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conditionDTO)))
            .andExpect(status().isCreated());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeCreate + 1);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getConditionText()).isEqualTo(DEFAULT_CONDITION_TEXT);
        assertThat(testCondition.getConditionOnsetDates()).isEqualTo(DEFAULT_CONDITION_ONSET_DATES);
        assertThat(testCondition.getPatientUID()).isEqualTo(DEFAULT_PATIENT_UID);
    }

    @Test
    @Transactional
    void createConditionWithExistingId() throws Exception {
        // Create the Condition with an existing ID
        condition.setId(1L);
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);

        int databaseSizeBeforeCreate = conditionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConditionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conditionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConditions() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList
        restConditionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(condition.getId().intValue())))
            .andExpect(jsonPath("$.[*].conditionText").value(hasItem(DEFAULT_CONDITION_TEXT)))
            .andExpect(jsonPath("$.[*].conditionOnsetDates").value(hasItem(DEFAULT_CONDITION_ONSET_DATES.toString())))
            .andExpect(jsonPath("$.[*].patientUID").value(hasItem(DEFAULT_PATIENT_UID)));
    }

    @Test
    @Transactional
    void getCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get the condition
        restConditionMockMvc
            .perform(get(ENTITY_API_URL_ID, condition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(condition.getId().intValue()))
            .andExpect(jsonPath("$.conditionText").value(DEFAULT_CONDITION_TEXT))
            .andExpect(jsonPath("$.conditionOnsetDates").value(DEFAULT_CONDITION_ONSET_DATES.toString()))
            .andExpect(jsonPath("$.patientUID").value(DEFAULT_PATIENT_UID));
    }

    @Test
    @Transactional
    void getConditionsByIdFiltering() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        Long id = condition.getId();

        defaultConditionShouldBeFound("id.equals=" + id);
        defaultConditionShouldNotBeFound("id.notEquals=" + id);

        defaultConditionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConditionShouldNotBeFound("id.greaterThan=" + id);

        defaultConditionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConditionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllConditionsByConditionTextIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionText equals to DEFAULT_CONDITION_TEXT
        defaultConditionShouldBeFound("conditionText.equals=" + DEFAULT_CONDITION_TEXT);

        // Get all the conditionList where conditionText equals to UPDATED_CONDITION_TEXT
        defaultConditionShouldNotBeFound("conditionText.equals=" + UPDATED_CONDITION_TEXT);
    }

    @Test
    @Transactional
    void getAllConditionsByConditionTextIsInShouldWork() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionText in DEFAULT_CONDITION_TEXT or UPDATED_CONDITION_TEXT
        defaultConditionShouldBeFound("conditionText.in=" + DEFAULT_CONDITION_TEXT + "," + UPDATED_CONDITION_TEXT);

        // Get all the conditionList where conditionText equals to UPDATED_CONDITION_TEXT
        defaultConditionShouldNotBeFound("conditionText.in=" + UPDATED_CONDITION_TEXT);
    }

    @Test
    @Transactional
    void getAllConditionsByConditionTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionText is not null
        defaultConditionShouldBeFound("conditionText.specified=true");

        // Get all the conditionList where conditionText is null
        defaultConditionShouldNotBeFound("conditionText.specified=false");
    }

    @Test
    @Transactional
    void getAllConditionsByConditionTextContainsSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionText contains DEFAULT_CONDITION_TEXT
        defaultConditionShouldBeFound("conditionText.contains=" + DEFAULT_CONDITION_TEXT);

        // Get all the conditionList where conditionText contains UPDATED_CONDITION_TEXT
        defaultConditionShouldNotBeFound("conditionText.contains=" + UPDATED_CONDITION_TEXT);
    }

    @Test
    @Transactional
    void getAllConditionsByConditionTextNotContainsSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionText does not contain DEFAULT_CONDITION_TEXT
        defaultConditionShouldNotBeFound("conditionText.doesNotContain=" + DEFAULT_CONDITION_TEXT);

        // Get all the conditionList where conditionText does not contain UPDATED_CONDITION_TEXT
        defaultConditionShouldBeFound("conditionText.doesNotContain=" + UPDATED_CONDITION_TEXT);
    }

    @Test
    @Transactional
    void getAllConditionsByConditionOnsetDatesIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionOnsetDates equals to DEFAULT_CONDITION_ONSET_DATES
        defaultConditionShouldBeFound("conditionOnsetDates.equals=" + DEFAULT_CONDITION_ONSET_DATES);

        // Get all the conditionList where conditionOnsetDates equals to UPDATED_CONDITION_ONSET_DATES
        defaultConditionShouldNotBeFound("conditionOnsetDates.equals=" + UPDATED_CONDITION_ONSET_DATES);
    }

    @Test
    @Transactional
    void getAllConditionsByConditionOnsetDatesIsInShouldWork() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionOnsetDates in DEFAULT_CONDITION_ONSET_DATES or UPDATED_CONDITION_ONSET_DATES
        defaultConditionShouldBeFound("conditionOnsetDates.in=" + DEFAULT_CONDITION_ONSET_DATES + "," + UPDATED_CONDITION_ONSET_DATES);

        // Get all the conditionList where conditionOnsetDates equals to UPDATED_CONDITION_ONSET_DATES
        defaultConditionShouldNotBeFound("conditionOnsetDates.in=" + UPDATED_CONDITION_ONSET_DATES);
    }

    @Test
    @Transactional
    void getAllConditionsByConditionOnsetDatesIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionOnsetDates is not null
        defaultConditionShouldBeFound("conditionOnsetDates.specified=true");

        // Get all the conditionList where conditionOnsetDates is null
        defaultConditionShouldNotBeFound("conditionOnsetDates.specified=false");
    }

    @Test
    @Transactional
    void getAllConditionsByConditionOnsetDatesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionOnsetDates is greater than or equal to DEFAULT_CONDITION_ONSET_DATES
        defaultConditionShouldBeFound("conditionOnsetDates.greaterThanOrEqual=" + DEFAULT_CONDITION_ONSET_DATES);

        // Get all the conditionList where conditionOnsetDates is greater than or equal to UPDATED_CONDITION_ONSET_DATES
        defaultConditionShouldNotBeFound("conditionOnsetDates.greaterThanOrEqual=" + UPDATED_CONDITION_ONSET_DATES);
    }

    @Test
    @Transactional
    void getAllConditionsByConditionOnsetDatesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionOnsetDates is less than or equal to DEFAULT_CONDITION_ONSET_DATES
        defaultConditionShouldBeFound("conditionOnsetDates.lessThanOrEqual=" + DEFAULT_CONDITION_ONSET_DATES);

        // Get all the conditionList where conditionOnsetDates is less than or equal to SMALLER_CONDITION_ONSET_DATES
        defaultConditionShouldNotBeFound("conditionOnsetDates.lessThanOrEqual=" + SMALLER_CONDITION_ONSET_DATES);
    }

    @Test
    @Transactional
    void getAllConditionsByConditionOnsetDatesIsLessThanSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionOnsetDates is less than DEFAULT_CONDITION_ONSET_DATES
        defaultConditionShouldNotBeFound("conditionOnsetDates.lessThan=" + DEFAULT_CONDITION_ONSET_DATES);

        // Get all the conditionList where conditionOnsetDates is less than UPDATED_CONDITION_ONSET_DATES
        defaultConditionShouldBeFound("conditionOnsetDates.lessThan=" + UPDATED_CONDITION_ONSET_DATES);
    }

    @Test
    @Transactional
    void getAllConditionsByConditionOnsetDatesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionOnsetDates is greater than DEFAULT_CONDITION_ONSET_DATES
        defaultConditionShouldNotBeFound("conditionOnsetDates.greaterThan=" + DEFAULT_CONDITION_ONSET_DATES);

        // Get all the conditionList where conditionOnsetDates is greater than SMALLER_CONDITION_ONSET_DATES
        defaultConditionShouldBeFound("conditionOnsetDates.greaterThan=" + SMALLER_CONDITION_ONSET_DATES);
    }

    @Test
    @Transactional
    void getAllConditionsByPatientUIDIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where patientUID equals to DEFAULT_PATIENT_UID
        defaultConditionShouldBeFound("patientUID.equals=" + DEFAULT_PATIENT_UID);

        // Get all the conditionList where patientUID equals to UPDATED_PATIENT_UID
        defaultConditionShouldNotBeFound("patientUID.equals=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllConditionsByPatientUIDIsInShouldWork() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where patientUID in DEFAULT_PATIENT_UID or UPDATED_PATIENT_UID
        defaultConditionShouldBeFound("patientUID.in=" + DEFAULT_PATIENT_UID + "," + UPDATED_PATIENT_UID);

        // Get all the conditionList where patientUID equals to UPDATED_PATIENT_UID
        defaultConditionShouldNotBeFound("patientUID.in=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllConditionsByPatientUIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where patientUID is not null
        defaultConditionShouldBeFound("patientUID.specified=true");

        // Get all the conditionList where patientUID is null
        defaultConditionShouldNotBeFound("patientUID.specified=false");
    }

    @Test
    @Transactional
    void getAllConditionsByPatientUIDContainsSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where patientUID contains DEFAULT_PATIENT_UID
        defaultConditionShouldBeFound("patientUID.contains=" + DEFAULT_PATIENT_UID);

        // Get all the conditionList where patientUID contains UPDATED_PATIENT_UID
        defaultConditionShouldNotBeFound("patientUID.contains=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllConditionsByPatientUIDNotContainsSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where patientUID does not contain DEFAULT_PATIENT_UID
        defaultConditionShouldNotBeFound("patientUID.doesNotContain=" + DEFAULT_PATIENT_UID);

        // Get all the conditionList where patientUID does not contain UPDATED_PATIENT_UID
        defaultConditionShouldBeFound("patientUID.doesNotContain=" + UPDATED_PATIENT_UID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConditionShouldBeFound(String filter) throws Exception {
        restConditionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(condition.getId().intValue())))
            .andExpect(jsonPath("$.[*].conditionText").value(hasItem(DEFAULT_CONDITION_TEXT)))
            .andExpect(jsonPath("$.[*].conditionOnsetDates").value(hasItem(DEFAULT_CONDITION_ONSET_DATES.toString())))
            .andExpect(jsonPath("$.[*].patientUID").value(hasItem(DEFAULT_PATIENT_UID)));

        // Check, that the count call also returns 1
        restConditionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConditionShouldNotBeFound(String filter) throws Exception {
        restConditionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConditionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCondition() throws Exception {
        // Get the condition
        restConditionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();

        // Update the condition
        Condition updatedCondition = conditionRepository.findById(condition.getId()).get();
        // Disconnect from session so that the updates on updatedCondition are not directly saved in db
        em.detach(updatedCondition);
        updatedCondition
            .conditionText(UPDATED_CONDITION_TEXT)
            .conditionOnsetDates(UPDATED_CONDITION_ONSET_DATES)
            .patientUID(UPDATED_PATIENT_UID);
        ConditionDTO conditionDTO = conditionMapper.toDto(updatedCondition);

        restConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conditionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getConditionText()).isEqualTo(UPDATED_CONDITION_TEXT);
        assertThat(testCondition.getConditionOnsetDates()).isEqualTo(UPDATED_CONDITION_ONSET_DATES);
        assertThat(testCondition.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void putNonExistingCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // Create the Condition
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conditionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // Create the Condition
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // Create the Condition
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conditionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConditionWithPatch() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();

        // Update the condition using partial update
        Condition partialUpdatedCondition = new Condition();
        partialUpdatedCondition.setId(condition.getId());

        partialUpdatedCondition.conditionText(UPDATED_CONDITION_TEXT);

        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCondition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCondition))
            )
            .andExpect(status().isOk());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getConditionText()).isEqualTo(UPDATED_CONDITION_TEXT);
        assertThat(testCondition.getConditionOnsetDates()).isEqualTo(DEFAULT_CONDITION_ONSET_DATES);
        assertThat(testCondition.getPatientUID()).isEqualTo(DEFAULT_PATIENT_UID);
    }

    @Test
    @Transactional
    void fullUpdateConditionWithPatch() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();

        // Update the condition using partial update
        Condition partialUpdatedCondition = new Condition();
        partialUpdatedCondition.setId(condition.getId());

        partialUpdatedCondition
            .conditionText(UPDATED_CONDITION_TEXT)
            .conditionOnsetDates(UPDATED_CONDITION_ONSET_DATES)
            .patientUID(UPDATED_PATIENT_UID);

        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCondition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCondition))
            )
            .andExpect(status().isOk());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getConditionText()).isEqualTo(UPDATED_CONDITION_TEXT);
        assertThat(testCondition.getConditionOnsetDates()).isEqualTo(UPDATED_CONDITION_ONSET_DATES);
        assertThat(testCondition.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void patchNonExistingCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // Create the Condition
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, conditionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conditionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // Create the Condition
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conditionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // Create the Condition
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(conditionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        int databaseSizeBeforeDelete = conditionRepository.findAll().size();

        // Delete the condition
        restConditionMockMvc
            .perform(delete(ENTITY_API_URL_ID, condition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
