package pl.kwidzinski.curencyexchanger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kwidzinski.curencyexchanger.model.CurrencySymbol;
import pl.kwidzinski.curencyexchanger.model.dto.CurrencySymbolDTO;
import pl.kwidzinski.curencyexchanger.service.CurrencySymbolService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/currencies/symbols")
public class CurrencySymbolController {

    private final CurrencySymbolService currencySymbolService;

    @GetMapping
    public List<CurrencySymbol> getCurrenciesSymbols() {
        return currencySymbolService.findAllCurrenciesSymbols();
    }

    @PostMapping
    public void addCurrencySymbol(@RequestBody @Valid CurrencySymbolDTO dto) {
        currencySymbolService.saveCurrencySymbol(dto);
    }
}
