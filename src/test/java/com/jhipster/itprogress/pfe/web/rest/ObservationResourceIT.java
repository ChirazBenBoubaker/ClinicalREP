package com.jhipster.itprogress.pfe.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.itprogress.pfe.IntegrationTest;
import com.jhipster.itprogress.pfe.domain.Observation;
import com.jhipster.itprogress.pfe.repository.ObservationRepository;
import com.jhipster.itprogress.pfe.service.criteria.ObservationCriteria;
import com.jhipster.itprogress.pfe.service.dto.ObservationDTO;
import com.jhipster.itprogress.pfe.service.mapper.ObservationMapper;
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
 * Integration tests for the {@link ObservationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ObservationResourceIT {

    private static final Float DEFAULT_BODY_HEIGHT = 1F;
    private static final Float UPDATED_BODY_HEIGHT = 2F;
    private static final Float SMALLER_BODY_HEIGHT = 1F - 1F;

    private static final Float DEFAULT_BODY_WEIGHT = 1F;
    private static final Float UPDATED_BODY_WEIGHT = 2F;
    private static final Float SMALLER_BODY_WEIGHT = 1F - 1F;

    private static final Float DEFAULT_BODY_MASS = 1F;
    private static final Float UPDATED_BODY_MASS = 2F;
    private static final Float SMALLER_BODY_MASS = 1F - 1F;

    private static final Float DEFAULT_PAINSEVERITY = 1F;
    private static final Float UPDATED_PAINSEVERITY = 2F;
    private static final Float SMALLER_PAINSEVERITY = 1F - 1F;

    private static final Float DEFAULT_BLOOD_PRESSURE = 1F;
    private static final Float UPDATED_BLOOD_PRESSURE = 2F;
    private static final Float SMALLER_BLOOD_PRESSURE = 1F - 1F;

    private static final Float DEFAULT_TOBACCOSMOKINGSTATUS_NHIS = 1F;
    private static final Float UPDATED_TOBACCOSMOKINGSTATUS_NHIS = 2F;
    private static final Float SMALLER_TOBACCOSMOKINGSTATUS_NHIS = 1F - 1F;

    private static final Float DEFAULT_CREATININE = 1F;
    private static final Float UPDATED_CREATININE = 2F;
    private static final Float SMALLER_CREATININE = 1F - 1F;

    private static final Float DEFAULT_CALCIUM = 1F;
    private static final Float UPDATED_CALCIUM = 2F;
    private static final Float SMALLER_CALCIUM = 1F - 1F;

    private static final Float DEFAULT_SODIUM = 1F;
    private static final Float UPDATED_SODIUM = 2F;
    private static final Float SMALLER_SODIUM = 1F - 1F;

    private static final Float DEFAULT_POTASSIUM = 1F;
    private static final Float UPDATED_POTASSIUM = 2F;
    private static final Float SMALLER_POTASSIUM = 1F - 1F;

    private static final Float DEFAULT_CHLORIDE = 1F;
    private static final Float UPDATED_CHLORIDE = 2F;
    private static final Float SMALLER_CHLORIDE = 1F - 1F;

    private static final Float DEFAULT_CARBON_DIOXIDE = 1F;
    private static final Float UPDATED_CARBON_DIOXIDE = 2F;
    private static final Float SMALLER_CARBON_DIOXIDE = 1F - 1F;

    private static final Float DEFAULT_GLUCOSE = 1F;
    private static final Float UPDATED_GLUCOSE = 2F;
    private static final Float SMALLER_GLUCOSE = 1F - 1F;

    private static final Float DEFAULT_UREA_NITROGEN = 1F;
    private static final Float UPDATED_UREA_NITROGEN = 2F;
    private static final Float SMALLER_UREA_NITROGEN = 1F - 1F;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PATIENT_UID = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_UID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/observations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private ObservationMapper observationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObservationMockMvc;

    private Observation observation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Observation createEntity(EntityManager em) {
        Observation observation = new Observation()
            .bodyHeight(DEFAULT_BODY_HEIGHT)
            .bodyWeight(DEFAULT_BODY_WEIGHT)
            .bodyMass(DEFAULT_BODY_MASS)
            .painseverity(DEFAULT_PAINSEVERITY)
            .bloodPressure(DEFAULT_BLOOD_PRESSURE)
            .tobaccosmokingstatusNHIS(DEFAULT_TOBACCOSMOKINGSTATUS_NHIS)
            .creatinine(DEFAULT_CREATININE)
            .calcium(DEFAULT_CALCIUM)
            .sodium(DEFAULT_SODIUM)
            .potassium(DEFAULT_POTASSIUM)
            .chloride(DEFAULT_CHLORIDE)
            .carbonDioxide(DEFAULT_CARBON_DIOXIDE)
            .glucose(DEFAULT_GLUCOSE)
            .ureaNitrogen(DEFAULT_UREA_NITROGEN)
            .date(DEFAULT_DATE)
            .patientUID(DEFAULT_PATIENT_UID);
        return observation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Observation createUpdatedEntity(EntityManager em) {
        Observation observation = new Observation()
            .bodyHeight(UPDATED_BODY_HEIGHT)
            .bodyWeight(UPDATED_BODY_WEIGHT)
            .bodyMass(UPDATED_BODY_MASS)
            .painseverity(UPDATED_PAINSEVERITY)
            .bloodPressure(UPDATED_BLOOD_PRESSURE)
            .tobaccosmokingstatusNHIS(UPDATED_TOBACCOSMOKINGSTATUS_NHIS)
            .creatinine(UPDATED_CREATININE)
            .calcium(UPDATED_CALCIUM)
            .sodium(UPDATED_SODIUM)
            .potassium(UPDATED_POTASSIUM)
            .chloride(UPDATED_CHLORIDE)
            .carbonDioxide(UPDATED_CARBON_DIOXIDE)
            .glucose(UPDATED_GLUCOSE)
            .ureaNitrogen(UPDATED_UREA_NITROGEN)
            .date(UPDATED_DATE)
            .patientUID(UPDATED_PATIENT_UID);
        return observation;
    }

    @BeforeEach
    public void initTest() {
        observation = createEntity(em);
    }

    @Test
    @Transactional
    void createObservation() throws Exception {
        int databaseSizeBeforeCreate = observationRepository.findAll().size();
        // Create the Observation
        ObservationDTO observationDTO = observationMapper.toDto(observation);
        restObservationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(observationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeCreate + 1);
        Observation testObservation = observationList.get(observationList.size() - 1);
        assertThat(testObservation.getBodyHeight()).isEqualTo(DEFAULT_BODY_HEIGHT);
        assertThat(testObservation.getBodyWeight()).isEqualTo(DEFAULT_BODY_WEIGHT);
        assertThat(testObservation.getBodyMass()).isEqualTo(DEFAULT_BODY_MASS);
        assertThat(testObservation.getPainseverity()).isEqualTo(DEFAULT_PAINSEVERITY);
        assertThat(testObservation.getBloodPressure()).isEqualTo(DEFAULT_BLOOD_PRESSURE);
        assertThat(testObservation.getTobaccosmokingstatusNHIS()).isEqualTo(DEFAULT_TOBACCOSMOKINGSTATUS_NHIS);
        assertThat(testObservation.getCreatinine()).isEqualTo(DEFAULT_CREATININE);
        assertThat(testObservation.getCalcium()).isEqualTo(DEFAULT_CALCIUM);
        assertThat(testObservation.getSodium()).isEqualTo(DEFAULT_SODIUM);
        assertThat(testObservation.getPotassium()).isEqualTo(DEFAULT_POTASSIUM);
        assertThat(testObservation.getChloride()).isEqualTo(DEFAULT_CHLORIDE);
        assertThat(testObservation.getCarbonDioxide()).isEqualTo(DEFAULT_CARBON_DIOXIDE);
        assertThat(testObservation.getGlucose()).isEqualTo(DEFAULT_GLUCOSE);
        assertThat(testObservation.getUreaNitrogen()).isEqualTo(DEFAULT_UREA_NITROGEN);
        assertThat(testObservation.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testObservation.getPatientUID()).isEqualTo(DEFAULT_PATIENT_UID);
    }

    @Test
    @Transactional
    void createObservationWithExistingId() throws Exception {
        // Create the Observation with an existing ID
        observation.setId(1L);
        ObservationDTO observationDTO = observationMapper.toDto(observation);

        int databaseSizeBeforeCreate = observationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restObservationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(observationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllObservations() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList
        restObservationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(observation.getId().intValue())))
            .andExpect(jsonPath("$.[*].bodyHeight").value(hasItem(DEFAULT_BODY_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].bodyWeight").value(hasItem(DEFAULT_BODY_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].bodyMass").value(hasItem(DEFAULT_BODY_MASS.doubleValue())))
            .andExpect(jsonPath("$.[*].painseverity").value(hasItem(DEFAULT_PAINSEVERITY.doubleValue())))
            .andExpect(jsonPath("$.[*].bloodPressure").value(hasItem(DEFAULT_BLOOD_PRESSURE.doubleValue())))
            .andExpect(jsonPath("$.[*].tobaccosmokingstatusNHIS").value(hasItem(DEFAULT_TOBACCOSMOKINGSTATUS_NHIS.doubleValue())))
            .andExpect(jsonPath("$.[*].creatinine").value(hasItem(DEFAULT_CREATININE.doubleValue())))
            .andExpect(jsonPath("$.[*].calcium").value(hasItem(DEFAULT_CALCIUM.doubleValue())))
            .andExpect(jsonPath("$.[*].sodium").value(hasItem(DEFAULT_SODIUM.doubleValue())))
            .andExpect(jsonPath("$.[*].potassium").value(hasItem(DEFAULT_POTASSIUM.doubleValue())))
            .andExpect(jsonPath("$.[*].chloride").value(hasItem(DEFAULT_CHLORIDE.doubleValue())))
            .andExpect(jsonPath("$.[*].carbonDioxide").value(hasItem(DEFAULT_CARBON_DIOXIDE.doubleValue())))
            .andExpect(jsonPath("$.[*].glucose").value(hasItem(DEFAULT_GLUCOSE.doubleValue())))
            .andExpect(jsonPath("$.[*].ureaNitrogen").value(hasItem(DEFAULT_UREA_NITROGEN.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].patientUID").value(hasItem(DEFAULT_PATIENT_UID)));
    }

    @Test
    @Transactional
    void getObservation() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get the observation
        restObservationMockMvc
            .perform(get(ENTITY_API_URL_ID, observation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(observation.getId().intValue()))
            .andExpect(jsonPath("$.bodyHeight").value(DEFAULT_BODY_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.bodyWeight").value(DEFAULT_BODY_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.bodyMass").value(DEFAULT_BODY_MASS.doubleValue()))
            .andExpect(jsonPath("$.painseverity").value(DEFAULT_PAINSEVERITY.doubleValue()))
            .andExpect(jsonPath("$.bloodPressure").value(DEFAULT_BLOOD_PRESSURE.doubleValue()))
            .andExpect(jsonPath("$.tobaccosmokingstatusNHIS").value(DEFAULT_TOBACCOSMOKINGSTATUS_NHIS.doubleValue()))
            .andExpect(jsonPath("$.creatinine").value(DEFAULT_CREATININE.doubleValue()))
            .andExpect(jsonPath("$.calcium").value(DEFAULT_CALCIUM.doubleValue()))
            .andExpect(jsonPath("$.sodium").value(DEFAULT_SODIUM.doubleValue()))
            .andExpect(jsonPath("$.potassium").value(DEFAULT_POTASSIUM.doubleValue()))
            .andExpect(jsonPath("$.chloride").value(DEFAULT_CHLORIDE.doubleValue()))
            .andExpect(jsonPath("$.carbonDioxide").value(DEFAULT_CARBON_DIOXIDE.doubleValue()))
            .andExpect(jsonPath("$.glucose").value(DEFAULT_GLUCOSE.doubleValue()))
            .andExpect(jsonPath("$.ureaNitrogen").value(DEFAULT_UREA_NITROGEN.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.patientUID").value(DEFAULT_PATIENT_UID));
    }

    @Test
    @Transactional
    void getObservationsByIdFiltering() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        Long id = observation.getId();

        defaultObservationShouldBeFound("id.equals=" + id);
        defaultObservationShouldNotBeFound("id.notEquals=" + id);

        defaultObservationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultObservationShouldNotBeFound("id.greaterThan=" + id);

        defaultObservationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultObservationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyHeight equals to DEFAULT_BODY_HEIGHT
        defaultObservationShouldBeFound("bodyHeight.equals=" + DEFAULT_BODY_HEIGHT);

        // Get all the observationList where bodyHeight equals to UPDATED_BODY_HEIGHT
        defaultObservationShouldNotBeFound("bodyHeight.equals=" + UPDATED_BODY_HEIGHT);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyHeightIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyHeight in DEFAULT_BODY_HEIGHT or UPDATED_BODY_HEIGHT
        defaultObservationShouldBeFound("bodyHeight.in=" + DEFAULT_BODY_HEIGHT + "," + UPDATED_BODY_HEIGHT);

        // Get all the observationList where bodyHeight equals to UPDATED_BODY_HEIGHT
        defaultObservationShouldNotBeFound("bodyHeight.in=" + UPDATED_BODY_HEIGHT);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyHeight is not null
        defaultObservationShouldBeFound("bodyHeight.specified=true");

        // Get all the observationList where bodyHeight is null
        defaultObservationShouldNotBeFound("bodyHeight.specified=false");
    }

    @Test
    @Transactional
    void getAllObservationsByBodyHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyHeight is greater than or equal to DEFAULT_BODY_HEIGHT
        defaultObservationShouldBeFound("bodyHeight.greaterThanOrEqual=" + DEFAULT_BODY_HEIGHT);

        // Get all the observationList where bodyHeight is greater than or equal to UPDATED_BODY_HEIGHT
        defaultObservationShouldNotBeFound("bodyHeight.greaterThanOrEqual=" + UPDATED_BODY_HEIGHT);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyHeight is less than or equal to DEFAULT_BODY_HEIGHT
        defaultObservationShouldBeFound("bodyHeight.lessThanOrEqual=" + DEFAULT_BODY_HEIGHT);

        // Get all the observationList where bodyHeight is less than or equal to SMALLER_BODY_HEIGHT
        defaultObservationShouldNotBeFound("bodyHeight.lessThanOrEqual=" + SMALLER_BODY_HEIGHT);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyHeight is less than DEFAULT_BODY_HEIGHT
        defaultObservationShouldNotBeFound("bodyHeight.lessThan=" + DEFAULT_BODY_HEIGHT);

        // Get all the observationList where bodyHeight is less than UPDATED_BODY_HEIGHT
        defaultObservationShouldBeFound("bodyHeight.lessThan=" + UPDATED_BODY_HEIGHT);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyHeight is greater than DEFAULT_BODY_HEIGHT
        defaultObservationShouldNotBeFound("bodyHeight.greaterThan=" + DEFAULT_BODY_HEIGHT);

        // Get all the observationList where bodyHeight is greater than SMALLER_BODY_HEIGHT
        defaultObservationShouldBeFound("bodyHeight.greaterThan=" + SMALLER_BODY_HEIGHT);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyWeight equals to DEFAULT_BODY_WEIGHT
        defaultObservationShouldBeFound("bodyWeight.equals=" + DEFAULT_BODY_WEIGHT);

        // Get all the observationList where bodyWeight equals to UPDATED_BODY_WEIGHT
        defaultObservationShouldNotBeFound("bodyWeight.equals=" + UPDATED_BODY_WEIGHT);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyWeightIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyWeight in DEFAULT_BODY_WEIGHT or UPDATED_BODY_WEIGHT
        defaultObservationShouldBeFound("bodyWeight.in=" + DEFAULT_BODY_WEIGHT + "," + UPDATED_BODY_WEIGHT);

        // Get all the observationList where bodyWeight equals to UPDATED_BODY_WEIGHT
        defaultObservationShouldNotBeFound("bodyWeight.in=" + UPDATED_BODY_WEIGHT);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyWeight is not null
        defaultObservationShouldBeFound("bodyWeight.specified=true");

        // Get all the observationList where bodyWeight is null
        defaultObservationShouldNotBeFound("bodyWeight.specified=false");
    }

    @Test
    @Transactional
    void getAllObservationsByBodyWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyWeight is greater than or equal to DEFAULT_BODY_WEIGHT
        defaultObservationShouldBeFound("bodyWeight.greaterThanOrEqual=" + DEFAULT_BODY_WEIGHT);

        // Get all the observationList where bodyWeight is greater than or equal to UPDATED_BODY_WEIGHT
        defaultObservationShouldNotBeFound("bodyWeight.greaterThanOrEqual=" + UPDATED_BODY_WEIGHT);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyWeight is less than or equal to DEFAULT_BODY_WEIGHT
        defaultObservationShouldBeFound("bodyWeight.lessThanOrEqual=" + DEFAULT_BODY_WEIGHT);

        // Get all the observationList where bodyWeight is less than or equal to SMALLER_BODY_WEIGHT
        defaultObservationShouldNotBeFound("bodyWeight.lessThanOrEqual=" + SMALLER_BODY_WEIGHT);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyWeight is less than DEFAULT_BODY_WEIGHT
        defaultObservationShouldNotBeFound("bodyWeight.lessThan=" + DEFAULT_BODY_WEIGHT);

        // Get all the observationList where bodyWeight is less than UPDATED_BODY_WEIGHT
        defaultObservationShouldBeFound("bodyWeight.lessThan=" + UPDATED_BODY_WEIGHT);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyWeight is greater than DEFAULT_BODY_WEIGHT
        defaultObservationShouldNotBeFound("bodyWeight.greaterThan=" + DEFAULT_BODY_WEIGHT);

        // Get all the observationList where bodyWeight is greater than SMALLER_BODY_WEIGHT
        defaultObservationShouldBeFound("bodyWeight.greaterThan=" + SMALLER_BODY_WEIGHT);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyMassIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyMass equals to DEFAULT_BODY_MASS
        defaultObservationShouldBeFound("bodyMass.equals=" + DEFAULT_BODY_MASS);

        // Get all the observationList where bodyMass equals to UPDATED_BODY_MASS
        defaultObservationShouldNotBeFound("bodyMass.equals=" + UPDATED_BODY_MASS);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyMassIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyMass in DEFAULT_BODY_MASS or UPDATED_BODY_MASS
        defaultObservationShouldBeFound("bodyMass.in=" + DEFAULT_BODY_MASS + "," + UPDATED_BODY_MASS);

        // Get all the observationList where bodyMass equals to UPDATED_BODY_MASS
        defaultObservationShouldNotBeFound("bodyMass.in=" + UPDATED_BODY_MASS);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyMassIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyMass is not null
        defaultObservationShouldBeFound("bodyMass.specified=true");

        // Get all the observationList where bodyMass is null
        defaultObservationShouldNotBeFound("bodyMass.specified=false");
    }

    @Test
    @Transactional
    void getAllObservationsByBodyMassIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyMass is greater than or equal to DEFAULT_BODY_MASS
        defaultObservationShouldBeFound("bodyMass.greaterThanOrEqual=" + DEFAULT_BODY_MASS);

        // Get all the observationList where bodyMass is greater than or equal to UPDATED_BODY_MASS
        defaultObservationShouldNotBeFound("bodyMass.greaterThanOrEqual=" + UPDATED_BODY_MASS);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyMassIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyMass is less than or equal to DEFAULT_BODY_MASS
        defaultObservationShouldBeFound("bodyMass.lessThanOrEqual=" + DEFAULT_BODY_MASS);

        // Get all the observationList where bodyMass is less than or equal to SMALLER_BODY_MASS
        defaultObservationShouldNotBeFound("bodyMass.lessThanOrEqual=" + SMALLER_BODY_MASS);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyMassIsLessThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyMass is less than DEFAULT_BODY_MASS
        defaultObservationShouldNotBeFound("bodyMass.lessThan=" + DEFAULT_BODY_MASS);

        // Get all the observationList where bodyMass is less than UPDATED_BODY_MASS
        defaultObservationShouldBeFound("bodyMass.lessThan=" + UPDATED_BODY_MASS);
    }

    @Test
    @Transactional
    void getAllObservationsByBodyMassIsGreaterThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bodyMass is greater than DEFAULT_BODY_MASS
        defaultObservationShouldNotBeFound("bodyMass.greaterThan=" + DEFAULT_BODY_MASS);

        // Get all the observationList where bodyMass is greater than SMALLER_BODY_MASS
        defaultObservationShouldBeFound("bodyMass.greaterThan=" + SMALLER_BODY_MASS);
    }

    @Test
    @Transactional
    void getAllObservationsByPainseverityIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where painseverity equals to DEFAULT_PAINSEVERITY
        defaultObservationShouldBeFound("painseverity.equals=" + DEFAULT_PAINSEVERITY);

        // Get all the observationList where painseverity equals to UPDATED_PAINSEVERITY
        defaultObservationShouldNotBeFound("painseverity.equals=" + UPDATED_PAINSEVERITY);
    }

    @Test
    @Transactional
    void getAllObservationsByPainseverityIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where painseverity in DEFAULT_PAINSEVERITY or UPDATED_PAINSEVERITY
        defaultObservationShouldBeFound("painseverity.in=" + DEFAULT_PAINSEVERITY + "," + UPDATED_PAINSEVERITY);

        // Get all the observationList where painseverity equals to UPDATED_PAINSEVERITY
        defaultObservationShouldNotBeFound("painseverity.in=" + UPDATED_PAINSEVERITY);
    }

    @Test
    @Transactional
    void getAllObservationsByPainseverityIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where painseverity is not null
        defaultObservationShouldBeFound("painseverity.specified=true");

        // Get all the observationList where painseverity is null
        defaultObservationShouldNotBeFound("painseverity.specified=false");
    }

    @Test
    @Transactional
    void getAllObservationsByPainseverityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where painseverity is greater than or equal to DEFAULT_PAINSEVERITY
        defaultObservationShouldBeFound("painseverity.greaterThanOrEqual=" + DEFAULT_PAINSEVERITY);

        // Get all the observationList where painseverity is greater than or equal to UPDATED_PAINSEVERITY
        defaultObservationShouldNotBeFound("painseverity.greaterThanOrEqual=" + UPDATED_PAINSEVERITY);
    }

    @Test
    @Transactional
    void getAllObservationsByPainseverityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where painseverity is less than or equal to DEFAULT_PAINSEVERITY
        defaultObservationShouldBeFound("painseverity.lessThanOrEqual=" + DEFAULT_PAINSEVERITY);

        // Get all the observationList where painseverity is less than or equal to SMALLER_PAINSEVERITY
        defaultObservationShouldNotBeFound("painseverity.lessThanOrEqual=" + SMALLER_PAINSEVERITY);
    }

    @Test
    @Transactional
    void getAllObservationsByPainseverityIsLessThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where painseverity is less than DEFAULT_PAINSEVERITY
        defaultObservationShouldNotBeFound("painseverity.lessThan=" + DEFAULT_PAINSEVERITY);

        // Get all the observationList where painseverity is less than UPDATED_PAINSEVERITY
        defaultObservationShouldBeFound("painseverity.lessThan=" + UPDATED_PAINSEVERITY);
    }

    @Test
    @Transactional
    void getAllObservationsByPainseverityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where painseverity is greater than DEFAULT_PAINSEVERITY
        defaultObservationShouldNotBeFound("painseverity.greaterThan=" + DEFAULT_PAINSEVERITY);

        // Get all the observationList where painseverity is greater than SMALLER_PAINSEVERITY
        defaultObservationShouldBeFound("painseverity.greaterThan=" + SMALLER_PAINSEVERITY);
    }

    @Test
    @Transactional
    void getAllObservationsByBloodPressureIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bloodPressure equals to DEFAULT_BLOOD_PRESSURE
        defaultObservationShouldBeFound("bloodPressure.equals=" + DEFAULT_BLOOD_PRESSURE);

        // Get all the observationList where bloodPressure equals to UPDATED_BLOOD_PRESSURE
        defaultObservationShouldNotBeFound("bloodPressure.equals=" + UPDATED_BLOOD_PRESSURE);
    }

    @Test
    @Transactional
    void getAllObservationsByBloodPressureIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bloodPressure in DEFAULT_BLOOD_PRESSURE or UPDATED_BLOOD_PRESSURE
        defaultObservationShouldBeFound("bloodPressure.in=" + DEFAULT_BLOOD_PRESSURE + "," + UPDATED_BLOOD_PRESSURE);

        // Get all the observationList where bloodPressure equals to UPDATED_BLOOD_PRESSURE
        defaultObservationShouldNotBeFound("bloodPressure.in=" + UPDATED_BLOOD_PRESSURE);
    }

    @Test
    @Transactional
    void getAllObservationsByBloodPressureIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bloodPressure is not null
        defaultObservationShouldBeFound("bloodPressure.specified=true");

        // Get all the observationList where bloodPressure is null
        defaultObservationShouldNotBeFound("bloodPressure.specified=false");
    }

    @Test
    @Transactional
    void getAllObservationsByBloodPressureIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bloodPressure is greater than or equal to DEFAULT_BLOOD_PRESSURE
        defaultObservationShouldBeFound("bloodPressure.greaterThanOrEqual=" + DEFAULT_BLOOD_PRESSURE);

        // Get all the observationList where bloodPressure is greater than or equal to UPDATED_BLOOD_PRESSURE
        defaultObservationShouldNotBeFound("bloodPressure.greaterThanOrEqual=" + UPDATED_BLOOD_PRESSURE);
    }

    @Test
    @Transactional
    void getAllObservationsByBloodPressureIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bloodPressure is less than or equal to DEFAULT_BLOOD_PRESSURE
        defaultObservationShouldBeFound("bloodPressure.lessThanOrEqual=" + DEFAULT_BLOOD_PRESSURE);

        // Get all the observationList where bloodPressure is less than or equal to SMALLER_BLOOD_PRESSURE
        defaultObservationShouldNotBeFound("bloodPressure.lessThanOrEqual=" + SMALLER_BLOOD_PRESSURE);
    }

    @Test
    @Transactional
    void getAllObservationsByBloodPressureIsLessThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bloodPressure is less than DEFAULT_BLOOD_PRESSURE
        defaultObservationShouldNotBeFound("bloodPressure.lessThan=" + DEFAULT_BLOOD_PRESSURE);

        // Get all the observationList where bloodPressure is less than UPDATED_BLOOD_PRESSURE
        defaultObservationShouldBeFound("bloodPressure.lessThan=" + UPDATED_BLOOD_PRESSURE);
    }

    @Test
    @Transactional
    void getAllObservationsByBloodPressureIsGreaterThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where bloodPressure is greater than DEFAULT_BLOOD_PRESSURE
        defaultObservationShouldNotBeFound("bloodPressure.greaterThan=" + DEFAULT_BLOOD_PRESSURE);

        // Get all the observationList where bloodPressure is greater than SMALLER_BLOOD_PRESSURE
        defaultObservationShouldBeFound("bloodPressure.greaterThan=" + SMALLER_BLOOD_PRESSURE);
    }

    @Test
    @Transactional
    void getAllObservationsByTobaccosmokingstatusNHISIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where tobaccosmokingstatusNHIS equals to DEFAULT_TOBACCOSMOKINGSTATUS_NHIS
        defaultObservationShouldBeFound("tobaccosmokingstatusNHIS.equals=" + DEFAULT_TOBACCOSMOKINGSTATUS_NHIS);

        // Get all the observationList where tobaccosmokingstatusNHIS equals to UPDATED_TOBACCOSMOKINGSTATUS_NHIS
        defaultObservationShouldNotBeFound("tobaccosmokingstatusNHIS.equals=" + UPDATED_TOBACCOSMOKINGSTATUS_NHIS);
    }

    @Test
    @Transactional
    void getAllObservationsByTobaccosmokingstatusNHISIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where tobaccosmokingstatusNHIS in DEFAULT_TOBACCOSMOKINGSTATUS_NHIS or UPDATED_TOBACCOSMOKINGSTATUS_NHIS
        defaultObservationShouldBeFound(
            "tobaccosmokingstatusNHIS.in=" + DEFAULT_TOBACCOSMOKINGSTATUS_NHIS + "," + UPDATED_TOBACCOSMOKINGSTATUS_NHIS
        );

        // Get all the observationList where tobaccosmokingstatusNHIS equals to UPDATED_TOBACCOSMOKINGSTATUS_NHIS
        defaultObservationShouldNotBeFound("tobaccosmokingstatusNHIS.in=" + UPDATED_TOBACCOSMOKINGSTATUS_NHIS);
    }

    @Test
    @Transactional
    void getAllObservationsByTobaccosmokingstatusNHISIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where tobaccosmokingstatusNHIS is not null
        defaultObservationShouldBeFound("tobaccosmokingstatusNHIS.specified=true");

        // Get all the observationList where tobaccosmokingstatusNHIS is null
        defaultObservationShouldNotBeFound("tobaccosmokingstatusNHIS.specified=false");
    }

    @Test
    @Transactional
    void getAllObservationsByTobaccosmokingstatusNHISIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where tobaccosmokingstatusNHIS is greater than or equal to DEFAULT_TOBACCOSMOKINGSTATUS_NHIS
        defaultObservationShouldBeFound("tobaccosmokingstatusNHIS.greaterThanOrEqual=" + DEFAULT_TOBACCOSMOKINGSTATUS_NHIS);

        // Get all the observationList where tobaccosmokingstatusNHIS is greater than or equal to UPDATED_TOBACCOSMOKINGSTATUS_NHIS
        defaultObservationShouldNotBeFound("tobaccosmokingstatusNHIS.greaterThanOrEqual=" + UPDATED_TOBACCOSMOKINGSTATUS_NHIS);
    }

    @Test
    @Transactional
    void getAllObservationsByTobaccosmokingstatusNHISIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where tobaccosmokingstatusNHIS is less than or equal to DEFAULT_TOBACCOSMOKINGSTATUS_NHIS
        defaultObservationShouldBeFound("tobaccosmokingstatusNHIS.lessThanOrEqual=" + DEFAULT_TOBACCOSMOKINGSTATUS_NHIS);

        // Get all the observationList where tobaccosmokingstatusNHIS is less than or equal to SMALLER_TOBACCOSMOKINGSTATUS_NHIS
        defaultObservationShouldNotBeFound("tobaccosmokingstatusNHIS.lessThanOrEqual=" + SMALLER_TOBACCOSMOKINGSTATUS_NHIS);
    }

    @Test
    @Transactional
    void getAllObservationsByTobaccosmokingstatusNHISIsLessThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where tobaccosmokingstatusNHIS is less than DEFAULT_TOBACCOSMOKINGSTATUS_NHIS
        defaultObservationShouldNotBeFound("tobaccosmokingstatusNHIS.lessThan=" + DEFAULT_TOBACCOSMOKINGSTATUS_NHIS);

        // Get all the observationList where tobaccosmokingstatusNHIS is less than UPDATED_TOBACCOSMOKINGSTATUS_NHIS
        defaultObservationShouldBeFound("tobaccosmokingstatusNHIS.lessThan=" + UPDATED_TOBACCOSMOKINGSTATUS_NHIS);
    }

    @Test
    @Transactional
    void getAllObservationsByTobaccosmokingstatusNHISIsGreaterThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where tobaccosmokingstatusNHIS is greater than DEFAULT_TOBACCOSMOKINGSTATUS_NHIS
        defaultObservationShouldNotBeFound("tobaccosmokingstatusNHIS.greaterThan=" + DEFAULT_TOBACCOSMOKINGSTATUS_NHIS);

        // Get all the observationList where tobaccosmokingstatusNHIS is greater than SMALLER_TOBACCOSMOKINGSTATUS_NHIS
        defaultObservationShouldBeFound("tobaccosmokingstatusNHIS.greaterThan=" + SMALLER_TOBACCOSMOKINGSTATUS_NHIS);
    }

    @Test
    @Transactional
    void getAllObservationsByCreatinineIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where creatinine equals to DEFAULT_CREATININE
        defaultObservationShouldBeFound("creatinine.equals=" + DEFAULT_CREATININE);

        // Get all the observationList where creatinine equals to UPDATED_CREATININE
        defaultObservationShouldNotBeFound("creatinine.equals=" + UPDATED_CREATININE);
    }

    @Test
    @Transactional
    void getAllObservationsByCreatinineIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where creatinine in DEFAULT_CREATININE or UPDATED_CREATININE
        defaultObservationShouldBeFound("creatinine.in=" + DEFAULT_CREATININE + "," + UPDATED_CREATININE);

        // Get all the observationList where creatinine equals to UPDATED_CREATININE
        defaultObservationShouldNotBeFound("creatinine.in=" + UPDATED_CREATININE);
    }

    @Test
    @Transactional
    void getAllObservationsByCreatinineIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where creatinine is not null
        defaultObservationShouldBeFound("creatinine.specified=true");

        // Get all the observationList where creatinine is null
        defaultObservationShouldNotBeFound("creatinine.specified=false");
    }

    @Test
    @Transactional
    void getAllObservationsByCreatinineIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where creatinine is greater than or equal to DEFAULT_CREATININE
        defaultObservationShouldBeFound("creatinine.greaterThanOrEqual=" + DEFAULT_CREATININE);

        // Get all the observationList where creatinine is greater than or equal to UPDATED_CREATININE
        defaultObservationShouldNotBeFound("creatinine.greaterThanOrEqual=" + UPDATED_CREATININE);
    }

    @Test
    @Transactional
    void getAllObservationsByCreatinineIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where creatinine is less than or equal to DEFAULT_CREATININE
        defaultObservationShouldBeFound("creatinine.lessThanOrEqual=" + DEFAULT_CREATININE);

        // Get all the observationList where creatinine is less than or equal to SMALLER_CREATININE
        defaultObservationShouldNotBeFound("creatinine.lessThanOrEqual=" + SMALLER_CREATININE);
    }

    @Test
    @Transactional
    void getAllObservationsByCreatinineIsLessThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where creatinine is less than DEFAULT_CREATININE
        defaultObservationShouldNotBeFound("creatinine.lessThan=" + DEFAULT_CREATININE);

        // Get all the observationList where creatinine is less than UPDATED_CREATININE
        defaultObservationShouldBeFound("creatinine.lessThan=" + UPDATED_CREATININE);
    }

    @Test
    @Transactional
    void getAllObservationsByCreatinineIsGreaterThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where creatinine is greater than DEFAULT_CREATININE
        defaultObservationShouldNotBeFound("creatinine.greaterThan=" + DEFAULT_CREATININE);

        // Get all the observationList where creatinine is greater than SMALLER_CREATININE
        defaultObservationShouldBeFound("creatinine.greaterThan=" + SMALLER_CREATININE);
    }

    @Test
    @Transactional
    void getAllObservationsByCalciumIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where calcium equals to DEFAULT_CALCIUM
        defaultObservationShouldBeFound("calcium.equals=" + DEFAULT_CALCIUM);

        // Get all the observationList where calcium equals to UPDATED_CALCIUM
        defaultObservationShouldNotBeFound("calcium.equals=" + UPDATED_CALCIUM);
    }

    @Test
    @Transactional
    void getAllObservationsByCalciumIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where calcium in DEFAULT_CALCIUM or UPDATED_CALCIUM
        defaultObservationShouldBeFound("calcium.in=" + DEFAULT_CALCIUM + "," + UPDATED_CALCIUM);

        // Get all the observationList where calcium equals to UPDATED_CALCIUM
        defaultObservationShouldNotBeFound("calcium.in=" + UPDATED_CALCIUM);
    }

    @Test
    @Transactional
    void getAllObservationsByCalciumIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where calcium is not null
        defaultObservationShouldBeFound("calcium.specified=true");

        // Get all the observationList where calcium is null
        defaultObservationShouldNotBeFound("calcium.specified=false");
    }

    @Test
    @Transactional
    void getAllObservationsByCalciumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where calcium is greater than or equal to DEFAULT_CALCIUM
        defaultObservationShouldBeFound("calcium.greaterThanOrEqual=" + DEFAULT_CALCIUM);

        // Get all the observationList where calcium is greater than or equal to UPDATED_CALCIUM
        defaultObservationShouldNotBeFound("calcium.greaterThanOrEqual=" + UPDATED_CALCIUM);
    }

    @Test
    @Transactional
    void getAllObservationsByCalciumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where calcium is less than or equal to DEFAULT_CALCIUM
        defaultObservationShouldBeFound("calcium.lessThanOrEqual=" + DEFAULT_CALCIUM);

        // Get all the observationList where calcium is less than or equal to SMALLER_CALCIUM
        defaultObservationShouldNotBeFound("calcium.lessThanOrEqual=" + SMALLER_CALCIUM);
    }

    @Test
    @Transactional
    void getAllObservationsByCalciumIsLessThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where calcium is less than DEFAULT_CALCIUM
        defaultObservationShouldNotBeFound("calcium.lessThan=" + DEFAULT_CALCIUM);

        // Get all the observationList where calcium is less than UPDATED_CALCIUM
        defaultObservationShouldBeFound("calcium.lessThan=" + UPDATED_CALCIUM);
    }

    @Test
    @Transactional
    void getAllObservationsByCalciumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where calcium is greater than DEFAULT_CALCIUM
        defaultObservationShouldNotBeFound("calcium.greaterThan=" + DEFAULT_CALCIUM);

        // Get all the observationList where calcium is greater than SMALLER_CALCIUM
        defaultObservationShouldBeFound("calcium.greaterThan=" + SMALLER_CALCIUM);
    }

    @Test
    @Transactional
    void getAllObservationsBySodiumIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where sodium equals to DEFAULT_SODIUM
        defaultObservationShouldBeFound("sodium.equals=" + DEFAULT_SODIUM);

        // Get all the observationList where sodium equals to UPDATED_SODIUM
        defaultObservationShouldNotBeFound("sodium.equals=" + UPDATED_SODIUM);
    }

    @Test
    @Transactional
    void getAllObservationsBySodiumIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where sodium in DEFAULT_SODIUM or UPDATED_SODIUM
        defaultObservationShouldBeFound("sodium.in=" + DEFAULT_SODIUM + "," + UPDATED_SODIUM);

        // Get all the observationList where sodium equals to UPDATED_SODIUM
        defaultObservationShouldNotBeFound("sodium.in=" + UPDATED_SODIUM);
    }

    @Test
    @Transactional
    void getAllObservationsBySodiumIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where sodium is not null
        defaultObservationShouldBeFound("sodium.specified=true");

        // Get all the observationList where sodium is null
        defaultObservationShouldNotBeFound("sodium.specified=false");
    }

    @Test
    @Transactional
    void getAllObservationsBySodiumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where sodium is greater than or equal to DEFAULT_SODIUM
        defaultObservationShouldBeFound("sodium.greaterThanOrEqual=" + DEFAULT_SODIUM);

        // Get all the observationList where sodium is greater than or equal to UPDATED_SODIUM
        defaultObservationShouldNotBeFound("sodium.greaterThanOrEqual=" + UPDATED_SODIUM);
    }

    @Test
    @Transactional
    void getAllObservationsBySodiumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where sodium is less than or equal to DEFAULT_SODIUM
        defaultObservationShouldBeFound("sodium.lessThanOrEqual=" + DEFAULT_SODIUM);

        // Get all the observationList where sodium is less than or equal to SMALLER_SODIUM
        defaultObservationShouldNotBeFound("sodium.lessThanOrEqual=" + SMALLER_SODIUM);
    }

    @Test
    @Transactional
    void getAllObservationsBySodiumIsLessThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where sodium is less than DEFAULT_SODIUM
        defaultObservationShouldNotBeFound("sodium.lessThan=" + DEFAULT_SODIUM);

        // Get all the observationList where sodium is less than UPDATED_SODIUM
        defaultObservationShouldBeFound("sodium.lessThan=" + UPDATED_SODIUM);
    }

    @Test
    @Transactional
    void getAllObservationsBySodiumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where sodium is greater than DEFAULT_SODIUM
        defaultObservationShouldNotBeFound("sodium.greaterThan=" + DEFAULT_SODIUM);

        // Get all the observationList where sodium is greater than SMALLER_SODIUM
        defaultObservationShouldBeFound("sodium.greaterThan=" + SMALLER_SODIUM);
    }

    @Test
    @Transactional
    void getAllObservationsByPotassiumIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where potassium equals to DEFAULT_POTASSIUM
        defaultObservationShouldBeFound("potassium.equals=" + DEFAULT_POTASSIUM);

        // Get all the observationList where potassium equals to UPDATED_POTASSIUM
        defaultObservationShouldNotBeFound("potassium.equals=" + UPDATED_POTASSIUM);
    }

    @Test
    @Transactional
    void getAllObservationsByPotassiumIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where potassium in DEFAULT_POTASSIUM or UPDATED_POTASSIUM
        defaultObservationShouldBeFound("potassium.in=" + DEFAULT_POTASSIUM + "," + UPDATED_POTASSIUM);

        // Get all the observationList where potassium equals to UPDATED_POTASSIUM
        defaultObservationShouldNotBeFound("potassium.in=" + UPDATED_POTASSIUM);
    }

    @Test
    @Transactional
    void getAllObservationsByPotassiumIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where potassium is not null
        defaultObservationShouldBeFound("potassium.specified=true");

        // Get all the observationList where potassium is null
        defaultObservationShouldNotBeFound("potassium.specified=false");
    }

    @Test
    @Transactional
    void getAllObservationsByPotassiumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where potassium is greater than or equal to DEFAULT_POTASSIUM
        defaultObservationShouldBeFound("potassium.greaterThanOrEqual=" + DEFAULT_POTASSIUM);

        // Get all the observationList where potassium is greater than or equal to UPDATED_POTASSIUM
        defaultObservationShouldNotBeFound("potassium.greaterThanOrEqual=" + UPDATED_POTASSIUM);
    }

    @Test
    @Transactional
    void getAllObservationsByPotassiumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where potassium is less than or equal to DEFAULT_POTASSIUM
        defaultObservationShouldBeFound("potassium.lessThanOrEqual=" + DEFAULT_POTASSIUM);

        // Get all the observationList where potassium is less than or equal to SMALLER_POTASSIUM
        defaultObservationShouldNotBeFound("potassium.lessThanOrEqual=" + SMALLER_POTASSIUM);
    }

    @Test
    @Transactional
    void getAllObservationsByPotassiumIsLessThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where potassium is less than DEFAULT_POTASSIUM
        defaultObservationShouldNotBeFound("potassium.lessThan=" + DEFAULT_POTASSIUM);

        // Get all the observationList where potassium is less than UPDATED_POTASSIUM
        defaultObservationShouldBeFound("potassium.lessThan=" + UPDATED_POTASSIUM);
    }

    @Test
    @Transactional
    void getAllObservationsByPotassiumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where potassium is greater than DEFAULT_POTASSIUM
        defaultObservationShouldNotBeFound("potassium.greaterThan=" + DEFAULT_POTASSIUM);

        // Get all the observationList where potassium is greater than SMALLER_POTASSIUM
        defaultObservationShouldBeFound("potassium.greaterThan=" + SMALLER_POTASSIUM);
    }

    @Test
    @Transactional
    void getAllObservationsByChlorideIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where chloride equals to DEFAULT_CHLORIDE
        defaultObservationShouldBeFound("chloride.equals=" + DEFAULT_CHLORIDE);

        // Get all the observationList where chloride equals to UPDATED_CHLORIDE
        defaultObservationShouldNotBeFound("chloride.equals=" + UPDATED_CHLORIDE);
    }

    @Test
    @Transactional
    void getAllObservationsByChlorideIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where chloride in DEFAULT_CHLORIDE or UPDATED_CHLORIDE
        defaultObservationShouldBeFound("chloride.in=" + DEFAULT_CHLORIDE + "," + UPDATED_CHLORIDE);

        // Get all the observationList where chloride equals to UPDATED_CHLORIDE
        defaultObservationShouldNotBeFound("chloride.in=" + UPDATED_CHLORIDE);
    }

    @Test
    @Transactional
    void getAllObservationsByChlorideIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where chloride is not null
        defaultObservationShouldBeFound("chloride.specified=true");

        // Get all the observationList where chloride is null
        defaultObservationShouldNotBeFound("chloride.specified=false");
    }

    @Test
    @Transactional
    void getAllObservationsByChlorideIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where chloride is greater than or equal to DEFAULT_CHLORIDE
        defaultObservationShouldBeFound("chloride.greaterThanOrEqual=" + DEFAULT_CHLORIDE);

        // Get all the observationList where chloride is greater than or equal to UPDATED_CHLORIDE
        defaultObservationShouldNotBeFound("chloride.greaterThanOrEqual=" + UPDATED_CHLORIDE);
    }

    @Test
    @Transactional
    void getAllObservationsByChlorideIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where chloride is less than or equal to DEFAULT_CHLORIDE
        defaultObservationShouldBeFound("chloride.lessThanOrEqual=" + DEFAULT_CHLORIDE);

        // Get all the observationList where chloride is less than or equal to SMALLER_CHLORIDE
        defaultObservationShouldNotBeFound("chloride.lessThanOrEqual=" + SMALLER_CHLORIDE);
    }

    @Test
    @Transactional
    void getAllObservationsByChlorideIsLessThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where chloride is less than DEFAULT_CHLORIDE
        defaultObservationShouldNotBeFound("chloride.lessThan=" + DEFAULT_CHLORIDE);

        // Get all the observationList where chloride is less than UPDATED_CHLORIDE
        defaultObservationShouldBeFound("chloride.lessThan=" + UPDATED_CHLORIDE);
    }

    @Test
    @Transactional
    void getAllObservationsByChlorideIsGreaterThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where chloride is greater than DEFAULT_CHLORIDE
        defaultObservationShouldNotBeFound("chloride.greaterThan=" + DEFAULT_CHLORIDE);

        // Get all the observationList where chloride is greater than SMALLER_CHLORIDE
        defaultObservationShouldBeFound("chloride.greaterThan=" + SMALLER_CHLORIDE);
    }

    @Test
    @Transactional
    void getAllObservationsByCarbonDioxideIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where carbonDioxide equals to DEFAULT_CARBON_DIOXIDE
        defaultObservationShouldBeFound("carbonDioxide.equals=" + DEFAULT_CARBON_DIOXIDE);

        // Get all the observationList where carbonDioxide equals to UPDATED_CARBON_DIOXIDE
        defaultObservationShouldNotBeFound("carbonDioxide.equals=" + UPDATED_CARBON_DIOXIDE);
    }

    @Test
    @Transactional
    void getAllObservationsByCarbonDioxideIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where carbonDioxide in DEFAULT_CARBON_DIOXIDE or UPDATED_CARBON_DIOXIDE
        defaultObservationShouldBeFound("carbonDioxide.in=" + DEFAULT_CARBON_DIOXIDE + "," + UPDATED_CARBON_DIOXIDE);

        // Get all the observationList where carbonDioxide equals to UPDATED_CARBON_DIOXIDE
        defaultObservationShouldNotBeFound("carbonDioxide.in=" + UPDATED_CARBON_DIOXIDE);
    }

    @Test
    @Transactional
    void getAllObservationsByCarbonDioxideIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where carbonDioxide is not null
        defaultObservationShouldBeFound("carbonDioxide.specified=true");

        // Get all the observationList where carbonDioxide is null
        defaultObservationShouldNotBeFound("carbonDioxide.specified=false");
    }

    @Test
    @Transactional
    void getAllObservationsByCarbonDioxideIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where carbonDioxide is greater than or equal to DEFAULT_CARBON_DIOXIDE
        defaultObservationShouldBeFound("carbonDioxide.greaterThanOrEqual=" + DEFAULT_CARBON_DIOXIDE);

        // Get all the observationList where carbonDioxide is greater than or equal to UPDATED_CARBON_DIOXIDE
        defaultObservationShouldNotBeFound("carbonDioxide.greaterThanOrEqual=" + UPDATED_CARBON_DIOXIDE);
    }

    @Test
    @Transactional
    void getAllObservationsByCarbonDioxideIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where carbonDioxide is less than or equal to DEFAULT_CARBON_DIOXIDE
        defaultObservationShouldBeFound("carbonDioxide.lessThanOrEqual=" + DEFAULT_CARBON_DIOXIDE);

        // Get all the observationList where carbonDioxide is less than or equal to SMALLER_CARBON_DIOXIDE
        defaultObservationShouldNotBeFound("carbonDioxide.lessThanOrEqual=" + SMALLER_CARBON_DIOXIDE);
    }

    @Test
    @Transactional
    void getAllObservationsByCarbonDioxideIsLessThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where carbonDioxide is less than DEFAULT_CARBON_DIOXIDE
        defaultObservationShouldNotBeFound("carbonDioxide.lessThan=" + DEFAULT_CARBON_DIOXIDE);

        // Get all the observationList where carbonDioxide is less than UPDATED_CARBON_DIOXIDE
        defaultObservationShouldBeFound("carbonDioxide.lessThan=" + UPDATED_CARBON_DIOXIDE);
    }

    @Test
    @Transactional
    void getAllObservationsByCarbonDioxideIsGreaterThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where carbonDioxide is greater than DEFAULT_CARBON_DIOXIDE
        defaultObservationShouldNotBeFound("carbonDioxide.greaterThan=" + DEFAULT_CARBON_DIOXIDE);

        // Get all the observationList where carbonDioxide is greater than SMALLER_CARBON_DIOXIDE
        defaultObservationShouldBeFound("carbonDioxide.greaterThan=" + SMALLER_CARBON_DIOXIDE);
    }

    @Test
    @Transactional
    void getAllObservationsByGlucoseIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where glucose equals to DEFAULT_GLUCOSE
        defaultObservationShouldBeFound("glucose.equals=" + DEFAULT_GLUCOSE);

        // Get all the observationList where glucose equals to UPDATED_GLUCOSE
        defaultObservationShouldNotBeFound("glucose.equals=" + UPDATED_GLUCOSE);
    }

    @Test
    @Transactional
    void getAllObservationsByGlucoseIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where glucose in DEFAULT_GLUCOSE or UPDATED_GLUCOSE
        defaultObservationShouldBeFound("glucose.in=" + DEFAULT_GLUCOSE + "," + UPDATED_GLUCOSE);

        // Get all the observationList where glucose equals to UPDATED_GLUCOSE
        defaultObservationShouldNotBeFound("glucose.in=" + UPDATED_GLUCOSE);
    }

    @Test
    @Transactional
    void getAllObservationsByGlucoseIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where glucose is not null
        defaultObservationShouldBeFound("glucose.specified=true");

        // Get all the observationList where glucose is null
        defaultObservationShouldNotBeFound("glucose.specified=false");
    }

    @Test
    @Transactional
    void getAllObservationsByGlucoseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where glucose is greater than or equal to DEFAULT_GLUCOSE
        defaultObservationShouldBeFound("glucose.greaterThanOrEqual=" + DEFAULT_GLUCOSE);

        // Get all the observationList where glucose is greater than or equal to UPDATED_GLUCOSE
        defaultObservationShouldNotBeFound("glucose.greaterThanOrEqual=" + UPDATED_GLUCOSE);
    }

    @Test
    @Transactional
    void getAllObservationsByGlucoseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where glucose is less than or equal to DEFAULT_GLUCOSE
        defaultObservationShouldBeFound("glucose.lessThanOrEqual=" + DEFAULT_GLUCOSE);

        // Get all the observationList where glucose is less than or equal to SMALLER_GLUCOSE
        defaultObservationShouldNotBeFound("glucose.lessThanOrEqual=" + SMALLER_GLUCOSE);
    }

    @Test
    @Transactional
    void getAllObservationsByGlucoseIsLessThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where glucose is less than DEFAULT_GLUCOSE
        defaultObservationShouldNotBeFound("glucose.lessThan=" + DEFAULT_GLUCOSE);

        // Get all the observationList where glucose is less than UPDATED_GLUCOSE
        defaultObservationShouldBeFound("glucose.lessThan=" + UPDATED_GLUCOSE);
    }

    @Test
    @Transactional
    void getAllObservationsByGlucoseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where glucose is greater than DEFAULT_GLUCOSE
        defaultObservationShouldNotBeFound("glucose.greaterThan=" + DEFAULT_GLUCOSE);

        // Get all the observationList where glucose is greater than SMALLER_GLUCOSE
        defaultObservationShouldBeFound("glucose.greaterThan=" + SMALLER_GLUCOSE);
    }

    @Test
    @Transactional
    void getAllObservationsByUreaNitrogenIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where ureaNitrogen equals to DEFAULT_UREA_NITROGEN
        defaultObservationShouldBeFound("ureaNitrogen.equals=" + DEFAULT_UREA_NITROGEN);

        // Get all the observationList where ureaNitrogen equals to UPDATED_UREA_NITROGEN
        defaultObservationShouldNotBeFound("ureaNitrogen.equals=" + UPDATED_UREA_NITROGEN);
    }

    @Test
    @Transactional
    void getAllObservationsByUreaNitrogenIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where ureaNitrogen in DEFAULT_UREA_NITROGEN or UPDATED_UREA_NITROGEN
        defaultObservationShouldBeFound("ureaNitrogen.in=" + DEFAULT_UREA_NITROGEN + "," + UPDATED_UREA_NITROGEN);

        // Get all the observationList where ureaNitrogen equals to UPDATED_UREA_NITROGEN
        defaultObservationShouldNotBeFound("ureaNitrogen.in=" + UPDATED_UREA_NITROGEN);
    }

    @Test
    @Transactional
    void getAllObservationsByUreaNitrogenIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where ureaNitrogen is not null
        defaultObservationShouldBeFound("ureaNitrogen.specified=true");

        // Get all the observationList where ureaNitrogen is null
        defaultObservationShouldNotBeFound("ureaNitrogen.specified=false");
    }

    @Test
    @Transactional
    void getAllObservationsByUreaNitrogenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where ureaNitrogen is greater than or equal to DEFAULT_UREA_NITROGEN
        defaultObservationShouldBeFound("ureaNitrogen.greaterThanOrEqual=" + DEFAULT_UREA_NITROGEN);

        // Get all the observationList where ureaNitrogen is greater than or equal to UPDATED_UREA_NITROGEN
        defaultObservationShouldNotBeFound("ureaNitrogen.greaterThanOrEqual=" + UPDATED_UREA_NITROGEN);
    }

    @Test
    @Transactional
    void getAllObservationsByUreaNitrogenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where ureaNitrogen is less than or equal to DEFAULT_UREA_NITROGEN
        defaultObservationShouldBeFound("ureaNitrogen.lessThanOrEqual=" + DEFAULT_UREA_NITROGEN);

        // Get all the observationList where ureaNitrogen is less than or equal to SMALLER_UREA_NITROGEN
        defaultObservationShouldNotBeFound("ureaNitrogen.lessThanOrEqual=" + SMALLER_UREA_NITROGEN);
    }

    @Test
    @Transactional
    void getAllObservationsByUreaNitrogenIsLessThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where ureaNitrogen is less than DEFAULT_UREA_NITROGEN
        defaultObservationShouldNotBeFound("ureaNitrogen.lessThan=" + DEFAULT_UREA_NITROGEN);

        // Get all the observationList where ureaNitrogen is less than UPDATED_UREA_NITROGEN
        defaultObservationShouldBeFound("ureaNitrogen.lessThan=" + UPDATED_UREA_NITROGEN);
    }

    @Test
    @Transactional
    void getAllObservationsByUreaNitrogenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where ureaNitrogen is greater than DEFAULT_UREA_NITROGEN
        defaultObservationShouldNotBeFound("ureaNitrogen.greaterThan=" + DEFAULT_UREA_NITROGEN);

        // Get all the observationList where ureaNitrogen is greater than SMALLER_UREA_NITROGEN
        defaultObservationShouldBeFound("ureaNitrogen.greaterThan=" + SMALLER_UREA_NITROGEN);
    }

    @Test
    @Transactional
    void getAllObservationsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where date equals to DEFAULT_DATE
        defaultObservationShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the observationList where date equals to UPDATED_DATE
        defaultObservationShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllObservationsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where date in DEFAULT_DATE or UPDATED_DATE
        defaultObservationShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the observationList where date equals to UPDATED_DATE
        defaultObservationShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllObservationsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where date is not null
        defaultObservationShouldBeFound("date.specified=true");

        // Get all the observationList where date is null
        defaultObservationShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllObservationsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where date is greater than or equal to DEFAULT_DATE
        defaultObservationShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the observationList where date is greater than or equal to UPDATED_DATE
        defaultObservationShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllObservationsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where date is less than or equal to DEFAULT_DATE
        defaultObservationShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the observationList where date is less than or equal to SMALLER_DATE
        defaultObservationShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllObservationsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where date is less than DEFAULT_DATE
        defaultObservationShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the observationList where date is less than UPDATED_DATE
        defaultObservationShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllObservationsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where date is greater than DEFAULT_DATE
        defaultObservationShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the observationList where date is greater than SMALLER_DATE
        defaultObservationShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllObservationsByPatientUIDIsEqualToSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where patientUID equals to DEFAULT_PATIENT_UID
        defaultObservationShouldBeFound("patientUID.equals=" + DEFAULT_PATIENT_UID);

        // Get all the observationList where patientUID equals to UPDATED_PATIENT_UID
        defaultObservationShouldNotBeFound("patientUID.equals=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllObservationsByPatientUIDIsInShouldWork() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where patientUID in DEFAULT_PATIENT_UID or UPDATED_PATIENT_UID
        defaultObservationShouldBeFound("patientUID.in=" + DEFAULT_PATIENT_UID + "," + UPDATED_PATIENT_UID);

        // Get all the observationList where patientUID equals to UPDATED_PATIENT_UID
        defaultObservationShouldNotBeFound("patientUID.in=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllObservationsByPatientUIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where patientUID is not null
        defaultObservationShouldBeFound("patientUID.specified=true");

        // Get all the observationList where patientUID is null
        defaultObservationShouldNotBeFound("patientUID.specified=false");
    }

    @Test
    @Transactional
    void getAllObservationsByPatientUIDContainsSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where patientUID contains DEFAULT_PATIENT_UID
        defaultObservationShouldBeFound("patientUID.contains=" + DEFAULT_PATIENT_UID);

        // Get all the observationList where patientUID contains UPDATED_PATIENT_UID
        defaultObservationShouldNotBeFound("patientUID.contains=" + UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void getAllObservationsByPatientUIDNotContainsSomething() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList where patientUID does not contain DEFAULT_PATIENT_UID
        defaultObservationShouldNotBeFound("patientUID.doesNotContain=" + DEFAULT_PATIENT_UID);

        // Get all the observationList where patientUID does not contain UPDATED_PATIENT_UID
        defaultObservationShouldBeFound("patientUID.doesNotContain=" + UPDATED_PATIENT_UID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultObservationShouldBeFound(String filter) throws Exception {
        restObservationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(observation.getId().intValue())))
            .andExpect(jsonPath("$.[*].bodyHeight").value(hasItem(DEFAULT_BODY_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].bodyWeight").value(hasItem(DEFAULT_BODY_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].bodyMass").value(hasItem(DEFAULT_BODY_MASS.doubleValue())))
            .andExpect(jsonPath("$.[*].painseverity").value(hasItem(DEFAULT_PAINSEVERITY.doubleValue())))
            .andExpect(jsonPath("$.[*].bloodPressure").value(hasItem(DEFAULT_BLOOD_PRESSURE.doubleValue())))
            .andExpect(jsonPath("$.[*].tobaccosmokingstatusNHIS").value(hasItem(DEFAULT_TOBACCOSMOKINGSTATUS_NHIS.doubleValue())))
            .andExpect(jsonPath("$.[*].creatinine").value(hasItem(DEFAULT_CREATININE.doubleValue())))
            .andExpect(jsonPath("$.[*].calcium").value(hasItem(DEFAULT_CALCIUM.doubleValue())))
            .andExpect(jsonPath("$.[*].sodium").value(hasItem(DEFAULT_SODIUM.doubleValue())))
            .andExpect(jsonPath("$.[*].potassium").value(hasItem(DEFAULT_POTASSIUM.doubleValue())))
            .andExpect(jsonPath("$.[*].chloride").value(hasItem(DEFAULT_CHLORIDE.doubleValue())))
            .andExpect(jsonPath("$.[*].carbonDioxide").value(hasItem(DEFAULT_CARBON_DIOXIDE.doubleValue())))
            .andExpect(jsonPath("$.[*].glucose").value(hasItem(DEFAULT_GLUCOSE.doubleValue())))
            .andExpect(jsonPath("$.[*].ureaNitrogen").value(hasItem(DEFAULT_UREA_NITROGEN.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].patientUID").value(hasItem(DEFAULT_PATIENT_UID)));

        // Check, that the count call also returns 1
        restObservationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultObservationShouldNotBeFound(String filter) throws Exception {
        restObservationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restObservationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingObservation() throws Exception {
        // Get the observation
        restObservationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingObservation() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        int databaseSizeBeforeUpdate = observationRepository.findAll().size();

        // Update the observation
        Observation updatedObservation = observationRepository.findById(observation.getId()).get();
        // Disconnect from session so that the updates on updatedObservation are not directly saved in db
        em.detach(updatedObservation);
        updatedObservation
            .bodyHeight(UPDATED_BODY_HEIGHT)
            .bodyWeight(UPDATED_BODY_WEIGHT)
            .bodyMass(UPDATED_BODY_MASS)
            .painseverity(UPDATED_PAINSEVERITY)
            .bloodPressure(UPDATED_BLOOD_PRESSURE)
            .tobaccosmokingstatusNHIS(UPDATED_TOBACCOSMOKINGSTATUS_NHIS)
            .creatinine(UPDATED_CREATININE)
            .calcium(UPDATED_CALCIUM)
            .sodium(UPDATED_SODIUM)
            .potassium(UPDATED_POTASSIUM)
            .chloride(UPDATED_CHLORIDE)
            .carbonDioxide(UPDATED_CARBON_DIOXIDE)
            .glucose(UPDATED_GLUCOSE)
            .ureaNitrogen(UPDATED_UREA_NITROGEN)
            .date(UPDATED_DATE)
            .patientUID(UPDATED_PATIENT_UID);
        ObservationDTO observationDTO = observationMapper.toDto(updatedObservation);

        restObservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, observationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(observationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
        Observation testObservation = observationList.get(observationList.size() - 1);
        assertThat(testObservation.getBodyHeight()).isEqualTo(UPDATED_BODY_HEIGHT);
        assertThat(testObservation.getBodyWeight()).isEqualTo(UPDATED_BODY_WEIGHT);
        assertThat(testObservation.getBodyMass()).isEqualTo(UPDATED_BODY_MASS);
        assertThat(testObservation.getPainseverity()).isEqualTo(UPDATED_PAINSEVERITY);
        assertThat(testObservation.getBloodPressure()).isEqualTo(UPDATED_BLOOD_PRESSURE);
        assertThat(testObservation.getTobaccosmokingstatusNHIS()).isEqualTo(UPDATED_TOBACCOSMOKINGSTATUS_NHIS);
        assertThat(testObservation.getCreatinine()).isEqualTo(UPDATED_CREATININE);
        assertThat(testObservation.getCalcium()).isEqualTo(UPDATED_CALCIUM);
        assertThat(testObservation.getSodium()).isEqualTo(UPDATED_SODIUM);
        assertThat(testObservation.getPotassium()).isEqualTo(UPDATED_POTASSIUM);
        assertThat(testObservation.getChloride()).isEqualTo(UPDATED_CHLORIDE);
        assertThat(testObservation.getCarbonDioxide()).isEqualTo(UPDATED_CARBON_DIOXIDE);
        assertThat(testObservation.getGlucose()).isEqualTo(UPDATED_GLUCOSE);
        assertThat(testObservation.getUreaNitrogen()).isEqualTo(UPDATED_UREA_NITROGEN);
        assertThat(testObservation.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testObservation.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void putNonExistingObservation() throws Exception {
        int databaseSizeBeforeUpdate = observationRepository.findAll().size();
        observation.setId(count.incrementAndGet());

        // Create the Observation
        ObservationDTO observationDTO = observationMapper.toDto(observation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, observationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(observationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchObservation() throws Exception {
        int databaseSizeBeforeUpdate = observationRepository.findAll().size();
        observation.setId(count.incrementAndGet());

        // Create the Observation
        ObservationDTO observationDTO = observationMapper.toDto(observation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(observationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamObservation() throws Exception {
        int databaseSizeBeforeUpdate = observationRepository.findAll().size();
        observation.setId(count.incrementAndGet());

        // Create the Observation
        ObservationDTO observationDTO = observationMapper.toDto(observation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObservationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(observationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateObservationWithPatch() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        int databaseSizeBeforeUpdate = observationRepository.findAll().size();

        // Update the observation using partial update
        Observation partialUpdatedObservation = new Observation();
        partialUpdatedObservation.setId(observation.getId());

        partialUpdatedObservation
            .bodyWeight(UPDATED_BODY_WEIGHT)
            .bodyMass(UPDATED_BODY_MASS)
            .painseverity(UPDATED_PAINSEVERITY)
            .bloodPressure(UPDATED_BLOOD_PRESSURE)
            .calcium(UPDATED_CALCIUM);

        restObservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObservation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObservation))
            )
            .andExpect(status().isOk());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
        Observation testObservation = observationList.get(observationList.size() - 1);
        assertThat(testObservation.getBodyHeight()).isEqualTo(DEFAULT_BODY_HEIGHT);
        assertThat(testObservation.getBodyWeight()).isEqualTo(UPDATED_BODY_WEIGHT);
        assertThat(testObservation.getBodyMass()).isEqualTo(UPDATED_BODY_MASS);
        assertThat(testObservation.getPainseverity()).isEqualTo(UPDATED_PAINSEVERITY);
        assertThat(testObservation.getBloodPressure()).isEqualTo(UPDATED_BLOOD_PRESSURE);
        assertThat(testObservation.getTobaccosmokingstatusNHIS()).isEqualTo(DEFAULT_TOBACCOSMOKINGSTATUS_NHIS);
        assertThat(testObservation.getCreatinine()).isEqualTo(DEFAULT_CREATININE);
        assertThat(testObservation.getCalcium()).isEqualTo(UPDATED_CALCIUM);
        assertThat(testObservation.getSodium()).isEqualTo(DEFAULT_SODIUM);
        assertThat(testObservation.getPotassium()).isEqualTo(DEFAULT_POTASSIUM);
        assertThat(testObservation.getChloride()).isEqualTo(DEFAULT_CHLORIDE);
        assertThat(testObservation.getCarbonDioxide()).isEqualTo(DEFAULT_CARBON_DIOXIDE);
        assertThat(testObservation.getGlucose()).isEqualTo(DEFAULT_GLUCOSE);
        assertThat(testObservation.getUreaNitrogen()).isEqualTo(DEFAULT_UREA_NITROGEN);
        assertThat(testObservation.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testObservation.getPatientUID()).isEqualTo(DEFAULT_PATIENT_UID);
    }

    @Test
    @Transactional
    void fullUpdateObservationWithPatch() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        int databaseSizeBeforeUpdate = observationRepository.findAll().size();

        // Update the observation using partial update
        Observation partialUpdatedObservation = new Observation();
        partialUpdatedObservation.setId(observation.getId());

        partialUpdatedObservation
            .bodyHeight(UPDATED_BODY_HEIGHT)
            .bodyWeight(UPDATED_BODY_WEIGHT)
            .bodyMass(UPDATED_BODY_MASS)
            .painseverity(UPDATED_PAINSEVERITY)
            .bloodPressure(UPDATED_BLOOD_PRESSURE)
            .tobaccosmokingstatusNHIS(UPDATED_TOBACCOSMOKINGSTATUS_NHIS)
            .creatinine(UPDATED_CREATININE)
            .calcium(UPDATED_CALCIUM)
            .sodium(UPDATED_SODIUM)
            .potassium(UPDATED_POTASSIUM)
            .chloride(UPDATED_CHLORIDE)
            .carbonDioxide(UPDATED_CARBON_DIOXIDE)
            .glucose(UPDATED_GLUCOSE)
            .ureaNitrogen(UPDATED_UREA_NITROGEN)
            .date(UPDATED_DATE)
            .patientUID(UPDATED_PATIENT_UID);

        restObservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObservation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObservation))
            )
            .andExpect(status().isOk());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
        Observation testObservation = observationList.get(observationList.size() - 1);
        assertThat(testObservation.getBodyHeight()).isEqualTo(UPDATED_BODY_HEIGHT);
        assertThat(testObservation.getBodyWeight()).isEqualTo(UPDATED_BODY_WEIGHT);
        assertThat(testObservation.getBodyMass()).isEqualTo(UPDATED_BODY_MASS);
        assertThat(testObservation.getPainseverity()).isEqualTo(UPDATED_PAINSEVERITY);
        assertThat(testObservation.getBloodPressure()).isEqualTo(UPDATED_BLOOD_PRESSURE);
        assertThat(testObservation.getTobaccosmokingstatusNHIS()).isEqualTo(UPDATED_TOBACCOSMOKINGSTATUS_NHIS);
        assertThat(testObservation.getCreatinine()).isEqualTo(UPDATED_CREATININE);
        assertThat(testObservation.getCalcium()).isEqualTo(UPDATED_CALCIUM);
        assertThat(testObservation.getSodium()).isEqualTo(UPDATED_SODIUM);
        assertThat(testObservation.getPotassium()).isEqualTo(UPDATED_POTASSIUM);
        assertThat(testObservation.getChloride()).isEqualTo(UPDATED_CHLORIDE);
        assertThat(testObservation.getCarbonDioxide()).isEqualTo(UPDATED_CARBON_DIOXIDE);
        assertThat(testObservation.getGlucose()).isEqualTo(UPDATED_GLUCOSE);
        assertThat(testObservation.getUreaNitrogen()).isEqualTo(UPDATED_UREA_NITROGEN);
        assertThat(testObservation.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testObservation.getPatientUID()).isEqualTo(UPDATED_PATIENT_UID);
    }

    @Test
    @Transactional
    void patchNonExistingObservation() throws Exception {
        int databaseSizeBeforeUpdate = observationRepository.findAll().size();
        observation.setId(count.incrementAndGet());

        // Create the Observation
        ObservationDTO observationDTO = observationMapper.toDto(observation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, observationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(observationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchObservation() throws Exception {
        int databaseSizeBeforeUpdate = observationRepository.findAll().size();
        observation.setId(count.incrementAndGet());

        // Create the Observation
        ObservationDTO observationDTO = observationMapper.toDto(observation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(observationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamObservation() throws Exception {
        int databaseSizeBeforeUpdate = observationRepository.findAll().size();
        observation.setId(count.incrementAndGet());

        // Create the Observation
        ObservationDTO observationDTO = observationMapper.toDto(observation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObservationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(observationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteObservation() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        int databaseSizeBeforeDelete = observationRepository.findAll().size();

        // Delete the observation
        restObservationMockMvc
            .perform(delete(ENTITY_API_URL_ID, observation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
