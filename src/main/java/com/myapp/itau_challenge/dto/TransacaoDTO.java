package com.myapp.itau_challenge.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;

public record TransacaoDTO(
		@NotNull(message = "O campo 'valor' é obrigatório.") BigDecimal valor,
		@NotNull(message = "O campo 'dataHora' é obrigatório.") OffsetDateTime dataHora) {

}
