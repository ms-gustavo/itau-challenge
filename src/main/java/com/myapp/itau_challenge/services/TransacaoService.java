package com.myapp.itau_challenge.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.myapp.itau_challenge.dto.EstatisticaDTO;
import com.myapp.itau_challenge.exceptions.TransacaoInvalidaException;
import com.myapp.itau_challenge.interfaces.TransacaoInterface;
import com.myapp.itau_challenge.model.Transacao;

@Service
public class TransacaoService implements TransacaoInterface {
	private final List<Transacao> transacoes = new ArrayList<>();

	public void adicionarTransacao(Transacao transacao) {
		if (transacao.getValor().compareTo(BigDecimal.ZERO) < 0) {
			throw new TransacaoInvalidaException("O valor da transação não pode ser negativo.");
		}
		if (transacao.getDataHora().isAfter(OffsetDateTime.now())) {
			throw new TransacaoInvalidaException("A data da transação não pode estar no futuro.");
		}
		transacoes.add(transacao);
	}

	public void limparTransacoes() {
		transacoes.clear();
	}

	public List<Transacao> obterUltimos60Segundos() {
		OffsetDateTime limite = OffsetDateTime.now().minusSeconds(60);
		return transacoes.stream().filter(t -> t.getDataHora().isAfter(limite)).collect(Collectors.toList());
	}

	public EstatisticaDTO calcularEstatisticas() {
		List<Transacao> ultimasTransacoes = obterUltimos60Segundos();

		if (ultimasTransacoes.isEmpty())
			return new EstatisticaDTO(0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
		;

		BigDecimal sum = ultimasTransacoes.stream().map(Transacao::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal min = ultimasTransacoes.stream().map(Transacao::getValor).min(BigDecimal::compareTo)
				.orElse(BigDecimal.ZERO);
		BigDecimal max = ultimasTransacoes.stream().map(Transacao::getValor).max(BigDecimal::compareTo)
				.orElse(BigDecimal.ZERO);
		BigDecimal avg = sum.divide(BigDecimal.valueOf(ultimasTransacoes.size()), 2, RoundingMode.HALF_UP);

		return new EstatisticaDTO(ultimasTransacoes.size(), sum, avg, max, min);
	}
}
