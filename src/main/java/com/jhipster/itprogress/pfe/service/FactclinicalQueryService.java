package com.jhipster.itprogress.pfe.service;

import com.jhipster.itprogress.pfe.domain.*; // for static metamodels
import com.jhipster.itprogress.pfe.domain.Factclinical;
import com.jhipster.itprogress.pfe.repository.FactclinicalRepository;
import com.jhipster.itprogress.pfe.service.criteria.FactclinicalCriteria;
import com.jhipster.itprogress.pfe.service.dto.FactclinicalDTO;
import com.jhipster.itprogress.pfe.service.mapper.FactclinicalMapper;
import java.util.List;
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

    /**
     * Return a {@link List} of {@link FactclinicalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FactclinicalDTO> findByCriteria(FactclinicalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Factclinical> specification = createSpecification(criteria);
        return factclinicalMapper.toDto(factclinicalRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FactclinicalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FactclinicalDTO> findByCriteria(FactclinicalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Factclinical> specification = createSpecification(criteria);
        return factclinicalRepository.findAll(specification, page).map(factclinicalMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
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
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Factclinical_.id));
            }
            if (criteria.getPatientUID() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPatientUID(), Factclinical_.patientUID));
            }
            if (criteria.getEncounterID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEncounterID(), Factclinical_.encounterID));
            }
            if (criteria.getObservationID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getObservationID(), Factclinical_.observationID));
            }
            if (criteria.getProcedureID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcedureID(), Factclinical_.procedureID));
            }
            if (criteria.getImmunizationID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getImmunizationID(), Factclinical_.immunizationID));
            }
            if (criteria.getMedicationID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMedicationID(), Factclinical_.medicationID));
            }
            if (criteria.getConditionID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConditionID(), Factclinical_.conditionID));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Factclinical_.date));
            }
        }
        return specification;
    }
}
