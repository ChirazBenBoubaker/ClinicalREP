package com.jhipster.itprogress.pfe.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.itprogress.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProcedureDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcedureDTO.class);
        ProcedureDTO procedureDTO1 = new ProcedureDTO();
        procedureDTO1.setId(1L);
        ProcedureDTO procedureDTO2 = new ProcedureDTO();
        assertThat(procedureDTO1).isNotEqualTo(procedureDTO2);
        procedureDTO2.setId(procedureDTO1.getId());
        assertThat(procedureDTO1).isEqualTo(procedureDTO2);
        procedureDTO2.setId(2L);
        assertThat(procedureDTO1).isNotEqualTo(procedureDTO2);
        procedureDTO1.setId(null);
        assertThat(procedureDTO1).isNotEqualTo(procedureDTO2);
    }
}
