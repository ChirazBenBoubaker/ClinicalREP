package com.jhipster.itprogress.pfe.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.itprogress.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EncounterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EncounterDTO.class);
        EncounterDTO encounterDTO1 = new EncounterDTO();
        encounterDTO1.setId(1L);
        EncounterDTO encounterDTO2 = new EncounterDTO();
        assertThat(encounterDTO1).isNotEqualTo(encounterDTO2);
        encounterDTO2.setId(encounterDTO1.getId());
        assertThat(encounterDTO1).isEqualTo(encounterDTO2);
        encounterDTO2.setId(2L);
        assertThat(encounterDTO1).isNotEqualTo(encounterDTO2);
        encounterDTO1.setId(null);
        assertThat(encounterDTO1).isNotEqualTo(encounterDTO2);
    }
}
