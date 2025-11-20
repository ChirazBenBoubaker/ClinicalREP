package com.jhipster.itprogress.pfe.service.mapper;

import com.jhipster.itprogress.pfe.domain.Medication;
import com.jhipster.itprogress.pfe.service.dto.MedicationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Medication} and its DTO {@link MedicationDTO}.
 */
@Mapper(componentModel = "spring")
public interface MedicationMapper extends EntityMapper<MedicationDTO, Medication> {}
