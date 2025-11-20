package com.jhipster.itprogress.pfe.service;

import com.jhipster.itprogress.pfe.service.dto.DateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.jhipster.itprogress.pfe.domain.Date}.
 */
public interface DateService {
    /**
     * Save a date.
     *
     * @param dateDTO the entity to save.
     * @return the persisted entity.
     */
    DateDTO save(DateDTO dateDTO);

    /**
     * Updates a date.
     *
     * @param dateDTO the entity to update.
     * @return the persisted entity.
     */
    DateDTO update(DateDTO dateDTO);

    /**
     * Partially updates a date.
     *
     * @param dateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DateDTO> partialUpdate(DateDTO dateDTO);

    /**
     * Get all the dates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" date.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DateDTO> findOne(Long id);

    /**
     * Delete the "id" date.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
