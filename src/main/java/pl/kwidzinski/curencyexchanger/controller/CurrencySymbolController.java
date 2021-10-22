package pl.kwidzinski.curencyexchanger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addCurrencySymbol(@RequestBody @Valid CurrencySymbolDTO dto) {
        currencySymbolService.saveCurrencySymbol(dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addCurrencySymbol(@PathVariable Long id, @RequestBody @Valid CurrencySymbolDTO dto) {
        currencySymbolService.updateCurrencySymbol(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addCurrencySymbol(@PathVariable Long id) {
        currencySymbolService.deleteCurrencySymbol(id);
    }
}
