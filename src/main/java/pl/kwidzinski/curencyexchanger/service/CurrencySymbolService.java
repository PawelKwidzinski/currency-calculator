package pl.kwidzinski.curencyexchanger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kwidzinski.curencyexchanger.exceptions.definition.ObjectFoundException;
import pl.kwidzinski.curencyexchanger.exceptions.definition.ObjectNotFoundException;
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
        if (currencySymbolRepository.existsBySymbol(dto.getSymbol().toUpperCase())) {
            throw new ObjectFoundException(CurrencySymbol.class, "symbol = " + dto.getSymbol());
        }

        CurrencySymbol currencySymbol = new CurrencySymbol();
        currencySymbol.setSymbol(dto.getSymbol().toUpperCase());
        currencySymbol.setName(dto.getName());

        currencySymbolRepository.save(currencySymbol);
    }

    public void updateCurrencySymbol(final Long id, final CurrencySymbolDTO dto) {
        final CurrencySymbol currencySymbolFromDB = currencySymbolRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(CurrencySymbol.class, "symbol = " + dto.getSymbol()));

        if (currencySymbolRepository.existsBySymbol(dto.getSymbol())) {
            throw new ObjectFoundException(CurrencySymbol.class, "symbol = " + dto.getSymbol());
        }
        currencySymbolFromDB.setSymbol(dto.getSymbol().toUpperCase());
        currencySymbolFromDB.setName(dto.getName());

        currencySymbolRepository.save(currencySymbolFromDB);
    }

    public void deleteCurrencySymbol(final Long id) {
        if (currencySymbolRepository.existsById(id)) {
            currencySymbolRepository.deleteById(id);
        }
        throw new ObjectNotFoundException(CurrencySymbol.class, "id = " + id);
    }
}

