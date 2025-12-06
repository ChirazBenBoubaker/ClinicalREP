package com.jhipster.itprogress.pfe.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.itprogress.pfe.domain.Condition;
import com.jhipster.itprogress.pfe.service.dto.ConditionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConditionMapperTest {

    private ConditionMapper conditionMapper;

    @BeforeEach
    void setUp() {
        conditionMapper = new ConditionMapperImpl();
    }

    @Test
    void shouldMapEntityToDto() {
        // given
        Condition entity = new Condition();
        entity.setId(99L);
        entity.setCode("FEVER");
        entity.setLabel("Fièvre élevée");
        entity.setDescription("Température > 38.5°C");
        // ajoute ici tous les autres champs de ton entity Condition

        // when
        ConditionDTO dto = conditionMapper.toDto(entity);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(99L);
        assertThat(dto.getCode()).isEqualTo("FEVER");
        assertThat(dto.getLabel()).isEqualTo("Fièvre élevée");
        assertThat(dto.getDescription()).isEqualTo("Température > 38.5°C");
        // vérifie tous les champs mappés
    }

    @Test
    void shouldMapDtoToEntity() {
        // given
        ConditionDTO dto = new ConditionDTO();
        dto.setId(123L);
        dto.setCode("HYPOTENSION");
        dto.setLabel("Hypotension artérielle");
        dto.setDescription("PA systolique < 90 mmHg");

        // when
        Condition entity = conditionMapper.toEntity(dto);

        // then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(123L);
        assertThat(entity.getCode()).isEqualTo("HYPOTENSION");
        assertThat(entity.getLabel()).isEqualTo("Hypotension artérielle");
        assertThat(entity.getDescription()).isEqualTo("PA systolique < 90 mmHg");
    }

    @Test
    void shouldReturnNullWhenEntityIsNull() {
        assertThat(conditionMapper.toDto(null)).isNull();
    }

    @Test
    void shouldReturnNullWhenDtoIsNull() {
        assertThat(conditionMapper.toEntity(null)).isNull();
    }
}
