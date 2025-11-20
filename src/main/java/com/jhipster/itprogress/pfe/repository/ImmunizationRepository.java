package com.jhipster.itprogress.pfe.repository;

import com.jhipster.itprogress.pfe.domain.Immunization;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Immunization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImmunizationRepository extends JpaRepository<Immunization, Long>, JpaSpecificationExecutor<Immunization> {
    @Query(
        "select count(*) from Immunization e where ( (extract(MONTH from e.date)= :monthTest) and (extract(YEAR from e.date)= :yearTest) ) "
    )
    public Long ImmunizationsMonthYear(@Param("monthTest") Integer monthTest, @Param("yearTest") Integer yearTest);
}
