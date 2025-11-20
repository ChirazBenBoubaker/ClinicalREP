package com.jhipster.itprogress.pfe.service;

import com.jhipster.itprogress.pfe.domain.*; // for static metamodels
import com.jhipster.itprogress.pfe.domain.Encounter;
import com.jhipster.itprogress.pfe.repository.EncounterRepository;
import com.jhipster.itprogress.pfe.service.criteria.EncounterCriteria;
import com.jhipster.itprogress.pfe.service.dto.EncounterDTO;
import com.jhipster.itprogress.pfe.service.mapper.EncounterMapper;
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
 * Service for executing complex queries for {@link Encounter} entities in the database.
 * The main input is a {@link EncounterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EncounterDTO} or a {@link Page} of {@link EncounterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EncounterQueryService extends QueryService<Encounter> {

    private final Logger log = LoggerFactory.getLogger(EncounterQueryService.class);

    private final EncounterRepository encounterRepository;

    private final EncounterMapper encounterMapper;

    public EncounterQueryService(EncounterRepository encounterRepository, EncounterMapper encounterMapper) {
        this.encounterRepository = encounterRepository;
        this.encounterMapper = encounterMapper;
    }

    /**
     * Return a {@link List} of {@link EncounterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EncounterDTO> findByCriteria(EncounterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Encounter> specification = createSpecification(criteria);
        return encounterMapper.toDto(encounterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EncounterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EncounterDTO> findByCriteria(EncounterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Encounter> specification = createSpecification(criteria);
        return encounterRepository.findAll(specification, page).map(encounterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EncounterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Encounter> specification = createSpecification(criteria);
        return encounterRepository.count(specification);
    }

    /**
     * Function to convert {@link EncounterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Encounter> createSpecification(EncounterCriteria criteria) {
        Specification<Encounter> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Encounter_.id));
            }
            if (criteria.getEncountersText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEncountersText(), Encounter_.encountersText));
            }
            if (criteria.getEncounterLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEncounterLocation(), Encounter_.encounterLocation));
            }
            if (criteria.getEncounterProvider() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEncounterProvider(), Encounter_.encounterProvider));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Encounter_.date));
            }
            if (criteria.getPatientUID() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPatientUID(), Encounter_.patientUID));
            }
        }
        return specification;
    }
}
