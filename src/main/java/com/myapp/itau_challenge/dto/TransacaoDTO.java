package com.myapp.itau_challenge.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransacaoDTO(BigDecimal valor, OffsetDateTime dataHora) {

}
