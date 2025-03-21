package com.myapp.itau_challenge.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	
	@Test
	void deveLancarExcecaoParaTransacaoFutura() {
		Transacao transacao = new Transacao(new BigDecimal("100.50"), OffsetDateTime.now().plusSeconds(60));
		
		TransacaoInvalidaException exception = assertThrows(TransacaoInvalidaException.class, () -> transacaoService.adicionarTransacao(transacao));
		
		assertEquals("A data da transação não pode estar no futuro.", exception.getMessage());
	}
	
	@Test
	void DeveLimparTransacoes() {
		Transacao transacao1 = new Transacao(new BigDecimal("100.50"), OffsetDateTime.now().minusSeconds(10));
		Transacao transacao2 = new Transacao(new BigDecimal("100.50"), OffsetDateTime.now().minusSeconds(20));
		
		transacaoService.adicionarTransacao(transacao2);
		transacaoService.adicionarTransacao(transacao1);
		
		assertEquals(2, transacaoService.obterUltimos60Segundos().size());
		
		transacaoService.limparTransacoes();
		
		assertEquals(0, transacaoService.obterUltimos60Segundos().size());
	}
	
	@Test
    void deveCalcularEstatisticasCorretamente() {
        transacaoService.adicionarTransacao(new Transacao(new BigDecimal("10.00"), OffsetDateTime.now().minusSeconds(10)));
        transacaoService.adicionarTransacao(new Transacao(new BigDecimal("20.00"), OffsetDateTime.now().minusSeconds(20)));
        transacaoService.adicionarTransacao(new Transacao(new BigDecimal("30.00"), OffsetDateTime.now().minusSeconds(30)));

        var estatisticas = transacaoService.calcularEstatisticas();

        assertEquals(3, estatisticas.count());
        assertEquals(new BigDecimal("60.00"), estatisticas.sum());
        assertEquals(new BigDecimal("20.00"), estatisticas.avg());
        assertEquals(new BigDecimal("10.00").setScale(2, RoundingMode.HALF_UP), estatisticas.min().setScale(2, RoundingMode.HALF_UP));

        assertEquals(new BigDecimal("30.00"), estatisticas.max());
    }
}
