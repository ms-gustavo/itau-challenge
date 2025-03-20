package com.myapp.itau_challenge.services;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.myapp.itau_challenge.model.Transacao;

@Service
public class TransacaoService {
	private final List<Transacao> transacoes = new ArrayList<>();
	
	public boolean adicionarTransaction(Transacao transacao) {
		if(transacao.getValor().compareTo(BigDecimal.ZERO) < 0 || transacao.getDataHora().isAfter(OffsetDateTime.now())) return false;
		
		transacoes.add(transacao);
		return true;
	}
	
	public void limparTransacoes() {
		transacoes.clear();
	}
	
	public List<Transacao> obterUltimos60Segundos(){
		OffsetDateTime limite = OffsetDateTime.now().minusSeconds(60);
		return transacoes.stream()
				.filter(t -> t.getDataHora().isAfter(limite))
				.collect(Collectors.toList());
	}
}
