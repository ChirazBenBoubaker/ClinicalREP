package com.jhipster.itprogress.pfe.service;

import com.jhipster.itprogress.pfe.domain.*; // for static metamodels
import com.jhipster.itprogress.pfe.domain.Observation;
import com.jhipster.itprogress.pfe.repository.ObservationRepository;
import com.jhipster.itprogress.pfe.service.criteria.ObservationCriteria;
import com.jhipster.itprogress.pfe.service.dto.ObservationDTO;
import com.jhipster.itprogress.pfe.service.mapper.ObservationMapper;
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
 * Service for executing complex queries for {@link Observation} entities in the database.
 * The main input is a {@link ObservationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ObservationDTO} or a {@link Page} of {@link ObservationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ObservationQueryService extends QueryService<Observation> {

    private final Logger log = LoggerFactory.getLogger(ObservationQueryService.class);

    private final ObservationRepository observationRepository;

    private final ObservationMapper observationMapper;

    public ObservationQueryService(ObservationRepository observationRepository, ObservationMapper observationMapper) {
        this.observationRepository = observationRepository;
        this.observationMapper = observationMapper;
    }

    /**
     * Return a {@link List} of {@link ObservationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ObservationDTO> findByCriteria(ObservationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Observation> specification = createSpecification(criteria);
        return observationMapper.toDto(observationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ObservationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ObservationDTO> findByCriteria(ObservationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Observation> specification = createSpecification(criteria);
        return observationRepository.findAll(specification, page).map(observationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ObservationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Observation> specification = createSpecification(criteria);
        return observationRepository.count(specification);
    }

    /**
     * Function to convert {@link ObservationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Observation> createSpecification(ObservationCriteria criteria) {
        Specification<Observation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Observation_.id));
            }
            if (criteria.getBodyHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBodyHeight(), Observation_.bodyHeight));
            }
            if (criteria.getBodyWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBodyWeight(), Observation_.bodyWeight));
            }
            if (criteria.getBodyMass() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBodyMass(), Observation_.bodyMass));
            }
            if (criteria.getPainseverity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPainseverity(), Observation_.painseverity));
            }
            if (criteria.getBloodPressure() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBloodPressure(), Observation_.bloodPressure));
            }
            if (criteria.getTobaccosmokingstatusNHIS() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getTobaccosmokingstatusNHIS(), Observation_.tobaccosmokingstatusNHIS)
                    );
            }
            if (criteria.getCreatinine() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatinine(), Observation_.creatinine));
            }
            if (criteria.getCalcium() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCalcium(), Observation_.calcium));
            }
            if (criteria.getSodium() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSodium(), Observation_.sodium));
            }
            if (criteria.getPotassium() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPotassium(), Observation_.potassium));
            }
            if (criteria.getChloride() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getChloride(), Observation_.chloride));
            }
            if (criteria.getCarbonDioxide() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCarbonDioxide(), Observation_.carbonDioxide));
            }
            if (criteria.getGlucose() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGlucose(), Observation_.glucose));
            }
            if (criteria.getUreaNitrogen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUreaNitrogen(), Observation_.ureaNitrogen));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Observation_.date));
            }
            if (criteria.getPatientUID() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPatientUID(), Observation_.patientUID));
            }
        }
        return specification;
    }
}
