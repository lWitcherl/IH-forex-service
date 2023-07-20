package ua.sikoraton.forexservice.dto.imports;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyApiRateDto {
    private String code;
    private BigDecimal value;
}
