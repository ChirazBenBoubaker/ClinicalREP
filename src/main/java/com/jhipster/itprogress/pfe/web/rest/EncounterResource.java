package com.jhipster.itprogress.pfe.web.rest;

import com.jhipster.itprogress.pfe.repository.EncounterRepository;
import com.jhipster.itprogress.pfe.service.EncounterQueryService;
import com.jhipster.itprogress.pfe.service.EncounterService;
import com.jhipster.itprogress.pfe.service.criteria.EncounterCriteria;
import com.jhipster.itprogress.pfe.service.dto.EncounterDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jhipster.itprogress.pfe.domain.Encounter}.
 */
@RestController
@RequestMapping("/api")
public class EncounterResource {

    private final Logger log = LoggerFactory.getLogger(EncounterResource.class);

    private static final String ENTITY_NAME = "encounter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EncounterService encounterService;

    private final EncounterRepository encounterRepository;

    private final EncounterQueryService encounterQueryService;

    public EncounterResource(
        EncounterService encounterService,
        EncounterRepository encounterRepository,
        EncounterQueryService encounterQueryService
    ) {
        this.encounterService = encounterService;
        this.encounterRepository = encounterRepository;
        this.encounterQueryService = encounterQueryService;
    }

    /**
     * {@code POST  /encounters} : Create a new encounter.
     *
     * @param encounterDTO the encounterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new encounterDTO, or with status {@code 400 (Bad Request)} if the encounter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/encounters")
    public ResponseEntity<EncounterDTO> createEncounter(@RequestBody EncounterDTO encounterDTO) throws URISyntaxException {
        log.debug("REST request to save Encounter : {}", encounterDTO);
        if (encounterDTO.getId() != null) {
            throw new BadRequestAlertException("A new encounter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EncounterDTO result = encounterService.save(encounterDTO);
        return ResponseEntity
            .created(new URI("/api/encounters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /encounters/:id} : Updates an existing encounter.
     *
     * @param id the id of the encounterDTO to save.
     * @param encounterDTO the encounterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated encounterDTO,
     * or with status {@code 400 (Bad Request)} if the encounterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the encounterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/encounters/{id}")
    public ResponseEntity<EncounterDTO> updateEncounter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EncounterDTO encounterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Encounter : {}, {}", id, encounterDTO);
        if (encounterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, encounterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!encounterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EncounterDTO result = encounterService.update(encounterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, encounterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /encounters/:id} : Partial updates given fields of an existing encounter, field will ignore if it is null
     *
     * @param id the id of the encounterDTO to save.
     * @param encounterDTO the encounterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated encounterDTO,
     * or with status {@code 400 (Bad Request)} if the encounterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the encounterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the encounterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/encounters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EncounterDTO> partialUpdateEncounter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EncounterDTO encounterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Encounter partially : {}, {}", id, encounterDTO);
        if (encounterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, encounterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!encounterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EncounterDTO> result = encounterService.partialUpdate(encounterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, encounterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /encounters} : get all the encounters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of encounters in body.
     */
    @GetMapping("/encounters")
    public ResponseEntity<List<EncounterDTO>> getAllEncounters(
        EncounterCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Encounters by criteria: {}", criteria);
        Page<EncounterDTO> page = encounterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /encounters/count} : count all the encounters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/encounters/count")
    public ResponseEntity<Long> countEncounters(EncounterCriteria criteria) {
        log.debug("REST request to count Encounters by criteria: {}", criteria);
        return ResponseEntity.ok().body(encounterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /encounters/:id} : get the "id" encounter.
     *
     * @param id the id of the encounterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the encounterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/encounters/{id}")
    public ResponseEntity<EncounterDTO> getEncounter(@PathVariable Long id) {
        log.debug("REST request to get Encounter : {}", id);
        Optional<EncounterDTO> encounterDTO = encounterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(encounterDTO);
    }

    /**
     * {@code DELETE  /encounters/:id} : delete the "id" encounter.
     *
     * @param id the id of the encounterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/encounters/{id}")
    public ResponseEntity<Void> deleteEncounter(@PathVariable Long id) {
        log.debug("REST request to delete Encounter : {}", id);
        encounterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/encounters/CountMonthYear")
    public ResponseEntity<Long> EncountersCountMonthYear(@RequestParam Integer month, @RequestParam Integer year) {
        //log.debug("REST request to get paid Percent Record : {}", date);
        return new ResponseEntity<Long>(encounterService.EncountersCountMonthYear(month, year), HttpStatus.OK);
    }
}
