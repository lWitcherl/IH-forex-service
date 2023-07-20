package ua.sikoraton.forexservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.sikoraton.forexservice.dto.CurrencyDto;

import java.util.Collection;
import java.util.Set;

public interface CurrencyService {
    Set<String> getAllCurrencyCode();
    Page<CurrencyDto> getAllCurrencies(Pageable pageable);
    Boolean validateCode(String from, Collection<String> to);
}
