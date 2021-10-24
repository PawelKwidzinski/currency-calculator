package pl.kwidzinski.curencyexchanger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kwidzinski.curencyexchanger.exceptions.definition.ObjectFoundException;
import pl.kwidzinski.curencyexchanger.exceptions.definition.ObjectNotFoundException;
import pl.kwidzinski.curencyexchanger.model.Currency;
import pl.kwidzinski.curencyexchanger.model.CurrencyRate;
import pl.kwidzinski.curencyexchanger.model.CurrencySymbol;
import pl.kwidzinski.curencyexchanger.model.dto.CurrencyResultDTO;
import pl.kwidzinski.curencyexchanger.model.dto.CurrencyDTO;
import pl.kwidzinski.curencyexchanger.model.dto.CurrencyUpdateDTO;
import pl.kwidzinski.curencyexchanger.repository.CurrencyRateRepository;
import pl.kwidzinski.curencyexchanger.repository.CurrencyRepository;
import pl.kwidzinski.curencyexchanger.repository.CurrencySymbolRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static pl.kwidzinski.curencyexchanger.model.dto.CurrencyResultDTO.buildCalculationDTO;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyRateRepository currencyRateRepository;
    private final CurrencySymbolRepository currencySymbolRepository;

    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }

    public void save(final CurrencyDTO dto) {
        CurrencySymbol currencySymbol = currencySymbolRepository.findBySymbol(dto.getSymbol().toUpperCase())
                .orElseThrow(() -> new ObjectNotFoundException(CurrencySymbol.class, "symbol = " + dto.getSymbol().toUpperCase()));

        final boolean existsByDateAndSymbol = currencyRepository.existsByDateAndSymbol(dto.getCreationDate(), currencySymbol.getSymbol());

        if (!existsByDateAndSymbol) {
            Currency currency = new Currency();

            currency.setQuantity(dto.getQuantity());
            currency.setSymbol(currencySymbol.getSymbol());
            currency.setName(currencySymbol.getName());
            currency.setDate(dto.getCreationDate());
            currencyRepository.save(currency);
        } else {
            throw new ObjectFoundException(Currency.class, "date = " +dto.getCreationDate(), "symbol = " +currencySymbol.getSymbol());
        }
    }

    public CurrencyResultDTO calculateCurrency(final String date, final String baseSymbol, final int quantity, final String calculateSymbol) {
        Currency currency = findByDateAndSymbol(date, baseSymbol)
                .orElseThrow(() -> new ObjectNotFoundException(Currency.class, date, baseSymbol));

        CurrencyRate currencyRate = currency.getCurrencyRates()
                .stream()
                .filter(rate -> rate.getSymbol().equals(calculateSymbol))
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException(CurrencyRate.class, "from " + baseSymbol, "to = " + calculateSymbol));

        double result = currency.getQuantity() * quantity * currencyRate.getRate();

        return buildCalculationDTO(currency, currencyRate, quantity, result);
    }

    public Optional<Currency> findByDateAndSymbol(final String date, final String symbol) {
        return currencyRepository.findByDateAndSymbol(LocalDate.parse(date), symbol);
    }

    public void updateCurrency(final Long id, final CurrencyUpdateDTO updateDTO) {
        Currency currencyFromDB = currencyRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Currency.class, id));

        CurrencySymbol mainCurrencySymbolFromDB = currencySymbolRepository.findBySymbol(updateDTO.getMainSymbol().toUpperCase())
                .orElseThrow(() -> new ObjectNotFoundException(CurrencySymbol.class, "main symbol = " + updateDTO.getMainSymbol().toUpperCase()));

        CurrencySymbol currencyRateSymbolFromDB = currencySymbolRepository.findBySymbol(updateDTO.getRateSymbol().toUpperCase())
                .orElseThrow(() -> new ObjectNotFoundException(CurrencySymbol.class, "rate symbol = " + updateDTO.getRateSymbol().toUpperCase()));

        if (!rateSymbolExistsInCurrencyRates(currencyFromDB, mainCurrencySymbolFromDB.getSymbol())) {
            currencyFromDB.setDate(updateDTO.getDate());
            currencyFromDB.setSymbol(mainCurrencySymbolFromDB.getSymbol());
            currencyFromDB.setName(mainCurrencySymbolFromDB.getName());
            currencyRepository.save(currencyFromDB);
        } else {
            throw new ObjectFoundException(Currency.class, mainCurrencySymbolFromDB.getSymbol());
        }

        if (rateSymbolExistsInCurrencyRates(currencyFromDB, currencyRateSymbolFromDB.getSymbol())) {
            CurrencyRate rateToEditFromCurrency = getCurrencyRateFromCurrencyRates(currencyFromDB, currencyRateSymbolFromDB);
            Long rateIdToUpdate = rateToEditFromCurrency.getId();

            CurrencyRate currencyRateFromDB = currencyRateRepository.findById(rateIdToUpdate)
                    .orElseThrow(() -> new ObjectNotFoundException(CurrencyRate.class, "id = " + rateIdToUpdate));

            currencyRateFromDB.setSymbol(updateDTO.getRateSymbol());
            currencyRateFromDB.setRate(updateDTO.getRate());

            currencyRateRepository.save(currencyRateFromDB);
        } else {
            throw new ObjectNotFoundException(CurrencyRate.class,
                    "symbol = " + currencyRateSymbolFromDB.getSymbol(), "symbol = " + currencyFromDB.getSymbol());
        }
    }

    public void deleteCurrency(final Long id) {
        Currency currencyToDelete = currencyRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Currency.class, id));

        List<CurrencyRate> currencyRates = currencyToDelete.getCurrencyRates();

        currencyRateRepository.deleteAll(currencyRates);
        currencyRepository.delete(currencyToDelete);
    }

    private CurrencyRate getCurrencyRateFromCurrencyRates(final Currency currencyFromDB, final CurrencySymbol currencyRateSymbolFromDB) {
        return currencyFromDB.getCurrencyRates().stream()
                .filter(rate -> rate.getSymbol().equals(currencyRateSymbolFromDB.getSymbol()))
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException(
                        CurrencyRate.class, "symbol = " + currencyFromDB.getSymbol(), "symbol = " + currencyRateSymbolFromDB.getSymbol()));
    }

    public boolean rateSymbolExistsInCurrencyRates(final Currency currencyFromDB, final String rateSymbolFromDB) {
        return currencyFromDB.getCurrencyRates().stream()
                .map(CurrencyRate::getSymbol)
                .anyMatch(s -> s.equals(rateSymbolFromDB));
    }
}
