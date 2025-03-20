package com.myapp.itau_challenge.dto;

import java.math.BigDecimal;

public record EstatisticaDTO(long count, BigDecimal sum, BigDecimal avg, BigDecimal min, BigDecimal max) {

}
