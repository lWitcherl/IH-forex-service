package ua.sikoraton.forexservice.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import ua.sikoraton.forexservice.dto.ExchangeRateDto;
import ua.sikoraton.forexservice.dto.ExchangeRateResponseDto;
import ua.sikoraton.forexservice.mapping.ExchangeRateMapper;
import ua.sikoraton.forexservice.persistence.entity.ExchangeRate;
import ua.sikoraton.forexservice.persistence.repository.ExchangeRateRepository;
import ua.sikoraton.forexservice.service.CurrencyService;
import ua.sikoraton.forexservice.service.ExchangeRateService;
import ua.sikoraton.forexservice.utils.ProcessingSpecification;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyService currencyService;

    @Override
    public List<ExchangeRateDto> saveAll(Collection<ExchangeRateDto> exchangeRateDtos) {
        return ExchangeRateMapper.INSTANCE.entityToDto(
                exchangeRateRepository.saveAll(ExchangeRateMapper.INSTANCE.dtoToEntity(exchangeRateDtos)));
    }

    @Override
    public Page<ExchangeRateResponseDto> getCurrentRate(String from, Collection<String> to, Pageable pageable) {
        currencyService.validateCode(from, to);
        Page<ExchangeRate> entities =
                exchangeRateRepository.findAllByFrom_CodeAndTo_CodeInAndDateAndLowerTrue(from,
                                                                                        to,
                                                                                        LocalDate.now(ZoneId.of("UTC")),
                                                                                        pageable);
        return entities.map(ExchangeRateMapper.INSTANCE::entityToRequestDto);
    }

    @Override
    public Boolean dailyLowerUpdate() {
        HashMap<String, ExchangeRateDto> actualRates = new HashMap<>();
        List<ExchangeRateDto> listFromDb = ExchangeRateMapper.INSTANCE.entityToDto(
                exchangeRateRepository.findAllByDate(
                        LocalDate.now(ZoneId.of("UTC"))));
        for (ExchangeRateDto x : listFromDb) {
            String key = x.getFrom().getCode().concat(x.getTo().getCode());
            if (!actualRates.containsKey(key) || actualRates.get(key).getRate().compareTo(x.getRate()) > 0) {
                actualRates.put(key, x);
            }
        }
        Integer saveRows = exchangeRateRepository.setLowerRate(actualRates.values().stream()
                .map(ExchangeRateDto::getId)
                .collect(Collectors.toList()));
        return actualRates.values().size() == saveRows;
    }

    @Override
    public Page<ExchangeRateResponseDto> getByParams(MultiValueMap<String, String> params, Pageable pageable) {
        Page<ExchangeRate> entities;
        if (params.isEmpty()) entities = exchangeRateRepository.findAll(pageable);
        else entities = exchangeRateRepository.findAll(ProcessingSpecification.processing(params), pageable);
        return entities.map(ExchangeRateMapper.INSTANCE::entityToRequestDto);
    }
}
