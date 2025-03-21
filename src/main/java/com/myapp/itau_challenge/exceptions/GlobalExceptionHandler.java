package com.myapp.itau_challenge.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(TransacaoInvalidaException.class)
	public ResponseEntity<Map<String, String>> handleTransacaoInvalidaException(TransacaoInvalidaException e) {
		Map<String, String> response = new HashMap<>();
		response.put("erro", e.getMessage());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
	}

	@ExceptionHandler(DadosInvalidosException.class)
	public ResponseEntity<Map<String, String>> handleDadosInvalidosException(DadosInvalidosException e) {
		Map<String, String> response = new HashMap<>();
		response.put("erro", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException e) {
		Map<String, String> response = new HashMap<>();
		response.put("erro", "O corpo da requisição está ausente ou é inválido.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException e) {
		Map<String, String> response = new HashMap<>();
		e.getBindingResult().getFieldErrors()
				.forEach(error -> response.put(error.getField(), error.getDefaultMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

}
