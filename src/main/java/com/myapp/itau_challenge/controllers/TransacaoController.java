package com.myapp.itau_challenge.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.itau_challenge.dto.TransacaoDTO;
import com.myapp.itau_challenge.model.Transacao;
import com.myapp.itau_challenge.services.TransacaoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
public class TransacaoController {

	private final TransacaoService transacaoService;
	
	@PostMapping
	public ResponseEntity<Void> criarTransacao(@Valid @RequestBody TransacaoDTO transacaoDTO){
		Transacao transacao = new Transacao(transacaoDTO.valor(), transacaoDTO.dataHora());
		Boolean sucesso = transacaoService.adicionarTransaction(transacao);
		
		return sucesso ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deletarTransacoes(){
		transacaoService.limparTransacoes();
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/estatistica")
	public ResponseEntity<List<Transacao>> obterEstatisticas(){
		List<Transacao> transacoes = transacaoService.obterUltimos60Segundos();
		return ResponseEntity.status(HttpStatus.OK).body(transacoes);
	}
}
