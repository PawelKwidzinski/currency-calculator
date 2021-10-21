package pl.kwidzinski.curencyexchanger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kwidzinski.curencyexchanger.exceptions.definition.EntityFoundException;
import pl.kwidzinski.curencyexchanger.model.CurrencySymbol;
import pl.kwidzinski.curencyexchanger.model.dto.CurrencySymbolDTO;
import pl.kwidzinski.curencyexchanger.repository.CurrencySymbolRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencySymbolService {

    private final CurrencySymbolRepository currencySymbolRepository;

    public List<CurrencySymbol> findAllCurrenciesSymbols() {
        return currencySymbolRepository.findAll();
    }

    public void saveCurrencySymbol(final CurrencySymbolDTO dto) {
        if (currencySymbolRepository.existsBySymbol(dto.getSymbol())) {
            throw new EntityFoundException(CurrencySymbol.class, "symbol = " + dto.getSymbol());
        }

        CurrencySymbol currencySymbol = new CurrencySymbol();
        currencySymbol.setSymbol(dto.getSymbol());
        currencySymbol.setName(dto.getName());

        currencySymbolRepository.save(currencySymbol);
    }
}

