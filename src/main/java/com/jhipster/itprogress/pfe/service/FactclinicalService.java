package com.jhipster.itprogress.pfe.service;

import com.jhipster.itprogress.pfe.service.dto.FactclinicalDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.jhipster.itprogress.pfe.domain.Factclinical}.
 */
public interface FactclinicalService {
    /**
     * Save a factclinical.
     *
     * @param factclinicalDTO the entity to save.
     * @return the persisted entity.
     */
    FactclinicalDTO save(FactclinicalDTO factclinicalDTO);

    /**
     * Updates a factclinical.
     *
     * @param factclinicalDTO the entity to update.
     * @return the persisted entity.
     */
    FactclinicalDTO update(FactclinicalDTO factclinicalDTO);

    /**
     * Partially updates a factclinical.
     *
     * @param factclinicalDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FactclinicalDTO> partialUpdate(FactclinicalDTO factclinicalDTO);

    /**
     * Get all the factclinicals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FactclinicalDTO> findAll(Pageable pageable);

    /**
     * Get the "id" factclinical.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FactclinicalDTO> findOne(Long id);

    /**
     * Delete the "id" factclinical.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
