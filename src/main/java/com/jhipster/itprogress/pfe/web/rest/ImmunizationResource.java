package com.jhipster.itprogress.pfe.web.rest;

import com.jhipster.itprogress.pfe.repository.ImmunizationRepository;
import com.jhipster.itprogress.pfe.service.ImmunizationQueryService;
import com.jhipster.itprogress.pfe.service.ImmunizationService;
import com.jhipster.itprogress.pfe.service.criteria.ImmunizationCriteria;
import com.jhipster.itprogress.pfe.service.dto.ImmunizationDTO;
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
 * REST controller for managing {@link com.jhipster.itprogress.pfe.domain.Immunization}.
 */
@RestController
@RequestMapping("/api")
public class ImmunizationResource {

    private final Logger log = LoggerFactory.getLogger(ImmunizationResource.class);

    private static final String ENTITY_NAME = "immunization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImmunizationService immunizationService;

    private final ImmunizationRepository immunizationRepository;

    private final ImmunizationQueryService immunizationQueryService;

    public ImmunizationResource(
        ImmunizationService immunizationService,
        ImmunizationRepository immunizationRepository,
        ImmunizationQueryService immunizationQueryService
    ) {
        this.immunizationService = immunizationService;
        this.immunizationRepository = immunizationRepository;
        this.immunizationQueryService = immunizationQueryService;
    }

    /**
     * {@code POST  /immunizations} : Create a new immunization.
     *
     * @param immunizationDTO the immunizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new immunizationDTO, or with status {@code 400 (Bad Request)} if the immunization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/immunizations")
    public ResponseEntity<ImmunizationDTO> createImmunization(@RequestBody ImmunizationDTO immunizationDTO) throws URISyntaxException {
        log.debug("REST request to save Immunization : {}", immunizationDTO);
        if (immunizationDTO.getId() != null) {
            throw new BadRequestAlertException("A new immunization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImmunizationDTO result = immunizationService.save(immunizationDTO);
        return ResponseEntity
            .created(new URI("/api/immunizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /immunizations/:id} : Updates an existing immunization.
     *
     * @param id the id of the immunizationDTO to save.
     * @param immunizationDTO the immunizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated immunizationDTO,
     * or with status {@code 400 (Bad Request)} if the immunizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the immunizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/immunizations/{id}")
    public ResponseEntity<ImmunizationDTO> updateImmunization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ImmunizationDTO immunizationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Immunization : {}, {}", id, immunizationDTO);
        if (immunizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, immunizationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!immunizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ImmunizationDTO result = immunizationService.update(immunizationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, immunizationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /immunizations/:id} : Partial updates given fields of an existing immunization, field will ignore if it is null
     *
     * @param id the id of the immunizationDTO to save.
     * @param immunizationDTO the immunizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated immunizationDTO,
     * or with status {@code 400 (Bad Request)} if the immunizationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the immunizationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the immunizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/immunizations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ImmunizationDTO> partialUpdateImmunization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ImmunizationDTO immunizationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Immunization partially : {}, {}", id, immunizationDTO);
        if (immunizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, immunizationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!immunizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ImmunizationDTO> result = immunizationService.partialUpdate(immunizationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, immunizationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /immunizations} : get all the immunizations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of immunizations in body.
     */
    @GetMapping("/immunizations")
    public ResponseEntity<List<ImmunizationDTO>> getAllImmunizations(
        ImmunizationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Immunizations by criteria: {}", criteria);
        Page<ImmunizationDTO> page = immunizationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /immunizations/count} : count all the immunizations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/immunizations/count")
    public ResponseEntity<Long> countImmunizations(ImmunizationCriteria criteria) {
        log.debug("REST request to count Immunizations by criteria: {}", criteria);
        return ResponseEntity.ok().body(immunizationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /immunizations/:id} : get the "id" immunization.
     *
     * @param id the id of the immunizationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the immunizationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/immunizations/{id}")
    public ResponseEntity<ImmunizationDTO> getImmunization(@PathVariable Long id) {
        log.debug("REST request to get Immunization : {}", id);
        Optional<ImmunizationDTO> immunizationDTO = immunizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(immunizationDTO);
    }

    /**
     * {@code DELETE  /immunizations/:id} : delete the "id" immunization.
     *
     * @param id the id of the immunizationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/immunizations/{id}")
    public ResponseEntity<Void> deleteImmunization(@PathVariable Long id) {
        log.debug("REST request to delete Immunization : {}", id);
        immunizationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    @GetMapping("/immunizations/CountMonthYear")
    public ResponseEntity<Long> ImmunizationsCountMonthYear(@RequestParam Integer month, @RequestParam Integer year) {
        return new ResponseEntity<Long>(immunizationService. ImmunizationsCountMonthYear(month, year), HttpStatus.OK);
    }
}
