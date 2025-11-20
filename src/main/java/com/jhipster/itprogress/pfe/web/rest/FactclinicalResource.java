package com.jhipster.itprogress.pfe.web.rest;

import com.jhipster.itprogress.pfe.repository.FactclinicalRepository;
import com.jhipster.itprogress.pfe.service.FactclinicalQueryService;
import com.jhipster.itprogress.pfe.service.FactclinicalService;
import com.jhipster.itprogress.pfe.service.criteria.FactclinicalCriteria;
import com.jhipster.itprogress.pfe.service.dto.FactclinicalDTO;
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
 * REST controller for managing {@link com.jhipster.itprogress.pfe.domain.Factclinical}.
 */
@RestController
@RequestMapping("/api")
public class FactclinicalResource {

    private final Logger log = LoggerFactory.getLogger(FactclinicalResource.class);

    private static final String ENTITY_NAME = "factclinical";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FactclinicalService factclinicalService;

    private final FactclinicalRepository factclinicalRepository;

    private final FactclinicalQueryService factclinicalQueryService;

    public FactclinicalResource(
        FactclinicalService factclinicalService,
        FactclinicalRepository factclinicalRepository,
        FactclinicalQueryService factclinicalQueryService
    ) {
        this.factclinicalService = factclinicalService;
        this.factclinicalRepository = factclinicalRepository;
        this.factclinicalQueryService = factclinicalQueryService;
    }

    /**
     * {@code POST  /factclinicals} : Create a new factclinical.
     *
     * @param factclinicalDTO the factclinicalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new factclinicalDTO, or with status {@code 400 (Bad Request)} if the factclinical has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/factclinicals")
    public ResponseEntity<FactclinicalDTO> createFactclinical(@RequestBody FactclinicalDTO factclinicalDTO) throws URISyntaxException {
        log.debug("REST request to save Factclinical : {}", factclinicalDTO);
        if (factclinicalDTO.getId() != null) {
            throw new BadRequestAlertException("A new factclinical cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FactclinicalDTO result = factclinicalService.save(factclinicalDTO);
        return ResponseEntity
            .created(new URI("/api/factclinicals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /factclinicals/:id} : Updates an existing factclinical.
     *
     * @param id the id of the factclinicalDTO to save.
     * @param factclinicalDTO the factclinicalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factclinicalDTO,
     * or with status {@code 400 (Bad Request)} if the factclinicalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the factclinicalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/factclinicals/{id}")
    public ResponseEntity<FactclinicalDTO> updateFactclinical(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FactclinicalDTO factclinicalDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Factclinical : {}, {}", id, factclinicalDTO);
        if (factclinicalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factclinicalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factclinicalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FactclinicalDTO result = factclinicalService.update(factclinicalDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, factclinicalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /factclinicals/:id} : Partial updates given fields of an existing factclinical, field will ignore if it is null
     *
     * @param id the id of the factclinicalDTO to save.
     * @param factclinicalDTO the factclinicalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factclinicalDTO,
     * or with status {@code 400 (Bad Request)} if the factclinicalDTO is not valid,
     * or with status {@code 404 (Not Found)} if the factclinicalDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the factclinicalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/factclinicals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FactclinicalDTO> partialUpdateFactclinical(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FactclinicalDTO factclinicalDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Factclinical partially : {}, {}", id, factclinicalDTO);
        if (factclinicalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, factclinicalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!factclinicalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FactclinicalDTO> result = factclinicalService.partialUpdate(factclinicalDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, factclinicalDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /factclinicals} : get all the factclinicals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of factclinicals in body.
     */
    @GetMapping("/factclinicals")
    public ResponseEntity<List<FactclinicalDTO>> getAllFactclinicals(
        FactclinicalCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Factclinicals by criteria: {}", criteria);
        Page<FactclinicalDTO> page = factclinicalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /factclinicals/count} : count all the factclinicals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/factclinicals/count")
    public ResponseEntity<Long> countFactclinicals(FactclinicalCriteria criteria) {
        log.debug("REST request to count Factclinicals by criteria: {}", criteria);
        return ResponseEntity.ok().body(factclinicalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /factclinicals/:id} : get the "id" factclinical.
     *
     * @param id the id of the factclinicalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the factclinicalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/factclinicals/{id}")
    public ResponseEntity<FactclinicalDTO> getFactclinical(@PathVariable Long id) {
        log.debug("REST request to get Factclinical : {}", id);
        Optional<FactclinicalDTO> factclinicalDTO = factclinicalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(factclinicalDTO);
    }

    /**
     * {@code DELETE  /factclinicals/:id} : delete the "id" factclinical.
     *
     * @param id the id of the factclinicalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/factclinicals/{id}")
    public ResponseEntity<Void> deleteFactclinical(@PathVariable Long id) {
        log.debug("REST request to delete Factclinical : {}", id);
        factclinicalService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
