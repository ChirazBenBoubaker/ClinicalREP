package com.jhipster.itprogress.pfe.service;

import com.jhipster.itprogress.pfe.service.dto.ProcedureDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.jhipster.itprogress.pfe.domain.Procedure}.
 */
public interface ProcedureService {
    /**
     * Save a procedure.
     *
     * @param procedureDTO the entity to save.
     * @return the persisted entity.
     */
    ProcedureDTO save(ProcedureDTO procedureDTO);

    /**
     * Updates a procedure.
     *
     * @param procedureDTO the entity to update.
     * @return the persisted entity.
     */
    ProcedureDTO update(ProcedureDTO procedureDTO);

    /**
     * Partially updates a procedure.
     *
     * @param procedureDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProcedureDTO> partialUpdate(ProcedureDTO procedureDTO);

    /**
     * Get all the procedures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProcedureDTO> findAll(Pageable pageable);

    /**
     * Get the "id" procedure.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProcedureDTO> findOne(Long id);

    /**
     * Delete the "id" procedure.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
