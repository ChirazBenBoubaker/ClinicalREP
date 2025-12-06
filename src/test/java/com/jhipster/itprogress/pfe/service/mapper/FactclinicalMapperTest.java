package com.jhipster.itprogress.pfe.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jhipster.itprogress.pfe.domain.FactClinical;
import com.jhipster.itprogress.pfe.service.dto.FactClinicalDTO;

class FactclinicalMapperTest {

    private FactclinicalMapper factclinicalMapper;

    @BeforeEach
    void setUp() {
        factclinicalMapper = new FactclinicalMapperImpl();
    }

    @Test
    void testToDto_singleEntity() {
        // GIVEN
        FactClinical entity = new FactClinical();
        entity.setId(1L);
        entity.setName("Test Fact");

        // WHEN
        FactClinicalDTO dto = factclinicalMapper.toDto(entity);

        // THEN
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getName()).isEqualTo(entity.getName());
    }

    @Test
    void testToDto_listEntities() {
        // GIVEN
        FactClinical entity1 = new FactClinical();
        entity1.setId(1L);
        entity1.setName("Fact 1");

        FactClinical entity2 = new FactClinical();
        entity2.setId(2L);
        entity2.setName("Fact 2");

        List<FactClinical> entities = List.of(entity1, entity2);

        // WHEN
        List<FactClinicalDTO> dtos = factclinicalMapper.toDto(entities);

        // THEN
        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getId()).isEqualTo(entity1.getId());
        assertThat(dtos.get(1).getId()).isEqualTo(entity2.getId());
    }

    @Test
    void testToEntity_singleDto() {
        // GIVEN
        FactClinicalDTO dto = new FactClinicalDTO();
        dto.setId(10L);
        dto.setName("DTO Fact");

        // WHEN
        FactClinical entity = factclinicalMapper.toEntity(dto);

        // THEN
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getName()).isEqualTo(dto.getName());
    }

    @Test
    void testToEntity_listDtos() {
        // GIVEN
        FactClinicalDTO dto1 = new FactClinicalDTO();
        dto1.setId(1L);
        dto1.setName("DTO 1");

        FactClinicalDTO dto2 = new FactClinicalDTO();
        dto2.setId(2L);
        dto2.setName("DTO 2");

        List<FactClinicalDTO> dtos = List.of(dto1, dto2);

        // WHEN
        List<FactClinical> entities = factclinicalMapper.toEntity(dtos);

        // THEN
        assertThat(entities).hasSize(2);
        assertThat(entities.get(0).getId()).isEqualTo(dto1.getId());
        assertThat(entities.get(1).getId()).isEqualTo(dto2.getId());
    }
}
