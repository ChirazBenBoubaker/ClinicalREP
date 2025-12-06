package com.jhipster.itprogress.pfe.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.itprogress.pfe.domain.Factclinical;
import com.jhipster.itprogress.pfe.service.dto.FactclinicalDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FactclinicalMapperTest {

    private FactclinicalMapper factclinicalMapper;

    @BeforeEach
    void setUp() {
        factclinicalMapper = new FactclinicalMapperImpl();
    }

    @Test
    void shouldMapEntityToDto() {
        Factclinical entity = new Factclinical();
        entity.setId(1L);

        FactclinicalDTO dto = factclinicalMapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
    }

    @Test
    void shouldMapDtoToEntity() {
        FactclinicalDTO dto = new FactclinicalDTO();
        dto.setId(2L);

        Factclinical entity = factclinicalMapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(2L);
    }

    @Test
    void shouldReturnNullWhenEntityIsNull() {
        assertThat(factclinicalMapper.toDto(null)).isNull();
    }

    @Test
    void shouldReturnNullWhenDtoIsNull() {
        assertThat(factclinicalMapper.toEntity(null)).isNull();
    }
}
