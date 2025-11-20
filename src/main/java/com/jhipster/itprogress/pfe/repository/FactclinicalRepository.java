package com.jhipster.itprogress.pfe.repository;

import com.jhipster.itprogress.pfe.domain.Factclinical;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Factclinical entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactclinicalRepository extends JpaRepository<Factclinical, Long>, JpaSpecificationExecutor<Factclinical> {}
