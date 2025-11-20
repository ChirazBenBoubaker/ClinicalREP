package com.jhipster.itprogress.pfe.service.impl;

import com.jhipster.itprogress.pfe.domain.Procedure;
import com.jhipster.itprogress.pfe.repository.ProcedureRepository;
import com.jhipster.itprogress.pfe.service.ProcedureService;
import com.jhipster.itprogress.pfe.service.dto.ProcedureDTO;
import com.jhipster.itprogress.pfe.service.mapper.ProcedureMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Procedure}.
 */
@Service
@Transactional
public class ProcedureServiceImpl implements ProcedureService {

    private final Logger log = LoggerFactory.getLogger(ProcedureServiceImpl.class);

    private final ProcedureRepository procedureRepository;

    private final ProcedureMapper procedureMapper;

    public ProcedureServiceImpl(ProcedureRepository procedureRepository, ProcedureMapper procedureMapper) {
        this.procedureRepository = procedureRepository;
        this.procedureMapper = procedureMapper;
    }

    @Override
    public ProcedureDTO save(ProcedureDTO procedureDTO) {
        log.debug("Request to save Procedure : {}", procedureDTO);
        Procedure procedure = procedureMapper.toEntity(procedureDTO);
        procedure = procedureRepository.save(procedure);
        return procedureMapper.toDto(procedure);
    }

    @Override
    public ProcedureDTO update(ProcedureDTO procedureDTO) {
        log.debug("Request to update Procedure : {}", procedureDTO);
        Procedure procedure = procedureMapper.toEntity(procedureDTO);
        procedure = procedureRepository.save(procedure);
        return procedureMapper.toDto(procedure);
    }

    @Override
    public Optional<ProcedureDTO> partialUpdate(ProcedureDTO procedureDTO) {
        log.debug("Request to partially update Procedure : {}", procedureDTO);

        return procedureRepository
            .findById(procedureDTO.getId())
            .map(existingProcedure -> {
                procedureMapper.partialUpdate(existingProcedure, procedureDTO);

                return existingProcedure;
            })
            .map(procedureRepository::save)
            .map(procedureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcedureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Procedures");
        return procedureRepository.findAll(pageable).map(procedureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProcedureDTO> findOne(Long id) {
        log.debug("Request to get Procedure : {}", id);
        return procedureRepository.findById(id).map(procedureMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Procedure : {}", id);
        procedureRepository.deleteById(id);
    }
}
