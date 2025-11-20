package com.jhipster.itprogress.pfe.service.mapper;

import com.jhipster.itprogress.pfe.domain.Observation;
import com.jhipster.itprogress.pfe.service.dto.ObservationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Observation} and its DTO {@link ObservationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ObservationMapper extends EntityMapper<ObservationDTO, Observation> {}
