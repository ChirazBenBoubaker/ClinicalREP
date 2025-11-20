package com.jhipster.itprogress.pfe.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.itprogress.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FactclinicalDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FactclinicalDTO.class);
        FactclinicalDTO factclinicalDTO1 = new FactclinicalDTO();
        factclinicalDTO1.setId(1L);
        FactclinicalDTO factclinicalDTO2 = new FactclinicalDTO();
        assertThat(factclinicalDTO1).isNotEqualTo(factclinicalDTO2);
        factclinicalDTO2.setId(factclinicalDTO1.getId());
        assertThat(factclinicalDTO1).isEqualTo(factclinicalDTO2);
        factclinicalDTO2.setId(2L);
        assertThat(factclinicalDTO1).isNotEqualTo(factclinicalDTO2);
        factclinicalDTO1.setId(null);
        assertThat(factclinicalDTO1).isNotEqualTo(factclinicalDTO2);
    }
}
