package ua.sikoraton.forexservice.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.sikoraton.forexservice.dto.CurrencyDto;
import ua.sikoraton.forexservice.dto.ExchangeRateDto;
import ua.sikoraton.forexservice.dto.ExchangeRateResponseDto;
import ua.sikoraton.forexservice.dto.imports.ApiLayerImportDto;
import ua.sikoraton.forexservice.dto.imports.CurrencyApiImportDto;
import ua.sikoraton.forexservice.dto.imports.CurrencyApiRateDto;
import ua.sikoraton.forexservice.persistence.entity.ExchangeRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Mapper
public interface ExchangeRateMapper {
    ExchangeRateMapper INSTANCE = Mappers.getMapper(ExchangeRateMapper.class);

    ExchangeRateDto entityToDto(ExchangeRate exchangeRate);

    List<ExchangeRateDto> entityToDto(Collection<ExchangeRate> exchangeRates);

    @Mapping(expression = "java(exchangeRates.getFrom().getCode())", target = "from")
    @Mapping(expression = "java(exchangeRates.getTo().getCode())", target = "to")
    ExchangeRateResponseDto entityToRequestDto(ExchangeRate exchangeRates);

    List<ExchangeRateResponseDto> entityToRequestDto(Collection<ExchangeRate> exchangeRates);

    ExchangeRate dtoToEntity(ExchangeRateDto exchangeRateDto);

    List<ExchangeRate> dtoToEntity(Collection<ExchangeRateDto> exchangeRateDtos);

    default List<ExchangeRateDto> importsToDto(String base, String service, CurrencyApiImportDto importDto) {
        LocalDate date = LocalDate.now(ZoneId.of("UTC"));
        List<ExchangeRateDto> dtoList = new LinkedList<>();
        CurrencyDto baseCurrency = CurrencyDto.builder().code(base).build();
        for (Map.Entry<String, CurrencyApiRateDto> e : importDto.getData().entrySet())
            dtoList.add(
                    ExchangeRateDto.builder()
                            .from(baseCurrency)
                            .to(CurrencyMapper.INSTANCE.importToDto(e.getValue()))
                            .rate(e.getValue().getValue())
                            .serviceName(service)
                            .date(date)
                            .dateFromImport(importDto.getDate().getDate())
                            .build());
        return dtoList;
    }

    default List<ExchangeRateDto> importsToDto(String service, ApiLayerImportDto importDto) {
        LocalDate date = LocalDate.now(ZoneId.of("UTC"));
        List<ExchangeRateDto> dtoList = new LinkedList<>();
        CurrencyDto baseCurrency = CurrencyDto.builder().code(importDto.getBase()).build();
        for (Map.Entry<String, BigDecimal> e : importDto.getRates().entrySet())
            dtoList.add(
                    ExchangeRateDto.builder()
                            .from(baseCurrency)
                            .to(CurrencyDto.builder().code(e.getKey()).build())
                            .rate(e.getValue())
                            .serviceName(service)
                            .date(date)
                            .dateFromImport(importDto.getDate().atStartOfDay())
                            .build());
        return dtoList;
    }
}
