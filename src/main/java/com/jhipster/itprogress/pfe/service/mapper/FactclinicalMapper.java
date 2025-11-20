package com.jhipster.itprogress.pfe.service.mapper;

import com.jhipster.itprogress.pfe.domain.Factclinical;
import com.jhipster.itprogress.pfe.service.dto.FactclinicalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Factclinical} and its DTO {@link FactclinicalDTO}.
 */
@Mapper(componentModel = "spring")
public interface FactclinicalMapper extends EntityMapper<FactclinicalDTO, Factclinical> {}
