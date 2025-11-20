package com.jhipster.itprogress.pfe.service.mapper;

import com.jhipster.itprogress.pfe.domain.Immunization;
import com.jhipster.itprogress.pfe.service.dto.ImmunizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Immunization} and its DTO {@link ImmunizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ImmunizationMapper extends EntityMapper<ImmunizationDTO, Immunization> {}
