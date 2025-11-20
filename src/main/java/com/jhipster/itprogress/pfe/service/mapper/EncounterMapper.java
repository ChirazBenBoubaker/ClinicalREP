package com.jhipster.itprogress.pfe.service.mapper;

import com.jhipster.itprogress.pfe.domain.Encounter;
import com.jhipster.itprogress.pfe.service.dto.EncounterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Encounter} and its DTO {@link EncounterDTO}.
 */
@Mapper(componentModel = "spring")
public interface EncounterMapper extends EntityMapper<EncounterDTO, Encounter> {}
