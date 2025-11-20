package com.jhipster.itprogress.pfe.service.impl;

import com.jhipster.itprogress.pfe.domain.Date;
import com.jhipster.itprogress.pfe.repository.DateRepository;
import com.jhipster.itprogress.pfe.service.DateService;
import com.jhipster.itprogress.pfe.service.dto.DateDTO;
import com.jhipster.itprogress.pfe.service.mapper.DateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Date}.
 */
@Service
@Transactional
public class DateServiceImpl implements DateService {

    private final Logger log = LoggerFactory.getLogger(DateServiceImpl.class);

    private final DateRepository dateRepository;

    private final DateMapper dateMapper;

    public DateServiceImpl(DateRepository dateRepository, DateMapper dateMapper) {
        this.dateRepository = dateRepository;
        this.dateMapper = dateMapper;
    }

    @Override
    public DateDTO save(DateDTO dateDTO) {
        log.debug("Request to save Date : {}", dateDTO);
        Date date = dateMapper.toEntity(dateDTO);
        date = dateRepository.save(date);
        return dateMapper.toDto(date);
    }

    @Override
    public DateDTO update(DateDTO dateDTO) {
        log.debug("Request to update Date : {}", dateDTO);
        Date date = dateMapper.toEntity(dateDTO);
        date = dateRepository.save(date);
        return dateMapper.toDto(date);
    }

    @Override
    public Optional<DateDTO> partialUpdate(DateDTO dateDTO) {
        log.debug("Request to partially update Date : {}", dateDTO);

        return dateRepository
            .findById(dateDTO.getId())
            .map(existingDate -> {
                dateMapper.partialUpdate(existingDate, dateDTO);

                return existingDate;
            })
            .map(dateRepository::save)
            .map(dateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dates");
        return dateRepository.findAll(pageable).map(dateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DateDTO> findOne(Long id) {
        log.debug("Request to get Date : {}", id);
        return dateRepository.findById(id).map(dateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Date : {}", id);
        dateRepository.deleteById(id);
    }
}
