package pl.kwidzinski.curencyexchanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kwidzinski.curencyexchanger.model.CurrencySymbol;

import java.util.Optional;

@Repository
public interface CurrencySymbolRepository extends JpaRepository<CurrencySymbol, Long> {
    boolean existsBySymbol(String symbol);
    Optional<CurrencySymbol>findByName(String name);
    Optional<CurrencySymbol>findBySymbol(String symbol);

}
