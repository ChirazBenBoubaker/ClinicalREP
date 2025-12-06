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
    void entityWithIdShouldBeMappedToDtoWithSameId() {
        Condition entity = new Condition();
        entity.setId(999L);

        ConditionDTO dto = conditionMapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(999L);
    }

    @Test
    void dtoWithIdShouldBeMappedToEntityWithSameId() {
        ConditionDTO dto = new ConditionDTO();
        dto.setId(777L);

        Condition entity = conditionMapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(777L);
    }

    @Test
    void nullEntityShouldReturnNullDto() {
        assertThat(conditionMapper.toDto(null)).isNull();
    }

    @Test
    void nullDtoShouldReturnNullEntity() {
        assertThat(conditionMapper.toEntity(null)).isNull();
    }
}
