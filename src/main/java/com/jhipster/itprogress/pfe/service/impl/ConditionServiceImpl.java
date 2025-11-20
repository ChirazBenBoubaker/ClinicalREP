package com.jhipster.itprogress.pfe.service.impl;

import com.jhipster.itprogress.pfe.domain.Condition;
import com.jhipster.itprogress.pfe.repository.ConditionRepository;
import com.jhipster.itprogress.pfe.service.ConditionService;
import com.jhipster.itprogress.pfe.service.dto.ConditionDTO;
import com.jhipster.itprogress.pfe.service.mapper.ConditionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Condition}.
 */
@Service
@Transactional
public class ConditionServiceImpl implements ConditionService {

    private final Logger log = LoggerFactory.getLogger(ConditionServiceImpl.class);

    private final ConditionRepository conditionRepository;

    private final ConditionMapper conditionMapper;

    public ConditionServiceImpl(ConditionRepository conditionRepository, ConditionMapper conditionMapper) {
        this.conditionRepository = conditionRepository;
        this.conditionMapper = conditionMapper;
    }

    @Override
    public ConditionDTO save(ConditionDTO conditionDTO) {
        log.debug("Request to save Condition : {}", conditionDTO);
        Condition condition = conditionMapper.toEntity(conditionDTO);
        condition = conditionRepository.save(condition);
        return conditionMapper.toDto(condition);
    }

    @Override
    public ConditionDTO update(ConditionDTO conditionDTO) {
        log.debug("Request to update Condition : {}", conditionDTO);
        Condition condition = conditionMapper.toEntity(conditionDTO);
        condition = conditionRepository.save(condition);
        return conditionMapper.toDto(condition);
    }

    @Override
    public Optional<ConditionDTO> partialUpdate(ConditionDTO conditionDTO) {
        log.debug("Request to partially update Condition : {}", conditionDTO);

        return conditionRepository
            .findById(conditionDTO.getId())
            .map(existingCondition -> {
                conditionMapper.partialUpdate(existingCondition, conditionDTO);

                return existingCondition;
            })
            .map(conditionRepository::save)
            .map(conditionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConditionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Conditions");
        return conditionRepository.findAll(pageable).map(conditionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConditionDTO> findOne(Long id) {
        log.debug("Request to get Condition : {}", id);
        return conditionRepository.findById(id).map(conditionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Condition : {}", id);
        conditionRepository.deleteById(id);
    }
}
