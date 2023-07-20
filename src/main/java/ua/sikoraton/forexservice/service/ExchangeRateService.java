package ua.sikoraton.forexservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import ua.sikoraton.forexservice.dto.ExchangeRateDto;
import ua.sikoraton.forexservice.dto.ExchangeRateResponseDto;

import java.util.Collection;
import java.util.List;

public interface ExchangeRateService {
    List<ExchangeRateDto> saveAll(Collection<ExchangeRateDto> exchangeRateDtos);

    Page<ExchangeRateResponseDto> getCurrentRate(String from, Collection<String> to, Pageable pageable);

    Boolean dailyLowerUpdate();

    Page<ExchangeRateResponseDto> getByParams(MultiValueMap<String, String> params, Pageable pageable);
}
