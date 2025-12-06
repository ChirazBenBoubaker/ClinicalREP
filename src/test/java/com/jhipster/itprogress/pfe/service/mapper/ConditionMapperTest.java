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
        entity.setId(1L);
        // Mette ici TOUS les champs qui existent vraiment dans ton entité Condition
        // Exemples très fréquents dans les projets JHipster :
        // entity.setName("Fever");
        // entity.setSeverity("HIGH");
        // entity.setCreatedDate(Instant.now());
        // etc.

        // when
        ConditionDTO dto = conditionMapper.toDto(entity);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        // Ajoute les asserts correspondants aux champs que tu as remplis ci-dessus
    }

    @Test
    void shouldMapDtoToEntity() {
        // given
        ConditionDTO dto = new ConditionDTO();
        dto.setId(2L);
        // même chose : remplis avec les vrais setters du DTO

        // when
        Condition entity = conditionMapper.toEntity(dto);

        // then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(2L);
    }

    @Test
    void shouldHandleNullEntity() {
        assertThat(conditionMapper.toDto(null)).isNull();
    }

    @Test
    void shouldHandleNullDto() {
        assertThat(conditionMapper.toEntity(null)).isNull();
    }
}
