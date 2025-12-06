package com.jhipster.itprogress.pfe.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConditionMapperTest {

    private ConditionMapper conditionMapper;

    @BeforeEach
    public void setUp() {
        conditionMapper = new ConditionMapperImpl();
    }

    @Test
    void testConditionMapperNotNull() {
        // Test basique pour vérifier que le mapper est bien instancié
        assertThat(conditionMapper).isNotNull();
    }

    // Tu peux ajouter ici d'autres tests réels, par exemple :
    // @Test
    // void toDto_shouldMapEntityToDtoCorrectly() { ... }
}
