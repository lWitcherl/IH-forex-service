package ua.sikoraton.forexservice.dto.imports;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
public class ApiLayerImportDto {
    private boolean success;
    private int timestamp;
    private String base;
    private LocalDate date;
    private Map<String, BigDecimal> rates;
}
