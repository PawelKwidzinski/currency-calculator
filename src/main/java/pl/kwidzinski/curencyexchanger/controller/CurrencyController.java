package pl.kwidzinski.curencyexchanger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kwidzinski.curencyexchanger.model.Currency;
import pl.kwidzinski.curencyexchanger.model.dto.CurrencyCalculationDTO;
import pl.kwidzinski.curencyexchanger.model.dto.CurrencyDTO;
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

    @GetMapping("/find")
    public ResponseEntity<Currency> findCurrencyByDate(@RequestParam String date, @RequestParam String symbol) {
        return currencyService.findByDateAndSymbol(date, symbol)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/calculate")
    public ResponseEntity<CurrencyCalculationDTO> getCalculateCurrency(@RequestParam String date,
                                                                       @RequestParam String baseSymbol,
                                                                       @RequestParam int quantity,
                                                                       @RequestParam String calculateSymbol) {
        return ResponseEntity.ok(currencyService.calculateCurrency(date, baseSymbol, quantity, calculateSymbol));
    }
}
