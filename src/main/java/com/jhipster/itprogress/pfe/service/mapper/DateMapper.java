package com.jhipster.itprogress.pfe.service.mapper;

import com.jhipster.itprogress.pfe.domain.Date;
import com.jhipster.itprogress.pfe.service.dto.DateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Date} and its DTO {@link DateDTO}.
 */
@Mapper(componentModel = "spring")
public interface DateMapper extends EntityMapper<DateDTO, Date> {}
