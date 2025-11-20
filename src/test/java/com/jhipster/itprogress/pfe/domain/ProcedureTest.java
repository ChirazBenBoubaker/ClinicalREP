package com.jhipster.itprogress.pfe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.itprogress.pfe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProcedureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Procedure.class);
        Procedure procedure1 = new Procedure();
        procedure1.setId(1L);
        Procedure procedure2 = new Procedure();
        procedure2.setId(procedure1.getId());
        assertThat(procedure1).isEqualTo(procedure2);
        procedure2.setId(2L);
        assertThat(procedure1).isNotEqualTo(procedure2);
        procedure1.setId(null);
        assertThat(procedure1).isNotEqualTo(procedure2);
    }
}
