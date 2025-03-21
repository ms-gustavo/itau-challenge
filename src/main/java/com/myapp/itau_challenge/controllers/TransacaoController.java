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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "Transações", description = "Gerenciamento de transações e estatísticas")
public class TransacaoController {

	private final TransacaoService transacaoService;

	@PostMapping("/transacao")
	@Operation(summary = "Criar uma nova transação", description = "Adiciona uma nova transação se for válida", responses = {
			@ApiResponse(responseCode = "201", description = "Transação criada com sucesso"),
			@ApiResponse(responseCode = "422", description = "Erro de validação", content = @Content(schema = @Schema(implementation = TransacaoDTO.class))),
			@ApiResponse(responseCode = "400", description = "JSON inválido") })
	public ResponseEntity<Void> criarTransacao(@Valid @RequestBody TransacaoDTO transacaoDTO) {
		Transacao transacao = new Transacao(transacaoDTO.valor(), transacaoDTO.dataHora());
		transacaoService.adicionarTransacao(transacao);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/transacao")
	@Operation(summary = "Deletar todas as transações", description = "Remove todas as transações da memória", responses = {
			@ApiResponse(responseCode = "200", description = "Transações apagadas com sucesso") })

	public ResponseEntity<Void> deletarTransacoes() {
		transacaoService.limparTransacoes();
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/estatistica")
	@Operation(summary = "Obter estatísticas", description = "Retorna as estatísticas das transações nos últimos 60 segundos", responses = {
			@ApiResponse(responseCode = "200", description = "Estatísticas calculadas com sucesso", content = @Content(schema = @Schema(implementation = EstatisticaDTO.class))) })
	public ResponseEntity<EstatisticaDTO> obterEstatisticas() {
		EstatisticaDTO transacoes = transacaoService.calcularEstatisticas();
		return ResponseEntity.status(HttpStatus.OK).body(transacoes);
	}
}
