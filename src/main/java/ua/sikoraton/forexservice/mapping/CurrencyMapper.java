package ua.sikoraton.forexservice.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.sikoraton.forexservice.dto.CurrencyDto;
import ua.sikoraton.forexservice.dto.imports.CurrencyApiRateDto;
import ua.sikoraton.forexservice.persistence.entity.Currency;

import java.util.Collection;
import java.util.List;

@Mapper
public interface CurrencyMapper {
    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);
    CurrencyDto entityToDto (Currency currency);
    List<CurrencyDto> entityToDto (Collection<Currency> currency);
    Currency dtoToEntity (CurrencyDto currencyDto);
    List<Currency> dtoToEntity (Collection<CurrencyDto> currencyDto);
    @Mapping(source = "code", target = "code")
    @Mapping(target = "name", ignore = true)
    CurrencyDto importToDto(CurrencyApiRateDto currencyApiRateDto);
}
