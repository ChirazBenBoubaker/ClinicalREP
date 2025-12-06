package com.jhipster.itprogress.pfe.service;

import com.jhipster.itprogress.pfe.domain.*; // for static metamodels
import com.jhipster.itprogress.pfe.domain.Factclinical;
import com.jhipster.itprogress.pfe.repository.FactclinicalRepository;
import com.jhipster.itprogress.pfe.service.criteria.FactclinicalCriteria;
import com.jhipster.itprogress.pfe.service.dto.FactclinicalDTO;
import com.jhipster.itprogress.pfe.service.mapper.FactclinicalMapper;

import java.util.List;
import java.util.function.Function;
import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Factclinical} entities in the database.
 * The main input is a {@link FactclinicalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FactclinicalDTO} or a {@link Page} of {@link FactclinicalDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FactclinicalQueryService extends QueryService<Factclinical> {

    private final Logger log = LoggerFactory.getLogger(FactclinicalQueryService.class);

    private final FactclinicalRepository factclinicalRepository;
    private final FactclinicalMapper factclinicalMapper;

    public FactclinicalQueryService(FactclinicalRepository factclinicalRepository, FactclinicalMapper factclinicalMapper) {
        this.factclinicalRepository = factclinicalRepository;
        this.factclinicalMapper = factclinicalMapper;
    }

    @Transactional(readOnly = true)
    public List<FactclinicalDTO> findByCriteria(FactclinicalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Factclinical> specification = createSpecification(criteria);
        return factclinicalMapper.toDto(factclinicalRepository.findAll(specification));
    }

    @Transactional(readOnly = true)
    public Page<FactclinicalDTO> findByCriteria(FactclinicalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Factclinical> specification = createSpecification(criteria);
        return factclinicalRepository.findAll(specification, page).map(factclinicalMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(FactclinicalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Factclinical> specification = createSpecification(criteria);
        return factclinicalRepository.count(specification);
    }

    /**
     * Function to convert {@link FactclinicalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Factclinical> createSpecification(FactclinicalCriteria criteria) {
        Specification<Factclinical> specification = Specification.where(null);

        if (criteria == null) {
            return specification;
        }

        // distinct must be applied first
        if (criteria.getDistinct() != null) {
            specification = specification.and(distinct(criteria.getDistinct()));
        }

        // Add all other criteria only when present
        specification = specification
            .and(addIfPresent(criteria.getId(),              id -> buildRangeSpecification(id, Factclinical_.id)))
            .and(addIfPresent(criteria.getPatientUID(),      patientUID -> buildStringSpecification(patientUID, Factclinical_.patientUID)))
            .and(addIfPresent(criteria.getEncounterID(),     encounterID -> buildRangeSpecification(encounterID, Factclinical_.encounterID)))
            .and(addIfPresent(criteria.getObservationID(),   observationID -> buildRangeSpecification(observationID, Factclinical_.observationID)))
            .and(addIfPresent(criteria.getProcedureID(),     procedureID -> buildRangeSpecification(procedureID, Factclinical_.procedureID)))
            .and(addIfPresent(criteria.getImmunizationID(),  immunizationID -> buildRangeSpecification(immunizationID, Factclinical_.immunizationID)))
            .and(addIfPresent(criteria.getMedicationID(),    medicationID -> buildRangeSpecification(medicationID, Factclinical_.medicationID)))
            .and(addIfPresent(criteria.getConditionID(),     conditionID -> buildRangeSpecification(conditionID, Factclinical_.conditionID)))
            .and(addIfPresent(criteria.getDate(),            date -> buildRangeSpecification(date, Factclinical_.date)));

        return specification;
    }

    /**
     * Helper that adds a specification only when the criterion value is not null.
     */
    private <T> Specification<Factclinical> addIfPresent(T criterion, Function<T, Specification<Factclinical>> builder) {
        return criterion != null ? builder.apply(criterion) : Specification.where(null);
    }
}
