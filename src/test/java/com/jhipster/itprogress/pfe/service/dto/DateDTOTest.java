package com.jhipster.itprogress.pfe.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.itprogress.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DateDTO.class);
        DateDTO dateDTO1 = new DateDTO();
        dateDTO1.setId(1L);
        DateDTO dateDTO2 = new DateDTO();
        assertThat(dateDTO1).isNotEqualTo(dateDTO2);
        dateDTO2.setId(dateDTO1.getId());
        assertThat(dateDTO1).isEqualTo(dateDTO2);
        dateDTO2.setId(2L);
        assertThat(dateDTO1).isNotEqualTo(dateDTO2);
        dateDTO1.setId(null);
        assertThat(dateDTO1).isNotEqualTo(dateDTO2);
    }
}
