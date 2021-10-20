package pl.kwidzinski.curencyexchanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kwidzinski.curencyexchanger.model.Currency;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    boolean existsByDateAndSymbol(LocalDate date, String symbol);
    Optional<Currency> findByDateAndSymbol(LocalDate date, String symbol);
}
