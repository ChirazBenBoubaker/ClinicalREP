package com.jhipster.itprogress.pfe.service.impl;

import com.jhipster.itprogress.pfe.domain.Factclinical;
import com.jhipster.itprogress.pfe.repository.FactclinicalRepository;
import com.jhipster.itprogress.pfe.service.FactclinicalService;
import com.jhipster.itprogress.pfe.service.dto.FactclinicalDTO;
import com.jhipster.itprogress.pfe.service.mapper.FactclinicalMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Factclinical}.
 */
@Service
@Transactional
public class FactclinicalServiceImpl implements FactclinicalService {

    private final Logger log = LoggerFactory.getLogger(FactclinicalServiceImpl.class);

    private final FactclinicalRepository factclinicalRepository;

    private final FactclinicalMapper factclinicalMapper;

    public FactclinicalServiceImpl(FactclinicalRepository factclinicalRepository, FactclinicalMapper factclinicalMapper) {
        this.factclinicalRepository = factclinicalRepository;
        this.factclinicalMapper = factclinicalMapper;
    }

    @Override
    public FactclinicalDTO save(FactclinicalDTO factclinicalDTO) {
        log.debug("Request to save Factclinical : {}", factclinicalDTO);
        Factclinical factclinical = factclinicalMapper.toEntity(factclinicalDTO);
        factclinical = factclinicalRepository.save(factclinical);
        return factclinicalMapper.toDto(factclinical);
    }

    @Override
    public FactclinicalDTO update(FactclinicalDTO factclinicalDTO) {
        log.debug("Request to update Factclinical : {}", factclinicalDTO);
        Factclinical factclinical = factclinicalMapper.toEntity(factclinicalDTO);
        factclinical = factclinicalRepository.save(factclinical);
        return factclinicalMapper.toDto(factclinical);
    }

    @Override
    public Optional<FactclinicalDTO> partialUpdate(FactclinicalDTO factclinicalDTO) {
        log.debug("Request to partially update Factclinical : {}", factclinicalDTO);

        return factclinicalRepository
            .findById(factclinicalDTO.getId())
            .map(existingFactclinical -> {
                factclinicalMapper.partialUpdate(existingFactclinical, factclinicalDTO);

                return existingFactclinical;
            })
            .map(factclinicalRepository::save)
            .map(factclinicalMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FactclinicalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Factclinicals");
        return factclinicalRepository.findAll(pageable).map(factclinicalMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FactclinicalDTO> findOne(Long id) {
        log.debug("Request to get Factclinical : {}", id);
        return factclinicalRepository.findById(id).map(factclinicalMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Factclinical : {}", id);
        factclinicalRepository.deleteById(id);
    }
}
