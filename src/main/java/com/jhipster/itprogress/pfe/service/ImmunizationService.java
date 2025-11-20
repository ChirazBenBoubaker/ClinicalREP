package com.jhipster.itprogress.pfe.service;

import com.jhipster.itprogress.pfe.service.dto.ImmunizationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.jhipster.itprogress.pfe.domain.Immunization}.
 */
public interface ImmunizationService {
    /**
     * Save a immunization.
     *
     * @param immunizationDTO the entity to save.
     * @return the persisted entity.
     */
    ImmunizationDTO save(ImmunizationDTO immunizationDTO);

    /**
     * Updates a immunization.
     *
     * @param immunizationDTO the entity to update.
     * @return the persisted entity.
     */
    ImmunizationDTO update(ImmunizationDTO immunizationDTO);

    /**
     * Partially updates a immunization.
     *
     * @param immunizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ImmunizationDTO> partialUpdate(ImmunizationDTO immunizationDTO);

    /**
     * Get all the immunizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImmunizationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" immunization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImmunizationDTO> findOne(Long id);

    /**
     * Delete the "id" immunization.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    Long ImmunizationsCountMonthYear(Integer month, Integer year);

}
