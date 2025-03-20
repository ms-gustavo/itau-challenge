package com.myapp.itau_challenge.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.itau_challenge.dto.EstatisticaDTO;
import com.myapp.itau_challenge.dto.TransacaoDTO;
import com.myapp.itau_challenge.model.Transacao;
import com.myapp.itau_challenge.services.TransacaoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TransacaoController {

	private final TransacaoService transacaoService;
	
	@PostMapping("/transacao")
	public ResponseEntity<Void> criarTransacao(@Valid @RequestBody TransacaoDTO transacaoDTO){
		Transacao transacao = new Transacao(transacaoDTO.valor(), transacaoDTO.dataHora());
		transacaoService.adicionarTransaction(transacao);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping("/transacao")
	public ResponseEntity<Void> deletarTransacoes(){
		transacaoService.limparTransacoes();
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/estatistica")
	public ResponseEntity<EstatisticaDTO> obterEstatisticas(){
		EstatisticaDTO transacoes = transacaoService.calcularEstatisticas();
		return ResponseEntity.status(HttpStatus.OK).body(transacoes);
	}
}
