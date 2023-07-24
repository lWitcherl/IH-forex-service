package ua.sikoraton.forexservice.dto.imports;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class CurrencyApiImportDto {
    @JsonProperty("meta")
    private CurrencyApiMetaDto date;
    private Map<String, CurrencyApiRateDto> data;
}
