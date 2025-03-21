package com.myapp.itau_challenge.interfaces;

import java.util.List;

import com.myapp.itau_challenge.dto.EstatisticaDTO;
import com.myapp.itau_challenge.model.Transacao;

public interface TransacaoInterface {
	void adicionarTransacao(Transacao transacao);
	void limparTransacoes();
	List<Transacao> obterUltimos60Segundos();
	EstatisticaDTO calcularEstatisticas();

}
