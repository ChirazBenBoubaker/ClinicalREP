package com.jhipster.itprogress.pfe.service;

import com.jhipster.itprogress.pfe.service.dto.EncounterDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.jhipster.itprogress.pfe.domain.Encounter}.
 */
public interface EncounterService {
    /**
     * Save a encounter.
     *
     * @param encounterDTO the entity to save.
     * @return the persisted entity.
     */
    EncounterDTO save(EncounterDTO encounterDTO);

    /**
     * Updates a encounter.
     *
     * @param encounterDTO the entity to update.
     * @return the persisted entity.
     */
    EncounterDTO update(EncounterDTO encounterDTO);

    /**
     * Partially updates a encounter.
     *
     * @param encounterDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EncounterDTO> partialUpdate(EncounterDTO encounterDTO);

    /**
     * Get all the encounters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EncounterDTO> findAll(Pageable pageable);

    /**
     * Get the "id" encounter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EncounterDTO> findOne(Long id);

    /**
     * Delete the "id" encounter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Long EncountersCountMonthYear(Integer month, Integer year);
}
