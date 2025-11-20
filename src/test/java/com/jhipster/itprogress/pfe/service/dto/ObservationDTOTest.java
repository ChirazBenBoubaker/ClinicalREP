package com.jhipster.itprogress.pfe.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.itprogress.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ObservationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObservationDTO.class);
        ObservationDTO observationDTO1 = new ObservationDTO();
        observationDTO1.setId(1L);
        ObservationDTO observationDTO2 = new ObservationDTO();
        assertThat(observationDTO1).isNotEqualTo(observationDTO2);
        observationDTO2.setId(observationDTO1.getId());
        assertThat(observationDTO1).isEqualTo(observationDTO2);
        observationDTO2.setId(2L);
        assertThat(observationDTO1).isNotEqualTo(observationDTO2);
        observationDTO1.setId(null);
        assertThat(observationDTO1).isNotEqualTo(observationDTO2);
    }
}
