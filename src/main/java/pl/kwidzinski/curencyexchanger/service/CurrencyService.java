package pl.kwidzinski.curencyexchanger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kwidzinski.curencyexchanger.model.Currency;
import pl.kwidzinski.curencyexchanger.model.CurrencyRate;
import pl.kwidzinski.curencyexchanger.model.CurrencySymbol;
import pl.kwidzinski.curencyexchanger.model.dto.CurrencyCalculationDTO;
import pl.kwidzinski.curencyexchanger.model.dto.CurrencyDTO;
import pl.kwidzinski.curencyexchanger.repository.CurrencyRepository;
import pl.kwidzinski.curencyexchanger.repository.CurrencySymbolRepository;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static pl.kwidzinski.curencyexchanger.model.dto.CurrencyCalculationDTO.buildCalculationDTO;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencySymbolRepository currencySymbolRepository;

    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }

    public void save(final CurrencyDTO dto) {
        CurrencySymbol currencySymbol = currencySymbolRepository.findBySymbol(dto.getSymbol().toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Currency symbol %s not found", dto.getSymbol())));

        Currency currency = new Currency();

        currency.setQuantity(dto.getQuantity());
        currency.setSymbol(currencySymbol.getSymbol());
        currency.setName(currencySymbol.getName());
        currency.setDate(dto.getCreationDate());
        currencyRepository.save(currency);
    }

    public CurrencyCalculationDTO calculateCurrency(final String date, final String baseSymbol, final int quantity, final String calculateSymbol) {
        Currency currency = findByDateAndSymbol(date, baseSymbol)
                .orElseThrow(() -> new EntityNotFoundException("Currency not found"));

        CurrencyRate currencyRate = currency.getCurrencyRates()
                .stream()
                .filter(rate -> rate.getSymbol().equals(calculateSymbol))
                .findFirst().orElseThrow(() -> new EntityNotFoundException(
                        String.format("Currency rate %s for %s not found", calculateSymbol, baseSymbol)));

        double result = currency.getQuantity() * quantity * currencyRate.getRate();

        return buildCalculationDTO(currency, currencyRate, quantity, result);
    }

    public Optional<Currency> findByDateAndSymbol(final String date, final String symbol) {
        return currencyRepository.findByDateAndSymbol(LocalDate.parse(date), symbol);
    }
}
