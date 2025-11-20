package com.jhipster.itprogress.pfe.service;

import com.jhipster.itprogress.pfe.domain.*; // for static metamodels
import com.jhipster.itprogress.pfe.domain.Immunization;
import com.jhipster.itprogress.pfe.repository.ImmunizationRepository;
import com.jhipster.itprogress.pfe.service.criteria.ImmunizationCriteria;
import com.jhipster.itprogress.pfe.service.dto.ImmunizationDTO;
import com.jhipster.itprogress.pfe.service.mapper.ImmunizationMapper;
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
 * Service for executing complex queries for {@link Immunization} entities in the database.
 * The main input is a {@link ImmunizationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ImmunizationDTO} or a {@link Page} of {@link ImmunizationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ImmunizationQueryService extends QueryService<Immunization> {

    private final Logger log = LoggerFactory.getLogger(ImmunizationQueryService.class);

    private final ImmunizationRepository immunizationRepository;

    private final ImmunizationMapper immunizationMapper;

    public ImmunizationQueryService(ImmunizationRepository immunizationRepository, ImmunizationMapper immunizationMapper) {
        this.immunizationRepository = immunizationRepository;
        this.immunizationMapper = immunizationMapper;
    }

    /**
     * Return a {@link List} of {@link ImmunizationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ImmunizationDTO> findByCriteria(ImmunizationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Immunization> specification = createSpecification(criteria);
        return immunizationMapper.toDto(immunizationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ImmunizationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ImmunizationDTO> findByCriteria(ImmunizationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Immunization> specification = createSpecification(criteria);
        return immunizationRepository.findAll(specification, page).map(immunizationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ImmunizationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Immunization> specification = createSpecification(criteria);
        return immunizationRepository.count(specification);
    }

    /**
     * Function to convert {@link ImmunizationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Immunization> createSpecification(ImmunizationCriteria criteria) {
        Specification<Immunization> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Immunization_.id));
            }
            if (criteria.getImmunization() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImmunization(), Immunization_.immunization));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Immunization_.date));
            }
            if (criteria.getPatientUID() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPatientUID(), Immunization_.patientUID));
            }
        }
        return specification;
    }
}
