package com.example.consulta_bd.controllers;

import com.example.consulta_bd.dto.CuentaRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BancoControllerTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @Test
    void create_duplicate_should_conflict() throws Exception {
        var req = new CuentaRequest("AR0011223344", 10.0, "Titular Uno");
        mvc.perform(post("/api/cuentas").contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isCreated());

        var dup = new CuentaRequest("AR0011223344", 0.0, "Otro Titular");
        mvc.perform(post("/api/cuentas").contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dup)))
                .andExpect(status().isConflict());
    }
}
