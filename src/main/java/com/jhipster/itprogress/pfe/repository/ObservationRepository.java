package com.jhipster.itprogress.pfe.repository;

import com.jhipster.itprogress.pfe.domain.Observation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Observation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long>, JpaSpecificationExecutor<Observation> {}
