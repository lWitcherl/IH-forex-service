package ua.sikoraton.forexservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ExchangeRateDto {
    private Long id;
    private CurrencyDto from;
    private CurrencyDto to;
    private BigDecimal rate;
    private String serviceName;
    private LocalDate date;
    private LocalDateTime dateFromImport;

}
