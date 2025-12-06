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
        if (criteria == null) {
            return Specification.where(null);
        }

        Specification<Factclinical> specification = Specification.where(null);

        // distinct doit être appliqué en premier (comportement JHipster)
        if (criteria.getDistinct() != null) {
            specification = specification.and(distinct(criteria.getDistinct()));
        }

        // Ajout conditionnel de chaque critère via un helper → réduit fortement la complexité cognitive
        specification = addIfPresent(specification, criteria.getId(),
                id -> buildRangeSpecification(id, Factclinical_.id));

        specification = addIfPresent(specification, criteria.getPatientUID(),
                uid -> buildStringSpecification(uid, Factclinical_.patientUID));

        specification = addIfPresent(specification, criteria.getEncounterID(),
                id -> buildRangeSpecification(id, Factclinical_.encounterID));

        specification = addIfPresent(specification, criteria.getObservationID(),
                id -> buildRangeSpecification(id, Factclinical_.observationID));

        specification = addIfPresent(specification, criteria.getProcedureID(),
                id -> buildRangeSpecification(id, Factclinical_.procedureID));

        specification = addIfPresent(specification, criteria.getImmunizationID(),
                id -> buildRangeSpecification(id, Factclinical_.immunizationID));

        specification = addIfPresent(specification, criteria.getMedicationID(),
                id -> buildRangeSpecification(id, Factclinical_.medicationID));

        specification = addIfPresent(specification, criteria.getConditionID(),
                id -> buildRangeSpecification(id, Factclinical_.conditionID));

        specification = addIfPresent(specification, criteria.getDate(),
                date -> buildRangeSpecification(date, Factclinical_.date));

        return specification;
    }

    /**
     * Méthode utilitaire générique pour ajouter une specification seulement si le critère est non null.
     * Chaque appel ne compte que comme +1 en complexité cognitive (au lieu de +2 pour un if + and).
     */
    private <T> Specification<Factclinical> addIfPresent(
            Specification<Factclinical> spec,
            T criterion,
            Function<T, Specification<Factclinical>> builder) {

        return criterion != null ? spec.and(builder.apply(criterion)) : spec;
    }
}
