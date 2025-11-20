package com.jhipster.itprogress.pfe.service;

import com.jhipster.itprogress.pfe.service.dto.ObservationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.jhipster.itprogress.pfe.domain.Observation}.
 */
public interface ObservationService {
    /**
     * Save a observation.
     *
     * @param observationDTO the entity to save.
     * @return the persisted entity.
     */
    ObservationDTO save(ObservationDTO observationDTO);

    /**
     * Updates a observation.
     *
     * @param observationDTO the entity to update.
     * @return the persisted entity.
     */
    ObservationDTO update(ObservationDTO observationDTO);

    /**
     * Partially updates a observation.
     *
     * @param observationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ObservationDTO> partialUpdate(ObservationDTO observationDTO);

    /**
     * Get all the observations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ObservationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" observation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ObservationDTO> findOne(Long id);

    /**
     * Delete the "id" observation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
