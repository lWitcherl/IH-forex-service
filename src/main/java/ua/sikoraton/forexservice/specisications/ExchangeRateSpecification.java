package ua.sikoraton.forexservice.specisications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import ua.sikoraton.forexservice.persistence.entity.ExchangeRate;

import java.math.BigDecimal;
import java.time.LocalDate;

import static ua.sikoraton.forexservice.utils.constant.ConstantForSpecification.CODE;
import static ua.sikoraton.forexservice.utils.constant.ConstantForSpecification.DATE;
import static ua.sikoraton.forexservice.utils.constant.ConstantForSpecification.JOIN_CURRENCY_FROM;
import static ua.sikoraton.forexservice.utils.constant.ConstantForSpecification.JOIN_CURRENCY_TO;
import static ua.sikoraton.forexservice.utils.constant.ConstantForSpecification.RATE;
import static ua.sikoraton.forexservice.utils.constant.ConstantForSpecification.SERVICE;
import static ua.sikoraton.forexservice.utils.constant.RequestParamConstant.PARAM_DATE_END;
import static ua.sikoraton.forexservice.utils.constant.RequestParamConstant.PARAM_DATE_START;
import static ua.sikoraton.forexservice.utils.constant.RequestParamConstant.PARAM_FROM;
import static ua.sikoraton.forexservice.utils.constant.RequestParamConstant.PARAM_RATE_LESS;
import static ua.sikoraton.forexservice.utils.constant.RequestParamConstant.PARAM_RATE_MORE;
import static ua.sikoraton.forexservice.utils.constant.RequestParamConstant.PARAM_SERVICE;
import static ua.sikoraton.forexservice.utils.constant.RequestParamConstant.PARAM_TO;

public class ExchangeRateSpecification implements Specification<ExchangeRate> {
    private final SearchCriteria criteria;

    public ExchangeRateSpecification(SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<ExchangeRate> root, CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {
        return switch (criteria.getKey()) {
            case PARAM_DATE_START ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get(DATE), LocalDate.parse(criteria.getValue()));
            case PARAM_DATE_END ->
                    criteriaBuilder.lessThanOrEqualTo(root.get(DATE), LocalDate.parse(criteria.getValue()));
            case PARAM_FROM -> criteriaBuilder.equal(root.join(JOIN_CURRENCY_FROM).get(CODE), criteria.getValue());
            case PARAM_TO -> root.join(JOIN_CURRENCY_TO).get(CODE).in(criteria.getList());
            case PARAM_RATE_MORE ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get(RATE), new BigDecimal(criteria.getValue()));
            case PARAM_RATE_LESS ->
                    criteriaBuilder.lessThanOrEqualTo(root.get(RATE), new BigDecimal(criteria.getValue()));
            case SERVICE -> criteriaBuilder.equal(root.get(PARAM_SERVICE), criteria.getValue());
            default -> null;
        };
    }
}
