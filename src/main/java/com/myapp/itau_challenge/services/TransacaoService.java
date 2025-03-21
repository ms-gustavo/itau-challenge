package com.myapp.itau_challenge.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.myapp.itau_challenge.dto.EstatisticaDTO;
import com.myapp.itau_challenge.exceptions.TransacaoInvalidaException;
import com.myapp.itau_challenge.interfaces.TransacaoInterface;
import com.myapp.itau_challenge.model.Transacao;

@Service
public class TransacaoService implements TransacaoInterface {
	private static final Logger logger = LoggerFactory.getLogger(TransacaoService.class);
	private final List<Transacao> transacoes = new ArrayList<>();

	public void adicionarTransacao(Transacao transacao) {
		logger.info("Recebendo nova transação: {}", transacao);
		if (transacao.getValor().compareTo(BigDecimal.ZERO) < 0) {
			logger.warn("Transação inválida: valor negativo");
			throw new TransacaoInvalidaException("O valor da transação não pode ser negativo.");
		}
		if (transacao.getDataHora().isAfter(OffsetDateTime.now())) {
			logger.warn("Transação inválida: data no futuro");
			throw new TransacaoInvalidaException("A data da transação não pode estar no futuro.");
		}
		transacoes.add(transacao);
		logger.info("Transação adicionada com sucesso.");
	}

	public void limparTransacoes() {
		logger.info("Limpando todas as transações...");
		transacoes.clear();
		logger.info("Todas as transações foram removidas.");
	}

	public List<Transacao> obterUltimos60Segundos() {
		logger.info("Obtendo lista de transações nos últimos 60 segundos");
		OffsetDateTime limite = OffsetDateTime.now().minusSeconds(60);
		return transacoes.stream().filter(t -> t.getDataHora().isAfter(limite)).collect(Collectors.toList());
	}

	public EstatisticaDTO calcularEstatisticas() {
		logger.info("Calculando estatísticas nos últimos 60 segundos");
		List<Transacao> ultimasTransacoes = obterUltimos60Segundos();
		if (ultimasTransacoes.isEmpty()) {
			logger.info("Nenhuma transação encontrada nos últimos 60 segundos.");
			return new EstatisticaDTO(0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
		}

		BigDecimal sum = ultimasTransacoes.stream().map(Transacao::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal min = ultimasTransacoes.stream().map(Transacao::getValor).min(BigDecimal::compareTo)
				.orElse(BigDecimal.ZERO);
		BigDecimal max = ultimasTransacoes.stream().map(Transacao::getValor).max(BigDecimal::compareTo)
				.orElse(BigDecimal.ZERO);
		BigDecimal avg = sum.divide(BigDecimal.valueOf(ultimasTransacoes.size()), 2, RoundingMode.HALF_UP);
		logger.info("Estatísticas calculadas: count={}, sum={}, avg={}, min={}, max={}", ultimasTransacoes.size(), sum,
				avg, min, max);
		return new EstatisticaDTO(ultimasTransacoes.size(), sum, avg, min, max);
	}
}
