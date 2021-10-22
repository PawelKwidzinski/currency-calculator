package pl.kwidzinski.curencyexchanger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kwidzinski.curencyexchanger.model.Currency;
import pl.kwidzinski.curencyexchanger.model.CurrencyRate;
import pl.kwidzinski.curencyexchanger.model.CurrencySymbol;
import pl.kwidzinski.curencyexchanger.model.dto.CurrencyRateDTO;
import pl.kwidzinski.curencyexchanger.repository.CurrencyRateRepository;
import pl.kwidzinski.curencyexchanger.repository.CurrencyRepository;
import pl.kwidzinski.curencyexchanger.repository.CurrencySymbolRepository;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class CurrencyRateService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyRateRepository currencyRateRepository;
    private final CurrencySymbolRepository currencySymbolRepository;

    public void addRate(final CurrencyRateDTO dto) {
        Currency currency = currencyRepository.findById(dto.getCurrencyId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Currency with id: %d not found", dto.getCurrencyId())));
        CurrencySymbol currencySymbol = currencySymbolRepository.findBySymbol(dto.getSymbol().toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Currency symbol: %s not found", dto.getSymbol())));

        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setSymbol(currencySymbol.getSymbol());
        currencyRate.setRate(dto.getValue());
        currencyRate.setCurrency(currency);

        currencyRateRepository.save(currencyRate);
    }
}
