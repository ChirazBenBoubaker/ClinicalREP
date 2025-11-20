package com.jhipster.itprogress.pfe.web.rest;

import com.jhipster.itprogress.pfe.repository.ObservationRepository;
import com.jhipster.itprogress.pfe.service.ObservationQueryService;
import com.jhipster.itprogress.pfe.service.ObservationService;
import com.jhipster.itprogress.pfe.service.criteria.ObservationCriteria;
import com.jhipster.itprogress.pfe.service.dto.ObservationDTO;
import com.jhipster.itprogress.pfe.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jhipster.itprogress.pfe.domain.Observation}.
 */
@RestController
@RequestMapping("/api")
public class ObservationResource {

    private final Logger log = LoggerFactory.getLogger(ObservationResource.class);

    private static final String ENTITY_NAME = "observation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObservationService observationService;

    private final ObservationRepository observationRepository;

    private final ObservationQueryService observationQueryService;

    public ObservationResource(
        ObservationService observationService,
        ObservationRepository observationRepository,
        ObservationQueryService observationQueryService
    ) {
        this.observationService = observationService;
        this.observationRepository = observationRepository;
        this.observationQueryService = observationQueryService;
    }

    /**
     * {@code POST  /observations} : Create a new observation.
     *
     * @param observationDTO the observationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new observationDTO, or with status {@code 400 (Bad Request)} if the observation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/observations")
    public ResponseEntity<ObservationDTO> createObservation(@RequestBody ObservationDTO observationDTO) throws URISyntaxException {
        log.debug("REST request to save Observation : {}", observationDTO);
        if (observationDTO.getId() != null) {
            throw new BadRequestAlertException("A new observation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObservationDTO result = observationService.save(observationDTO);
        return ResponseEntity
            .created(new URI("/api/observations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /observations/:id} : Updates an existing observation.
     *
     * @param id the id of the observationDTO to save.
     * @param observationDTO the observationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated observationDTO,
     * or with status {@code 400 (Bad Request)} if the observationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the observationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/observations/{id}")
    public ResponseEntity<ObservationDTO> updateObservation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ObservationDTO observationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Observation : {}, {}", id, observationDTO);
        if (observationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, observationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!observationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ObservationDTO result = observationService.update(observationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, observationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /observations/:id} : Partial updates given fields of an existing observation, field will ignore if it is null
     *
     * @param id the id of the observationDTO to save.
     * @param observationDTO the observationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated observationDTO,
     * or with status {@code 400 (Bad Request)} if the observationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the observationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the observationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/observations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ObservationDTO> partialUpdateObservation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ObservationDTO observationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Observation partially : {}, {}", id, observationDTO);
        if (observationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, observationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!observationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ObservationDTO> result = observationService.partialUpdate(observationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, observationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /observations} : get all the observations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of observations in body.
     */
    @GetMapping("/observations")
    public ResponseEntity<List<ObservationDTO>> getAllObservations(
        ObservationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Observations by criteria: {}", criteria);
        Page<ObservationDTO> page = observationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /observations/count} : count all the observations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/observations/count")
    public ResponseEntity<Long> countObservations(ObservationCriteria criteria) {
        log.debug("REST request to count Observations by criteria: {}", criteria);
        return ResponseEntity.ok().body(observationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /observations/:id} : get the "id" observation.
     *
     * @param id the id of the observationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the observationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/observations/{id}")
    public ResponseEntity<ObservationDTO> getObservation(@PathVariable Long id) {
        log.debug("REST request to get Observation : {}", id);
        Optional<ObservationDTO> observationDTO = observationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(observationDTO);
    }

    /**
     * {@code DELETE  /observations/:id} : delete the "id" observation.
     *
     * @param id the id of the observationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/observations/{id}")
    public ResponseEntity<Void> deleteObservation(@PathVariable Long id) {
        log.debug("REST request to delete Observation : {}", id);
        observationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
