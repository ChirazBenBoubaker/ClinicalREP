package com.jhipster.itprogress.pfe.service.mapper;

import com.jhipster.itprogress.pfe.domain.Procedure;
import com.jhipster.itprogress.pfe.service.dto.ProcedureDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Procedure} and its DTO {@link ProcedureDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProcedureMapper extends EntityMapper<ProcedureDTO, Procedure> {}
