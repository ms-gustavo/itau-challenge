package com.myapp.itau_challenge.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transacao {

	private BigDecimal valor;
	private OffsetDateTime dataHora;
}
