package com.jhipster.itprogress.pfe.service;

import com.jhipster.itprogress.pfe.domain.*; // for static metamodels
import com.jhipster.itprogress.pfe.domain.Condition;
import com.jhipster.itprogress.pfe.repository.ConditionRepository;
import com.jhipster.itprogress.pfe.service.criteria.ConditionCriteria;
import com.jhipster.itprogress.pfe.service.dto.ConditionDTO;
import com.jhipster.itprogress.pfe.service.mapper.ConditionMapper;
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
 * Service for executing complex queries for {@link Condition} entities in the database.
 * The main input is a {@link ConditionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConditionDTO} or a {@link Page} of {@link ConditionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConditionQueryService extends QueryService<Condition> {

    private final Logger log = LoggerFactory.getLogger(ConditionQueryService.class);

    private final ConditionRepository conditionRepository;

    private final ConditionMapper conditionMapper;

    public ConditionQueryService(ConditionRepository conditionRepository, ConditionMapper conditionMapper) {
        this.conditionRepository = conditionRepository;
        this.conditionMapper = conditionMapper;
    }

    /**
     * Return a {@link List} of {@link ConditionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConditionDTO> findByCriteria(ConditionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Condition> specification = createSpecification(criteria);
        return conditionMapper.toDto(conditionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ConditionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConditionDTO> findByCriteria(ConditionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Condition> specification = createSpecification(criteria);
        return conditionRepository.findAll(specification, page).map(conditionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConditionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Condition> specification = createSpecification(criteria);
        return conditionRepository.count(specification);
    }

    /**
     * Function to convert {@link ConditionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Condition> createSpecification(ConditionCriteria criteria) {
        Specification<Condition> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Condition_.id));
            }
            if (criteria.getConditionText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConditionText(), Condition_.conditionText));
            }
            if (criteria.getConditionOnsetDates() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getConditionOnsetDates(), Condition_.conditionOnsetDates));
            }
            if (criteria.getPatientUID() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPatientUID(), Condition_.patientUID));
            }
        }
        return specification;
    }
}
