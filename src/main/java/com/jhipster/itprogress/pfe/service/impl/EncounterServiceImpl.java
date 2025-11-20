package com.jhipster.itprogress.pfe.service.impl;

import com.jhipster.itprogress.pfe.domain.Encounter;
import com.jhipster.itprogress.pfe.repository.EncounterRepository;
import com.jhipster.itprogress.pfe.service.EncounterService;
import com.jhipster.itprogress.pfe.service.dto.EncounterDTO;
import com.jhipster.itprogress.pfe.service.mapper.EncounterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Encounter}.
 */
@Service
@Transactional
public class EncounterServiceImpl implements EncounterService {

    private final Logger log = LoggerFactory.getLogger(EncounterServiceImpl.class);

    private final EncounterRepository encounterRepository;

    private final EncounterMapper encounterMapper;

    public EncounterServiceImpl(EncounterRepository encounterRepository, EncounterMapper encounterMapper) {
        this.encounterRepository = encounterRepository;
        this.encounterMapper = encounterMapper;
    }

    @Override
    public EncounterDTO save(EncounterDTO encounterDTO) {
        log.debug("Request to save Encounter : {}", encounterDTO);
        Encounter encounter = encounterMapper.toEntity(encounterDTO);
        encounter = encounterRepository.save(encounter);
        return encounterMapper.toDto(encounter);
    }

    @Override
    public EncounterDTO update(EncounterDTO encounterDTO) {
        log.debug("Request to update Encounter : {}", encounterDTO);
        Encounter encounter = encounterMapper.toEntity(encounterDTO);
        encounter = encounterRepository.save(encounter);
        return encounterMapper.toDto(encounter);
    }

    @Override
    public Optional<EncounterDTO> partialUpdate(EncounterDTO encounterDTO) {
        log.debug("Request to partially update Encounter : {}", encounterDTO);

        return encounterRepository
            .findById(encounterDTO.getId())
            .map(existingEncounter -> {
                encounterMapper.partialUpdate(existingEncounter, encounterDTO);

                return existingEncounter;
            })
            .map(encounterRepository::save)
            .map(encounterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EncounterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Encounters");
        return encounterRepository.findAll(pageable).map(encounterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EncounterDTO> findOne(Long id) {
        log.debug("Request to get Encounter : {}", id);
        return encounterRepository.findById(id).map(encounterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Encounter : {}", id);
        encounterRepository.deleteById(id);
    }

    @Override
    public Long EncountersCountMonthYear(Integer month, Integer year) {
        Long result = encounterRepository.EncountersMonthYear(month, year);
        return result != null ? result : null;
    }
}
