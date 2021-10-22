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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kwidzinski.curencyexchanger.model.CurrencyRate;
import pl.kwidzinski.curencyexchanger.model.dto.CurrencyRateDTO;
import pl.kwidzinski.curencyexchanger.service.CurrencyRateService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/currencies/rates")
@RequiredArgsConstructor
public class CurrencyRateController {
    private final CurrencyRateService currencyRateService;

    @GetMapping
    public List<CurrencyRate> getAllRates() {
        return currencyRateService.findAll();
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addCurrencyRate(@RequestParam Long currencyId, @RequestBody @Valid CurrencyRateDTO dto) {
        currencyRateService.addRate(currencyId, dto);
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateCurrencyRate(@PathVariable Long id, @RequestBody @Valid CurrencyRateDTO dto) {
        currencyRateService.updateRate(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCurrencyRate(@PathVariable Long id) {
        currencyRateService.deleteRate(id);
    }
}
