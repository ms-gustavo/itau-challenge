package com.myapp.itau_challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.itau_challenge.dto.TransacaoDTO;
import com.myapp.itau_challenge.services.TransacaoService;

@SpringBootTest
@AutoConfigureMockMvc
public class TransacaoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private TransacaoService transacaoService;

	@Test
	void deveCriarTransacaoComSucesso() throws Exception {
		TransacaoDTO transacaoDTO = new TransacaoDTO(new BigDecimal("60.00"), OffsetDateTime.now().minusSeconds(10));

		mockMvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transacaoDTO))).andExpect(status().isCreated());
	}

	@Test
	void deveRetornarErroParaTransacaoNegativa() throws Exception {
		TransacaoDTO transacaoDTO = new TransacaoDTO(new BigDecimal("-100.00"), OffsetDateTime.now().minusSeconds(10));

		mockMvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transacaoDTO))).andExpect(status().isUnprocessableEntity());
	}

	@Test
	void deveApagarTodasAsTransacoes() throws Exception {
		mockMvc.perform(delete("/transacao")).andExpect(status().isOk());
	}

	@Test
	void deveRetornarEstatisticas() throws Exception {
		mockMvc.perform(get("/estatistica")).andExpect(status().isOk())
				.andExpect(jsonPath("$.count").exists());
	}
}
