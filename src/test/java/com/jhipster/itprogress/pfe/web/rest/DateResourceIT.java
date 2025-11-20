package com.jhipster.itprogress.pfe.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.itprogress.pfe.IntegrationTest;
import com.jhipster.itprogress.pfe.domain.Date;
import com.jhipster.itprogress.pfe.repository.DateRepository;
import com.jhipster.itprogress.pfe.service.criteria.DateCriteria;
import com.jhipster.itprogress.pfe.service.dto.DateDTO;
import com.jhipster.itprogress.pfe.service.mapper.DateMapper;
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
 * Integration tests for the {@link DateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DateResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;
    private static final Integer SMALLER_MONTH = 1 - 1;

    private static final Integer DEFAULT_DAY = 1;
    private static final Integer UPDATED_DAY = 2;
    private static final Integer SMALLER_DAY = 1 - 1;

    private static final String ENTITY_API_URL = "/api/dates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DateRepository dateRepository;

    @Autowired
    private DateMapper dateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDateMockMvc;

    private Date date;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Date createEntity(EntityManager em) {
        Date date = new Date().date(DEFAULT_DATE).year(DEFAULT_YEAR).month(DEFAULT_MONTH).day(DEFAULT_DAY);
        return date;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Date createUpdatedEntity(EntityManager em) {
        Date date = new Date().date(UPDATED_DATE).year(UPDATED_YEAR).month(UPDATED_MONTH).day(UPDATED_DAY);
        return date;
    }

    @BeforeEach
    public void initTest() {
        date = createEntity(em);
    }

    @Test
    @Transactional
    void createDate() throws Exception {
        int databaseSizeBeforeCreate = dateRepository.findAll().size();
        // Create the Date
        DateDTO dateDTO = dateMapper.toDto(date);
        restDateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dateDTO)))
            .andExpect(status().isCreated());

        // Validate the Date in the database
        List<Date> dateList = dateRepository.findAll();
        assertThat(dateList).hasSize(databaseSizeBeforeCreate + 1);
        Date testDate = dateList.get(dateList.size() - 1);
        assertThat(testDate.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDate.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testDate.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testDate.getDay()).isEqualTo(DEFAULT_DAY);
    }

    @Test
    @Transactional
    void createDateWithExistingId() throws Exception {
        // Create the Date with an existing ID
        date.setId(1L);
        DateDTO dateDTO = dateMapper.toDto(date);

        int databaseSizeBeforeCreate = dateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Date in the database
        List<Date> dateList = dateRepository.findAll();
        assertThat(dateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDates() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList
        restDateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(date.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)));
    }

    @Test
    @Transactional
    void getDate() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get the date
        restDateMockMvc
            .perform(get(ENTITY_API_URL_ID, date.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(date.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY));
    }

    @Test
    @Transactional
    void getDatesByIdFiltering() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        Long id = date.getId();

        defaultDateShouldBeFound("id.equals=" + id);
        defaultDateShouldNotBeFound("id.notEquals=" + id);

        defaultDateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDateShouldNotBeFound("id.greaterThan=" + id);

        defaultDateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDateShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDatesByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where date equals to DEFAULT_DATE
        defaultDateShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the dateList where date equals to UPDATED_DATE
        defaultDateShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllDatesByDateIsInShouldWork() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where date in DEFAULT_DATE or UPDATED_DATE
        defaultDateShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the dateList where date equals to UPDATED_DATE
        defaultDateShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllDatesByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where date is not null
        defaultDateShouldBeFound("date.specified=true");

        // Get all the dateList where date is null
        defaultDateShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllDatesByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where date is greater than or equal to DEFAULT_DATE
        defaultDateShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the dateList where date is greater than or equal to UPDATED_DATE
        defaultDateShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllDatesByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where date is less than or equal to DEFAULT_DATE
        defaultDateShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the dateList where date is less than or equal to SMALLER_DATE
        defaultDateShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllDatesByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where date is less than DEFAULT_DATE
        defaultDateShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the dateList where date is less than UPDATED_DATE
        defaultDateShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllDatesByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where date is greater than DEFAULT_DATE
        defaultDateShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the dateList where date is greater than SMALLER_DATE
        defaultDateShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllDatesByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where year equals to DEFAULT_YEAR
        defaultDateShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the dateList where year equals to UPDATED_YEAR
        defaultDateShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllDatesByYearIsInShouldWork() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultDateShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the dateList where year equals to UPDATED_YEAR
        defaultDateShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllDatesByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where year is not null
        defaultDateShouldBeFound("year.specified=true");

        // Get all the dateList where year is null
        defaultDateShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllDatesByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where year is greater than or equal to DEFAULT_YEAR
        defaultDateShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the dateList where year is greater than or equal to UPDATED_YEAR
        defaultDateShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllDatesByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where year is less than or equal to DEFAULT_YEAR
        defaultDateShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the dateList where year is less than or equal to SMALLER_YEAR
        defaultDateShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    void getAllDatesByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where year is less than DEFAULT_YEAR
        defaultDateShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the dateList where year is less than UPDATED_YEAR
        defaultDateShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllDatesByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where year is greater than DEFAULT_YEAR
        defaultDateShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the dateList where year is greater than SMALLER_YEAR
        defaultDateShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    void getAllDatesByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where month equals to DEFAULT_MONTH
        defaultDateShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the dateList where month equals to UPDATED_MONTH
        defaultDateShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllDatesByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultDateShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the dateList where month equals to UPDATED_MONTH
        defaultDateShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllDatesByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where month is not null
        defaultDateShouldBeFound("month.specified=true");

        // Get all the dateList where month is null
        defaultDateShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    void getAllDatesByMonthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where month is greater than or equal to DEFAULT_MONTH
        defaultDateShouldBeFound("month.greaterThanOrEqual=" + DEFAULT_MONTH);

        // Get all the dateList where month is greater than or equal to UPDATED_MONTH
        defaultDateShouldNotBeFound("month.greaterThanOrEqual=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllDatesByMonthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where month is less than or equal to DEFAULT_MONTH
        defaultDateShouldBeFound("month.lessThanOrEqual=" + DEFAULT_MONTH);

        // Get all the dateList where month is less than or equal to SMALLER_MONTH
        defaultDateShouldNotBeFound("month.lessThanOrEqual=" + SMALLER_MONTH);
    }

    @Test
    @Transactional
    void getAllDatesByMonthIsLessThanSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where month is less than DEFAULT_MONTH
        defaultDateShouldNotBeFound("month.lessThan=" + DEFAULT_MONTH);

        // Get all the dateList where month is less than UPDATED_MONTH
        defaultDateShouldBeFound("month.lessThan=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    void getAllDatesByMonthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where month is greater than DEFAULT_MONTH
        defaultDateShouldNotBeFound("month.greaterThan=" + DEFAULT_MONTH);

        // Get all the dateList where month is greater than SMALLER_MONTH
        defaultDateShouldBeFound("month.greaterThan=" + SMALLER_MONTH);
    }

    @Test
    @Transactional
    void getAllDatesByDayIsEqualToSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where day equals to DEFAULT_DAY
        defaultDateShouldBeFound("day.equals=" + DEFAULT_DAY);

        // Get all the dateList where day equals to UPDATED_DAY
        defaultDateShouldNotBeFound("day.equals=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllDatesByDayIsInShouldWork() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where day in DEFAULT_DAY or UPDATED_DAY
        defaultDateShouldBeFound("day.in=" + DEFAULT_DAY + "," + UPDATED_DAY);

        // Get all the dateList where day equals to UPDATED_DAY
        defaultDateShouldNotBeFound("day.in=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllDatesByDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where day is not null
        defaultDateShouldBeFound("day.specified=true");

        // Get all the dateList where day is null
        defaultDateShouldNotBeFound("day.specified=false");
    }

    @Test
    @Transactional
    void getAllDatesByDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where day is greater than or equal to DEFAULT_DAY
        defaultDateShouldBeFound("day.greaterThanOrEqual=" + DEFAULT_DAY);

        // Get all the dateList where day is greater than or equal to UPDATED_DAY
        defaultDateShouldNotBeFound("day.greaterThanOrEqual=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllDatesByDayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where day is less than or equal to DEFAULT_DAY
        defaultDateShouldBeFound("day.lessThanOrEqual=" + DEFAULT_DAY);

        // Get all the dateList where day is less than or equal to SMALLER_DAY
        defaultDateShouldNotBeFound("day.lessThanOrEqual=" + SMALLER_DAY);
    }

    @Test
    @Transactional
    void getAllDatesByDayIsLessThanSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where day is less than DEFAULT_DAY
        defaultDateShouldNotBeFound("day.lessThan=" + DEFAULT_DAY);

        // Get all the dateList where day is less than UPDATED_DAY
        defaultDateShouldBeFound("day.lessThan=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllDatesByDayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        // Get all the dateList where day is greater than DEFAULT_DAY
        defaultDateShouldNotBeFound("day.greaterThan=" + DEFAULT_DAY);

        // Get all the dateList where day is greater than SMALLER_DAY
        defaultDateShouldBeFound("day.greaterThan=" + SMALLER_DAY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDateShouldBeFound(String filter) throws Exception {
        restDateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(date.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)));

        // Check, that the count call also returns 1
        restDateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDateShouldNotBeFound(String filter) throws Exception {
        restDateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDate() throws Exception {
        // Get the date
        restDateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDate() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        int databaseSizeBeforeUpdate = dateRepository.findAll().size();

        // Update the date
        Date updatedDate = dateRepository.findById(date.getId()).get();
        // Disconnect from session so that the updates on updatedDate are not directly saved in db
        em.detach(updatedDate);
        updatedDate.date(UPDATED_DATE).year(UPDATED_YEAR).month(UPDATED_MONTH).day(UPDATED_DAY);
        DateDTO dateDTO = dateMapper.toDto(updatedDate);

        restDateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dateDTO))
            )
            .andExpect(status().isOk());

        // Validate the Date in the database
        List<Date> dateList = dateRepository.findAll();
        assertThat(dateList).hasSize(databaseSizeBeforeUpdate);
        Date testDate = dateList.get(dateList.size() - 1);
        assertThat(testDate.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDate.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testDate.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testDate.getDay()).isEqualTo(UPDATED_DAY);
    }

    @Test
    @Transactional
    void putNonExistingDate() throws Exception {
        int databaseSizeBeforeUpdate = dateRepository.findAll().size();
        date.setId(count.incrementAndGet());

        // Create the Date
        DateDTO dateDTO = dateMapper.toDto(date);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Date in the database
        List<Date> dateList = dateRepository.findAll();
        assertThat(dateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDate() throws Exception {
        int databaseSizeBeforeUpdate = dateRepository.findAll().size();
        date.setId(count.incrementAndGet());

        // Create the Date
        DateDTO dateDTO = dateMapper.toDto(date);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Date in the database
        List<Date> dateList = dateRepository.findAll();
        assertThat(dateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDate() throws Exception {
        int databaseSizeBeforeUpdate = dateRepository.findAll().size();
        date.setId(count.incrementAndGet());

        // Create the Date
        DateDTO dateDTO = dateMapper.toDto(date);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Date in the database
        List<Date> dateList = dateRepository.findAll();
        assertThat(dateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDateWithPatch() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        int databaseSizeBeforeUpdate = dateRepository.findAll().size();

        // Update the date using partial update
        Date partialUpdatedDate = new Date();
        partialUpdatedDate.setId(date.getId());

        partialUpdatedDate.date(UPDATED_DATE).month(UPDATED_MONTH);

        restDateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDate))
            )
            .andExpect(status().isOk());

        // Validate the Date in the database
        List<Date> dateList = dateRepository.findAll();
        assertThat(dateList).hasSize(databaseSizeBeforeUpdate);
        Date testDate = dateList.get(dateList.size() - 1);
        assertThat(testDate.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDate.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testDate.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testDate.getDay()).isEqualTo(DEFAULT_DAY);
    }

    @Test
    @Transactional
    void fullUpdateDateWithPatch() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        int databaseSizeBeforeUpdate = dateRepository.findAll().size();

        // Update the date using partial update
        Date partialUpdatedDate = new Date();
        partialUpdatedDate.setId(date.getId());

        partialUpdatedDate.date(UPDATED_DATE).year(UPDATED_YEAR).month(UPDATED_MONTH).day(UPDATED_DAY);

        restDateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDate))
            )
            .andExpect(status().isOk());

        // Validate the Date in the database
        List<Date> dateList = dateRepository.findAll();
        assertThat(dateList).hasSize(databaseSizeBeforeUpdate);
        Date testDate = dateList.get(dateList.size() - 1);
        assertThat(testDate.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDate.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testDate.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testDate.getDay()).isEqualTo(UPDATED_DAY);
    }

    @Test
    @Transactional
    void patchNonExistingDate() throws Exception {
        int databaseSizeBeforeUpdate = dateRepository.findAll().size();
        date.setId(count.incrementAndGet());

        // Create the Date
        DateDTO dateDTO = dateMapper.toDto(date);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Date in the database
        List<Date> dateList = dateRepository.findAll();
        assertThat(dateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDate() throws Exception {
        int databaseSizeBeforeUpdate = dateRepository.findAll().size();
        date.setId(count.incrementAndGet());

        // Create the Date
        DateDTO dateDTO = dateMapper.toDto(date);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Date in the database
        List<Date> dateList = dateRepository.findAll();
        assertThat(dateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDate() throws Exception {
        int databaseSizeBeforeUpdate = dateRepository.findAll().size();
        date.setId(count.incrementAndGet());

        // Create the Date
        DateDTO dateDTO = dateMapper.toDto(date);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDateMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Date in the database
        List<Date> dateList = dateRepository.findAll();
        assertThat(dateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDate() throws Exception {
        // Initialize the database
        dateRepository.saveAndFlush(date);

        int databaseSizeBeforeDelete = dateRepository.findAll().size();

        // Delete the date
        restDateMockMvc
            .perform(delete(ENTITY_API_URL_ID, date.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Date> dateList = dateRepository.findAll();
        assertThat(dateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
