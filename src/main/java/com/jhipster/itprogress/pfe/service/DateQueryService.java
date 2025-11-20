package com.jhipster.itprogress.pfe.service;

import com.jhipster.itprogress.pfe.domain.*; // for static metamodels
import com.jhipster.itprogress.pfe.domain.Date;
import com.jhipster.itprogress.pfe.repository.DateRepository;
import com.jhipster.itprogress.pfe.service.criteria.DateCriteria;
import com.jhipster.itprogress.pfe.service.dto.DateDTO;
import com.jhipster.itprogress.pfe.service.mapper.DateMapper;
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
 * Service for executing complex queries for {@link Date} entities in the database.
 * The main input is a {@link DateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DateDTO} or a {@link Page} of {@link DateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DateQueryService extends QueryService<Date> {

    private final Logger log = LoggerFactory.getLogger(DateQueryService.class);

    private final DateRepository dateRepository;

    private final DateMapper dateMapper;

    public DateQueryService(DateRepository dateRepository, DateMapper dateMapper) {
        this.dateRepository = dateRepository;
        this.dateMapper = dateMapper;
    }

    /**
     * Return a {@link List} of {@link DateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DateDTO> findByCriteria(DateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Date> specification = createSpecification(criteria);
        return dateMapper.toDto(dateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DateDTO> findByCriteria(DateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Date> specification = createSpecification(criteria);
        return dateRepository.findAll(specification, page).map(dateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Date> specification = createSpecification(criteria);
        return dateRepository.count(specification);
    }

    /**
     * Function to convert {@link DateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Date> createSpecification(DateCriteria criteria) {
        Specification<Date> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Date_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Date_.date));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), Date_.year));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMonth(), Date_.month));
            }
            if (criteria.getDay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDay(), Date_.day));
            }
        }
        return specification;
    }
}
