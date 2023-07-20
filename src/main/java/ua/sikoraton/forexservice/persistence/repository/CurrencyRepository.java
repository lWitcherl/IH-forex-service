package ua.sikoraton.forexservice.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.sikoraton.forexservice.persistence.entity.Currency;

import java.util.Set;

public interface CurrencyRepository extends JpaRepository<Currency, String> {
    @Query(value = "SELECT code from Currency")
    Set<String> getAllCode();
}
