package com.jhipster.itprogress.pfe.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcedureMapperTest {

    private ProcedureMapper procedureMapper;

    @BeforeEach
    public void setUp() {
        procedureMapper = new ProcedureMapperImpl();
    }
}
