package ua.sikoraton.forexservice.imports;


import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ua.sikoraton.forexservice.dto.ExchangeRateDto;
import ua.sikoraton.forexservice.dto.imports.CurrencyApiImportDto;
import ua.sikoraton.forexservice.mapping.ExchangeRateMapper;
import ua.sikoraton.forexservice.service.CurrencyService;
import ua.sikoraton.forexservice.service.ExchangeRateService;


import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

@Component
@ConfigurationProperties(prefix = "import-service.currency")
@Getter @Setter
public class CurrencyApiImport implements CurrencyImport{
    private static final String LATEST_URL = "latest";
    private static final String HEADER_KEY = "apikey";
    private static final String BASE_CURRENCY = "base_currency";
    private static final String BASE_CURRENCY_PARAM = "{base_currency}";
    private static final String CURRENCIES = "currencies";
    private static final String CURRENCIES_PARAM = "{currencies}";
    private String url;
    private String name;
    private String key;
    private HttpHeaders headers = new HttpHeaders();

    private RestTemplate restTemplate;
    private CurrencyService currencyService;
    private ExchangeRateService exchangeRateService;

    @Autowired
    public CurrencyApiImport(RestTemplate restTemplate,
                             CurrencyService currencyService,
                             ExchangeRateService exchangeRateService) {
        this.restTemplate = restTemplate;
        this.currencyService = currencyService;
        this.exchangeRateService = exchangeRateService;
    }

    @PostConstruct
    void init(){
        headers.add(HEADER_KEY, key);
        headers.add(HttpHeaders.USER_AGENT, "Application");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void daily() {
        Set<String> actualCurrencies = currencyService.getAllCurrencyCode();
        HttpEntity<Object> response = new HttpEntity<>(headers);
        String url = UriComponentsBuilder.fromHttpUrl(getUrl()).path(LATEST_URL)
                                        .queryParam(CURRENCIES, CURRENCIES_PARAM)
                                        .queryParam(BASE_CURRENCY, BASE_CURRENCY_PARAM)
                                        .encode()
                                        .toUriString();
        Map<String, String> params = new HashMap<>();
        List<ExchangeRateDto> exchangeRateDtos = new LinkedList<>();
        for(String base : actualCurrencies) {
            StringJoiner stringJoiner = new StringJoiner(",");

            for (String curForJoin : actualCurrencies)
                if (!Objects.equals(base, curForJoin))
                    stringJoiner.add(curForJoin);

            params.put(BASE_CURRENCY, base);
            params.put(CURRENCIES, stringJoiner.toString());
            CurrencyApiImportDto responseEntity=
                    restTemplate.exchange(url,
                            HttpMethod.GET,
                            response,
                            CurrencyApiImportDto.class,
                            params).getBody();

            assert responseEntity != null : "Empty entity in CurrencyAPi import";
            exchangeRateDtos.addAll(ExchangeRateMapper.INSTANCE.importsToDto(base, name, responseEntity));
        }
        exchangeRateService.saveAll(exchangeRateDtos);
    }
}
