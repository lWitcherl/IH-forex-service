package ua.sikoraton.forexservice.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.sikoraton.forexservice.dto.CurrencyDto;
import ua.sikoraton.forexservice.service.CurrencyService;

import java.util.Set;


@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;
    @GetMapping("/codes")
    public Set<String> getAllCurrenciesCode(){
        return currencyService.getAllCurrencyCode();
    }

    @GetMapping("/all")
    public Page<CurrencyDto> getAllCurrencies(Pageable pageable){
        return currencyService.getAllCurrencies(pageable);
    }
}
