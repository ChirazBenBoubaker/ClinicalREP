package com.jhipster.itprogress.pfe.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.itprogress.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImmunizationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImmunizationDTO.class);
        ImmunizationDTO immunizationDTO1 = new ImmunizationDTO();
        immunizationDTO1.setId(1L);
        ImmunizationDTO immunizationDTO2 = new ImmunizationDTO();
        assertThat(immunizationDTO1).isNotEqualTo(immunizationDTO2);
        immunizationDTO2.setId(immunizationDTO1.getId());
        assertThat(immunizationDTO1).isEqualTo(immunizationDTO2);
        immunizationDTO2.setId(2L);
        assertThat(immunizationDTO1).isNotEqualTo(immunizationDTO2);
        immunizationDTO1.setId(null);
        assertThat(immunizationDTO1).isNotEqualTo(immunizationDTO2);
    }
}
