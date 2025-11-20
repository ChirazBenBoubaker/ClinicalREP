package com.jhipster.itprogress.pfe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.itprogress.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FactclinicalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Factclinical.class);
        Factclinical factclinical1 = new Factclinical();
        factclinical1.setId(1L);
        Factclinical factclinical2 = new Factclinical();
        factclinical2.setId(factclinical1.getId());
        assertThat(factclinical1).isEqualTo(factclinical2);
        factclinical2.setId(2L);
        assertThat(factclinical1).isNotEqualTo(factclinical2);
        factclinical1.setId(null);
        assertThat(factclinical1).isNotEqualTo(factclinical2);
    }
}
