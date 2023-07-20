package ua.sikoraton.forexservice.persistence.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.sikoraton.forexservice.persistence.entity.ExchangeRate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long>,
                                                    JpaSpecificationExecutor<ExchangeRate> {
    Set<ExchangeRate> findAllByDate(LocalDate date);
    Page<ExchangeRate> findAllByFrom_CodeAndTo_CodeInAndDateAndLowerTrue(String from, Collection<String> to, LocalDate date, Pageable pageable);

    @Modifying
    @Query(value = "update ExchangeRate set lower = true where id in :ids")
    Integer setLowerRate(@Param("ids") List<Long> ids);
}
