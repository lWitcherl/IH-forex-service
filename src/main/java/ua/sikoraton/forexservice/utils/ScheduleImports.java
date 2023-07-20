package ua.sikoraton.forexservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.sikoraton.forexservice.imports.CurrencyImport;
import ua.sikoraton.forexservice.service.ExchangeRateService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleImports {
    private final List<CurrencyImport> imports;
    private final ExchangeRateService exchangeRateService;

    @Scheduled(cron = "${import-service.import-crone}", zone = "UTC")
    void dailyImport(){
        imports.forEach(CurrencyImport::daily);
        exchangeRateService.dailyLowerUpdate();
    }
}
