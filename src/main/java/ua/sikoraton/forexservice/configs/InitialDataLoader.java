package ua.sikoraton.forexservice.configs;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ua.sikoraton.forexservice.imports.CurrencyImport;
import ua.sikoraton.forexservice.persistence.entity.Currency;
import ua.sikoraton.forexservice.persistence.repository.CurrencyRepository;
import ua.sikoraton.forexservice.service.ExchangeRateService;

import java.util.LinkedList;
import java.util.List;

/**
 * class for the initial filling of the database
 * **/
@Component
@AllArgsConstructor
public class InitialDataLoader implements ApplicationRunner {
    private final CurrencyRepository currencyRepository;
    private final List<CurrencyImport> currencyImport;
    private final ExchangeRateService exchangeRateService;

    @Override
    public void run(ApplicationArguments args) {
        if (!currencyRepository.findAll().isEmpty()) return;
        List<Currency> initialCurrencyList = new LinkedList<>();
        initialCurrencyList.add(new Currency("USD", "United States dollar"));
        initialCurrencyList.add(new Currency("EUR", "Euro"));
        initialCurrencyList.add(new Currency("UAH", "Ukrainian hryvnia"));
        currencyRepository.saveAll(initialCurrencyList);
        currencyImport.forEach(CurrencyImport::daily);
        exchangeRateService.dailyLowerUpdate();
    }
}
