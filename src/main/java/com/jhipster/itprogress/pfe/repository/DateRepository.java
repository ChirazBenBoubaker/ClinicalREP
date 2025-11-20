package com.jhipster.itprogress.pfe.repository;

import com.jhipster.itprogress.pfe.domain.Date;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Date entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DateRepository extends JpaRepository<Date, Long>, JpaSpecificationExecutor<Date> {}
