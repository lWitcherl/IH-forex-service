package ua.sikoraton.forexservice.dto.imports;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CurrencyApiMetaDto {
    @JsonProperty("last_updated_at")
    private LocalDateTime date;
}
