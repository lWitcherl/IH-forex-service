package ua.sikoraton.forexservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ExchangeRateResponseDto {
    private String from;
    private String to;
    private BigDecimal rate;
    private String serviceName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
