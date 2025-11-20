package com.jhipster.itprogress.pfe.web.rest;

import com.jhipster.itprogress.pfe.repository.DateRepository;
import com.jhipster.itprogress.pfe.service.DateQueryService;
import com.jhipster.itprogress.pfe.service.DateService;
import com.jhipster.itprogress.pfe.service.criteria.DateCriteria;
import com.jhipster.itprogress.pfe.service.dto.DateDTO;
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
 * REST controller for managing {@link com.jhipster.itprogress.pfe.domain.Date}.
 */
@RestController
@RequestMapping("/api")
public class DateResource {

    private final Logger log = LoggerFactory.getLogger(DateResource.class);

    private static final String ENTITY_NAME = "date";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DateService dateService;

    private final DateRepository dateRepository;

    private final DateQueryService dateQueryService;

    public DateResource(DateService dateService, DateRepository dateRepository, DateQueryService dateQueryService) {
        this.dateService = dateService;
        this.dateRepository = dateRepository;
        this.dateQueryService = dateQueryService;
    }

    /**
     * {@code POST  /dates} : Create a new date.
     *
     * @param dateDTO the dateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dateDTO, or with status {@code 400 (Bad Request)} if the date has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dates")
    public ResponseEntity<DateDTO> createDate(@RequestBody DateDTO dateDTO) throws URISyntaxException {
        log.debug("REST request to save Date : {}", dateDTO);
        if (dateDTO.getId() != null) {
            throw new BadRequestAlertException("A new date cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DateDTO result = dateService.save(dateDTO);
        return ResponseEntity
            .created(new URI("/api/dates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dates/:id} : Updates an existing date.
     *
     * @param id the id of the dateDTO to save.
     * @param dateDTO the dateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dateDTO,
     * or with status {@code 400 (Bad Request)} if the dateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dates/{id}")
    public ResponseEntity<DateDTO> updateDate(@PathVariable(value = "id", required = false) final Long id, @RequestBody DateDTO dateDTO)
        throws URISyntaxException {
        log.debug("REST request to update Date : {}, {}", id, dateDTO);
        if (dateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DateDTO result = dateService.update(dateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dates/:id} : Partial updates given fields of an existing date, field will ignore if it is null
     *
     * @param id the id of the dateDTO to save.
     * @param dateDTO the dateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dateDTO,
     * or with status {@code 400 (Bad Request)} if the dateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DateDTO> partialUpdateDate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DateDTO dateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Date partially : {}, {}", id, dateDTO);
        if (dateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DateDTO> result = dateService.partialUpdate(dateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dates} : get all the dates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dates in body.
     */
    @GetMapping("/dates")
    public ResponseEntity<List<DateDTO>> getAllDates(
        DateCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Dates by criteria: {}", criteria);
        Page<DateDTO> page = dateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dates/count} : count all the dates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/dates/count")
    public ResponseEntity<Long> countDates(DateCriteria criteria) {
        log.debug("REST request to count Dates by criteria: {}", criteria);
        return ResponseEntity.ok().body(dateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dates/:id} : get the "id" date.
     *
     * @param id the id of the dateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dates/{id}")
    public ResponseEntity<DateDTO> getDate(@PathVariable Long id) {
        log.debug("REST request to get Date : {}", id);
        Optional<DateDTO> dateDTO = dateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dateDTO);
    }

    /**
     * {@code DELETE  /dates/:id} : delete the "id" date.
     *
     * @param id the id of the dateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dates/{id}")
    public ResponseEntity<Void> deleteDate(@PathVariable Long id) {
        log.debug("REST request to delete Date : {}", id);
        dateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
