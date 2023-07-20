package ua.sikoraton.forexservice.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.sikoraton.forexservice.dto.ExchangeRateResponseDto;
import ua.sikoraton.forexservice.service.ExchangeRateService;

import static ua.sikoraton.forexservice.utils.constant.RequestParamConstant.DEFAULT_VALUE_FROM;
import static ua.sikoraton.forexservice.utils.constant.RequestParamConstant.DEFAULT_VALUE_TO;
import static ua.sikoraton.forexservice.utils.constant.RequestParamConstant.PARAM_FROM;
import static ua.sikoraton.forexservice.utils.constant.RequestParamConstant.PARAM_TO;

@RestController
@RequestMapping("/exchangerate")
@RequiredArgsConstructor
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @GetMapping("/latest")
    public Page<ExchangeRateResponseDto> getLatest (@RequestParam MultiValueMap<String, String> params, Pageable pageable){
        if (!params.containsKey(PARAM_FROM))
            params.add(PARAM_FROM, DEFAULT_VALUE_FROM);
        if (!params.containsKey(PARAM_TO))
            params.add(PARAM_TO, DEFAULT_VALUE_TO);
        return exchangeRateService.getCurrentRate(params.getFirst(PARAM_FROM), params.get(PARAM_TO), pageable);
    }

    @GetMapping("/param")
    public Page<ExchangeRateResponseDto> getByParams (@RequestParam MultiValueMap<String, String> params, Pageable pageable){

        return exchangeRateService.getByParams(params, pageable);
    }
}

