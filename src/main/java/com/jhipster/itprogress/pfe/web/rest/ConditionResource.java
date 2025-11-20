package com.jhipster.itprogress.pfe.web.rest;

import com.jhipster.itprogress.pfe.repository.ConditionRepository;
import com.jhipster.itprogress.pfe.service.ConditionQueryService;
import com.jhipster.itprogress.pfe.service.ConditionService;
import com.jhipster.itprogress.pfe.service.criteria.ConditionCriteria;
import com.jhipster.itprogress.pfe.service.dto.ConditionDTO;
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
 * REST controller for managing {@link com.jhipster.itprogress.pfe.domain.Condition}.
 */
@RestController
@RequestMapping("/api")
public class ConditionResource {

    private final Logger log = LoggerFactory.getLogger(ConditionResource.class);

    private static final String ENTITY_NAME = "condition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConditionService conditionService;

    private final ConditionRepository conditionRepository;

    private final ConditionQueryService conditionQueryService;

    public ConditionResource(
        ConditionService conditionService,
        ConditionRepository conditionRepository,
        ConditionQueryService conditionQueryService
    ) {
        this.conditionService = conditionService;
        this.conditionRepository = conditionRepository;
        this.conditionQueryService = conditionQueryService;
    }

    /**
     * {@code POST  /conditions} : Create a new condition.
     *
     * @param conditionDTO the conditionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conditionDTO, or with status {@code 400 (Bad Request)} if the condition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conditions")
    public ResponseEntity<ConditionDTO> createCondition(@RequestBody ConditionDTO conditionDTO) throws URISyntaxException {
        log.debug("REST request to save Condition : {}", conditionDTO);
        if (conditionDTO.getId() != null) {
            throw new BadRequestAlertException("A new condition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConditionDTO result = conditionService.save(conditionDTO);
        return ResponseEntity
            .created(new URI("/api/conditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conditions/:id} : Updates an existing condition.
     *
     * @param id the id of the conditionDTO to save.
     * @param conditionDTO the conditionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conditionDTO,
     * or with status {@code 400 (Bad Request)} if the conditionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conditionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conditions/{id}")
    public ResponseEntity<ConditionDTO> updateCondition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConditionDTO conditionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Condition : {}, {}", id, conditionDTO);
        if (conditionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conditionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conditionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConditionDTO result = conditionService.update(conditionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conditionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /conditions/:id} : Partial updates given fields of an existing condition, field will ignore if it is null
     *
     * @param id the id of the conditionDTO to save.
     * @param conditionDTO the conditionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conditionDTO,
     * or with status {@code 400 (Bad Request)} if the conditionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the conditionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the conditionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/conditions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConditionDTO> partialUpdateCondition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConditionDTO conditionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Condition partially : {}, {}", id, conditionDTO);
        if (conditionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conditionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conditionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConditionDTO> result = conditionService.partialUpdate(conditionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conditionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /conditions} : get all the conditions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conditions in body.
     */
    @GetMapping("/conditions")
    public ResponseEntity<List<ConditionDTO>> getAllConditions(
        ConditionCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Conditions by criteria: {}", criteria);
        Page<ConditionDTO> page = conditionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /conditions/count} : count all the conditions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/conditions/count")
    public ResponseEntity<Long> countConditions(ConditionCriteria criteria) {
        log.debug("REST request to count Conditions by criteria: {}", criteria);
        return ResponseEntity.ok().body(conditionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /conditions/:id} : get the "id" condition.
     *
     * @param id the id of the conditionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conditionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conditions/{id}")
    public ResponseEntity<ConditionDTO> getCondition(@PathVariable Long id) {
        log.debug("REST request to get Condition : {}", id);
        Optional<ConditionDTO> conditionDTO = conditionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conditionDTO);
    }

    /**
     * {@code DELETE  /conditions/:id} : delete the "id" condition.
     *
     * @param id the id of the conditionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conditions/{id}")
    public ResponseEntity<Void> deleteCondition(@PathVariable Long id) {
        log.debug("REST request to delete Condition : {}", id);
        conditionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
