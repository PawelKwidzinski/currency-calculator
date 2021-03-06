package pl.kwidzinski.curencyexchanger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kwidzinski.curencyexchanger.model.Currency;
import pl.kwidzinski.curencyexchanger.model.dto.CurrencyCalculateDTO;
import pl.kwidzinski.curencyexchanger.model.dto.CurrencyResultDTO;
import pl.kwidzinski.curencyexchanger.model.dto.CurrencyDTO;
import pl.kwidzinski.curencyexchanger.model.dto.CurrencyUpdateDTO;
import pl.kwidzinski.curencyexchanger.service.CurrencyService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public List<Currency> getAllCurrencies() {
        return currencyService.getCurrencies();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addCurrency(@RequestBody @Valid CurrencyDTO dto) {
        currencyService.save(dto);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateCurrency(@PathVariable Long id, @Valid @RequestBody CurrencyUpdateDTO updateDTO) {
        currencyService.updateCurrency(id, updateDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCurrency(@PathVariable Long id) {
        currencyService.deleteCurrency(id);
    }

    @GetMapping("/find")
    public ResponseEntity<Currency> findCurrencyByDate(@RequestParam String date, @RequestParam String symbol) {
        return currencyService.findByDateAndSymbol(date, symbol)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/calculate")
    public ResponseEntity<CurrencyResultDTO> getCalculateCurrency(@RequestBody CurrencyCalculateDTO dto) {
        return ResponseEntity.ok(currencyService.calculateCurrency
                (dto.getDate(), dto.getBaseSymbol(), dto.getQuantity(), dto.getCalculateSymbol()));
    }
}
