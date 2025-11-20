package com.jhipster.itprogress.pfe.repository;

import com.jhipster.itprogress.pfe.domain.Encounter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Encounter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long>, JpaSpecificationExecutor<Encounter> {
    @Query(
        "select count(*) from Encounter e where ( (extract(MONTH from e.date)= :monthTest) and (extract(YEAR from e.date)= :yearTest) ) "
    )
    public Long EncountersMonthYear(@Param("monthTest") Integer monthTest, @Param("yearTest") Integer yearTest);
}
