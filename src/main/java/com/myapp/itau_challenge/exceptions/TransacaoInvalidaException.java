package com.myapp.itau_challenge.exceptions;

public class TransacaoInvalidaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TransacaoInvalidaException(String message) {
		super(message);
	}
}
