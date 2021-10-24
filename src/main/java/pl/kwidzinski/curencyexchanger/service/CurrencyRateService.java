package pl.kwidzinski.curencyexchanger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kwidzinski.curencyexchanger.exceptions.definition.ObjectFoundException;
import pl.kwidzinski.curencyexchanger.exceptions.definition.ObjectNotFoundException;
import pl.kwidzinski.curencyexchanger.model.Currency;
import pl.kwidzinski.curencyexchanger.model.CurrencyRate;
import pl.kwidzinski.curencyexchanger.model.CurrencySymbol;
import pl.kwidzinski.curencyexchanger.model.dto.CurrencyRateDTO;
import pl.kwidzinski.curencyexchanger.repository.CurrencyRateRepository;
import pl.kwidzinski.curencyexchanger.repository.CurrencyRepository;
import pl.kwidzinski.curencyexchanger.repository.CurrencySymbolRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyRateService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyRateRepository currencyRateRepository;
    private final CurrencySymbolRepository currencySymbolRepository;
    private final CurrencyService currencyService;

    public List<CurrencyRate> findAll() {
        return currencyRateRepository.findAll();
    }

    public void addRate(final Long currencyId, final CurrencyRateDTO dto) {
        Currency currencyFromDB = currencyRepository.findById(currencyId)
                .orElseThrow(() -> new ObjectNotFoundException(Currency.class, "id = " + currencyId));

        CurrencySymbol currencySymbolFromDB = currencySymbolRepository.findBySymbol(dto.getSymbol().toUpperCase())
                .orElseThrow(() -> new ObjectNotFoundException(CurrencySymbol.class, "symbol = " + dto.getSymbol()));

        boolean symbolExistsInCurrencyRates = currencyService.rateSymbolExistsInCurrencyRates(currencyFromDB, currencySymbolFromDB.getSymbol());

        if (!symbolExistsInCurrencyRates) {
            CurrencyRate currencyRate = new CurrencyRate();
            currencyRate.setSymbol(currencySymbolFromDB.getSymbol());
            currencyRate.setRate(dto.getValue());
            currencyRate.setCurrency(currencyFromDB);

            currencyRateRepository.save(currencyRate);
        } else {
            throw new ObjectFoundException(CurrencyRate.class, "symbol = " + currencySymbolFromDB.getSymbol());
        }
    }

    public void updateRate(final Long rateId, final CurrencyRateDTO dto) {
        CurrencyRate currencyRateFromDB = currencyRateRepository.findById(rateId)
                .orElseThrow(() -> new ObjectNotFoundException(CurrencyRate.class, "id = " + rateId));

        CurrencySymbol currencySymbolFromDB = currencySymbolRepository.findBySymbol(dto.getSymbol().toUpperCase())
                .orElseThrow(() -> new ObjectNotFoundException(CurrencySymbol.class, "symbol = " + dto.getSymbol()));

        final Currency currency = currencyRateFromDB.getCurrency();
        if (currency.getSymbol().equals(currencySymbolFromDB.getSymbol())) {
            throw new ObjectFoundException(CurrencySymbol.class, "base symbol  = " + currencySymbolFromDB.getSymbol());
        } else {
            currencyRateFromDB.setSymbol(currencySymbolFromDB.getSymbol());
            currencyRateFromDB.setRate(dto.getValue());

            currencyRateRepository.save(currencyRateFromDB);
        }
    }

    public void deleteRate(final Long id) {
        if (currencyRateRepository.existsById(id)) {
            currencyRateRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException(CurrencyRate.class, "id = " + id);
        }
    }
}
