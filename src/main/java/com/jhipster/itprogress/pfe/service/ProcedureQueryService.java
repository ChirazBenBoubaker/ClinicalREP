package com.jhipster.itprogress.pfe.service;

import com.jhipster.itprogress.pfe.domain.*; // for static metamodels
import com.jhipster.itprogress.pfe.domain.Procedure;
import com.jhipster.itprogress.pfe.repository.ProcedureRepository;
import com.jhipster.itprogress.pfe.service.criteria.ProcedureCriteria;
import com.jhipster.itprogress.pfe.service.dto.ProcedureDTO;
import com.jhipster.itprogress.pfe.service.mapper.ProcedureMapper;
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
 * Service for executing complex queries for {@link Procedure} entities in the database.
 * The main input is a {@link ProcedureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProcedureDTO} or a {@link Page} of {@link ProcedureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProcedureQueryService extends QueryService<Procedure> {

    private final Logger log = LoggerFactory.getLogger(ProcedureQueryService.class);

    private final ProcedureRepository procedureRepository;

    private final ProcedureMapper procedureMapper;

    public ProcedureQueryService(ProcedureRepository procedureRepository, ProcedureMapper procedureMapper) {
        this.procedureRepository = procedureRepository;
        this.procedureMapper = procedureMapper;
    }

    /**
     * Return a {@link List} of {@link ProcedureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProcedureDTO> findByCriteria(ProcedureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Procedure> specification = createSpecification(criteria);
        return procedureMapper.toDto(procedureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProcedureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcedureDTO> findByCriteria(ProcedureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Procedure> specification = createSpecification(criteria);
        return procedureRepository.findAll(specification, page).map(procedureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProcedureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Procedure> specification = createSpecification(criteria);
        return procedureRepository.count(specification);
    }

    /**
     * Function to convert {@link ProcedureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Procedure> createSpecification(ProcedureCriteria criteria) {
        Specification<Procedure> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Procedure_.id));
            }
            if (criteria.getProcedureText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProcedureText(), Procedure_.procedureText));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Procedure_.date));
            }
            if (criteria.getPatientUID() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPatientUID(), Procedure_.patientUID));
            }
        }
        return specification;
    }
}
