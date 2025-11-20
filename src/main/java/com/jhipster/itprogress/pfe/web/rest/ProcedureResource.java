package com.jhipster.itprogress.pfe.web.rest;

import com.jhipster.itprogress.pfe.repository.ProcedureRepository;
import com.jhipster.itprogress.pfe.service.ProcedureQueryService;
import com.jhipster.itprogress.pfe.service.ProcedureService;
import com.jhipster.itprogress.pfe.service.criteria.ProcedureCriteria;
import com.jhipster.itprogress.pfe.service.dto.ProcedureDTO;
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
 * REST controller for managing {@link com.jhipster.itprogress.pfe.domain.Procedure}.
 */
@RestController
@RequestMapping("/api")
public class ProcedureResource {

    private final Logger log = LoggerFactory.getLogger(ProcedureResource.class);

    private static final String ENTITY_NAME = "procedure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcedureService procedureService;

    private final ProcedureRepository procedureRepository;

    private final ProcedureQueryService procedureQueryService;

    public ProcedureResource(
        ProcedureService procedureService,
        ProcedureRepository procedureRepository,
        ProcedureQueryService procedureQueryService
    ) {
        this.procedureService = procedureService;
        this.procedureRepository = procedureRepository;
        this.procedureQueryService = procedureQueryService;
    }

    /**
     * {@code POST  /procedures} : Create a new procedure.
     *
     * @param procedureDTO the procedureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new procedureDTO, or with status {@code 400 (Bad Request)} if the procedure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/procedures")
    public ResponseEntity<ProcedureDTO> createProcedure(@RequestBody ProcedureDTO procedureDTO) throws URISyntaxException {
        log.debug("REST request to save Procedure : {}", procedureDTO);
        if (procedureDTO.getId() != null) {
            throw new BadRequestAlertException("A new procedure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcedureDTO result = procedureService.save(procedureDTO);
        return ResponseEntity
            .created(new URI("/api/procedures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /procedures/:id} : Updates an existing procedure.
     *
     * @param id the id of the procedureDTO to save.
     * @param procedureDTO the procedureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procedureDTO,
     * or with status {@code 400 (Bad Request)} if the procedureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the procedureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/procedures/{id}")
    public ResponseEntity<ProcedureDTO> updateProcedure(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProcedureDTO procedureDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Procedure : {}, {}", id, procedureDTO);
        if (procedureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, procedureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!procedureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProcedureDTO result = procedureService.update(procedureDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, procedureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /procedures/:id} : Partial updates given fields of an existing procedure, field will ignore if it is null
     *
     * @param id the id of the procedureDTO to save.
     * @param procedureDTO the procedureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procedureDTO,
     * or with status {@code 400 (Bad Request)} if the procedureDTO is not valid,
     * or with status {@code 404 (Not Found)} if the procedureDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the procedureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/procedures/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProcedureDTO> partialUpdateProcedure(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProcedureDTO procedureDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Procedure partially : {}, {}", id, procedureDTO);
        if (procedureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, procedureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!procedureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProcedureDTO> result = procedureService.partialUpdate(procedureDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, procedureDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /procedures} : get all the procedures.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of procedures in body.
     */
    @GetMapping("/procedures")
    public ResponseEntity<List<ProcedureDTO>> getAllProcedures(
        ProcedureCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Procedures by criteria: {}", criteria);
        Page<ProcedureDTO> page = procedureQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /procedures/count} : count all the procedures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/procedures/count")
    public ResponseEntity<Long> countProcedures(ProcedureCriteria criteria) {
        log.debug("REST request to count Procedures by criteria: {}", criteria);
        return ResponseEntity.ok().body(procedureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /procedures/:id} : get the "id" procedure.
     *
     * @param id the id of the procedureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the procedureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/procedures/{id}")
    public ResponseEntity<ProcedureDTO> getProcedure(@PathVariable Long id) {
        log.debug("REST request to get Procedure : {}", id);
        Optional<ProcedureDTO> procedureDTO = procedureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(procedureDTO);
    }

    /**
     * {@code DELETE  /procedures/:id} : delete the "id" procedure.
     *
     * @param id the id of the procedureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/procedures/{id}")
    public ResponseEntity<Void> deleteProcedure(@PathVariable Long id) {
        log.debug("REST request to delete Procedure : {}", id);
        procedureService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
