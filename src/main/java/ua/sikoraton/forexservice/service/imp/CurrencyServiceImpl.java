package ua.sikoraton.forexservice.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.sikoraton.forexservice.dto.CurrencyDto;
import ua.sikoraton.forexservice.exception.CurrencyCodeValidationException;
import ua.sikoraton.forexservice.mapping.CurrencyMapper;
import ua.sikoraton.forexservice.persistence.entity.Currency;
import ua.sikoraton.forexservice.persistence.repository.CurrencyRepository;
import ua.sikoraton.forexservice.service.CurrencyService;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;
    @Override
    public Set<String> getAllCurrencyCode() {
        return currencyRepository.getAllCode();
    }

    @Override
    public Page<CurrencyDto> getAllCurrencies(Pageable pageable) {
        Page<Currency> entities = currencyRepository.findAll(pageable);
        return entities.map(CurrencyMapper.INSTANCE::entityToDto);
    }

    @Override
    public Boolean validateCode(String from, Collection<String> to) {
        Set<String> code = currencyRepository.getAllCode();
        if(!code.contains(from))
            throw new CurrencyCodeValidationException("From code");
        else if(!code.containsAll(to))
            throw new CurrencyCodeValidationException("Some TO code");
        return Boolean.TRUE;
    }
}
