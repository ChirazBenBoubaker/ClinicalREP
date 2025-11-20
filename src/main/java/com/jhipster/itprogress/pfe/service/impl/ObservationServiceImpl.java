package com.jhipster.itprogress.pfe.service.impl;

import com.jhipster.itprogress.pfe.domain.Observation;
import com.jhipster.itprogress.pfe.repository.ObservationRepository;
import com.jhipster.itprogress.pfe.service.ObservationService;
import com.jhipster.itprogress.pfe.service.dto.ObservationDTO;
import com.jhipster.itprogress.pfe.service.mapper.ObservationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Observation}.
 */
@Service
@Transactional
public class ObservationServiceImpl implements ObservationService {

    private final Logger log = LoggerFactory.getLogger(ObservationServiceImpl.class);

    private final ObservationRepository observationRepository;

    private final ObservationMapper observationMapper;

    public ObservationServiceImpl(ObservationRepository observationRepository, ObservationMapper observationMapper) {
        this.observationRepository = observationRepository;
        this.observationMapper = observationMapper;
    }

    @Override
    public ObservationDTO save(ObservationDTO observationDTO) {
        log.debug("Request to save Observation : {}", observationDTO);
        Observation observation = observationMapper.toEntity(observationDTO);
        observation = observationRepository.save(observation);
        return observationMapper.toDto(observation);
    }

    @Override
    public ObservationDTO update(ObservationDTO observationDTO) {
        log.debug("Request to update Observation : {}", observationDTO);
        Observation observation = observationMapper.toEntity(observationDTO);
        observation = observationRepository.save(observation);
        return observationMapper.toDto(observation);
    }

    @Override
    public Optional<ObservationDTO> partialUpdate(ObservationDTO observationDTO) {
        log.debug("Request to partially update Observation : {}", observationDTO);

        return observationRepository
            .findById(observationDTO.getId())
            .map(existingObservation -> {
                observationMapper.partialUpdate(existingObservation, observationDTO);

                return existingObservation;
            })
            .map(observationRepository::save)
            .map(observationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ObservationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Observations");
        return observationRepository.findAll(pageable).map(observationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ObservationDTO> findOne(Long id) {
        log.debug("Request to get Observation : {}", id);
        return observationRepository.findById(id).map(observationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Observation : {}", id);
        observationRepository.deleteById(id);
    }
}
