package com.myapp.itau_challenge.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transacao {

	@Getter @Setter private BigDecimal valor;
	@Getter @Setter private OffsetDateTime dataHora;
	
}
