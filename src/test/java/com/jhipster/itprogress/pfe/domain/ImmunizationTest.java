package com.jhipster.itprogress.pfe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.itprogress.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImmunizationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Immunization.class);
        Immunization immunization1 = new Immunization();
        immunization1.setId(1L);
        Immunization immunization2 = new Immunization();
        immunization2.setId(immunization1.getId());
        assertThat(immunization1).isEqualTo(immunization2);
        immunization2.setId(2L);
        assertThat(immunization1).isNotEqualTo(immunization2);
        immunization1.setId(null);
        assertThat(immunization1).isNotEqualTo(immunization2);
    }
}
