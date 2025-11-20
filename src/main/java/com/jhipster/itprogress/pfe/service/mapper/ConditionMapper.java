package com.jhipster.itprogress.pfe.service.mapper;

import com.jhipster.itprogress.pfe.domain.Condition;
import com.jhipster.itprogress.pfe.service.dto.ConditionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Condition} and its DTO {@link ConditionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConditionMapper extends EntityMapper<ConditionDTO, Condition> {}
