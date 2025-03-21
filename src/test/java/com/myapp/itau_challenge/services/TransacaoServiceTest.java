package com.myapp.itau_challenge.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.myapp.itau_challenge.exceptions.TransacaoInvalidaException;
import com.myapp.itau_challenge.model.Transacao;


public class TransacaoServiceTest {

	
	private TransacaoService transacaoService;
	
	@BeforeEach
	void setup() {
		transacaoService = new TransacaoService();
	}
	
	@Test
	void deveAdicionarTransacaoValida() {
		Transacao transacao = new Transacao(new BigDecimal("100.50"), OffsetDateTime.now().minusSeconds(10));
		assertDoesNotThrow(() -> transacaoService.adicionarTransacao(transacao));
		
		List<Transacao> transacoes = transacaoService.obterUltimos60Segundos();
		assertEquals(1, transacoes.size());
		assertEquals(new BigDecimal("100.50"), transacoes.get(0).getValor());
	}
	
	@Test
	void deveLancarExcecaoParaTransacaoNegativa() {
		Transacao transacao = new Transacao(new BigDecimal("-100.50"), OffsetDateTime.now().minusSeconds(60));
		
		TransacaoInvalidaException exception = assertThrows(TransacaoInvalidaException.class, () -> transacaoService.adicionarTransacao(transacao));
		assertEquals("O valor da transação não pode ser negativo.", exception.getMessage());
	}
}
