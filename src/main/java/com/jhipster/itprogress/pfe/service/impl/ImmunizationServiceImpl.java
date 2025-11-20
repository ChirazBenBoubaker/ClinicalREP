package com.jhipster.itprogress.pfe.service.impl;

import com.jhipster.itprogress.pfe.domain.Immunization;
import com.jhipster.itprogress.pfe.repository.ImmunizationRepository;
import com.jhipster.itprogress.pfe.service.ImmunizationService;
import com.jhipster.itprogress.pfe.service.dto.ImmunizationDTO;
import com.jhipster.itprogress.pfe.service.mapper.ImmunizationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Immunization}.
 */
@Service
@Transactional
public class ImmunizationServiceImpl implements ImmunizationService {

    private final Logger log = LoggerFactory.getLogger(ImmunizationServiceImpl.class);

    private final ImmunizationRepository immunizationRepository;

    private final ImmunizationMapper immunizationMapper;

    public ImmunizationServiceImpl(ImmunizationRepository immunizationRepository, ImmunizationMapper immunizationMapper) {
        this.immunizationRepository = immunizationRepository;
        this.immunizationMapper = immunizationMapper;
    }

    @Override
    public ImmunizationDTO save(ImmunizationDTO immunizationDTO) {
        log.debug("Request to save Immunization : {}", immunizationDTO);
        Immunization immunization = immunizationMapper.toEntity(immunizationDTO);
        immunization = immunizationRepository.save(immunization);
        return immunizationMapper.toDto(immunization);
    }

    @Override
    public ImmunizationDTO update(ImmunizationDTO immunizationDTO) {
        log.debug("Request to update Immunization : {}", immunizationDTO);
        Immunization immunization = immunizationMapper.toEntity(immunizationDTO);
        immunization = immunizationRepository.save(immunization);
        return immunizationMapper.toDto(immunization);
    }

    @Override
    public Optional<ImmunizationDTO> partialUpdate(ImmunizationDTO immunizationDTO) {
        log.debug("Request to partially update Immunization : {}", immunizationDTO);

        return immunizationRepository
            .findById(immunizationDTO.getId())
            .map(existingImmunization -> {
                immunizationMapper.partialUpdate(existingImmunization, immunizationDTO);

                return existingImmunization;
            })
            .map(immunizationRepository::save)
            .map(immunizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImmunizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Immunizations");
        return immunizationRepository.findAll(pageable).map(immunizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ImmunizationDTO> findOne(Long id) {
        log.debug("Request to get Immunization : {}", id);
        return immunizationRepository.findById(id).map(immunizationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Immunization : {}", id);
        immunizationRepository.deleteById(id);
    }

    @Override
    public Long ImmunizationsCountMonthYear(Integer month, Integer year) {
        Long result = immunizationRepository.ImmunizationsMonthYear(month, year);
        return result != null ? result : null;
    }
}
