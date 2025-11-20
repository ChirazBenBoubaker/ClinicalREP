package com.jhipster.itprogress.pfe.service;

import com.jhipster.itprogress.pfe.service.dto.ConditionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.jhipster.itprogress.pfe.domain.Condition}.
 */
public interface ConditionService {
    /**
     * Save a condition.
     *
     * @param conditionDTO the entity to save.
     * @return the persisted entity.
     */
    ConditionDTO save(ConditionDTO conditionDTO);

    /**
     * Updates a condition.
     *
     * @param conditionDTO the entity to update.
     * @return the persisted entity.
     */
    ConditionDTO update(ConditionDTO conditionDTO);

    /**
     * Partially updates a condition.
     *
     * @param conditionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConditionDTO> partialUpdate(ConditionDTO conditionDTO);

    /**
     * Get all the conditions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConditionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" condition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConditionDTO> findOne(Long id);

    /**
     * Delete the "id" condition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
