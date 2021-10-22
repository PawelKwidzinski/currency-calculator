package pl.kwidzinski.curencyexchanger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kwidzinski.curencyexchanger.model.dto.CurrencyRateDTO;
import pl.kwidzinski.curencyexchanger.service.CurrencyRateService;

import javax.validation.Valid;

@RestController
@RequestMapping("/currencies/rates")
@RequiredArgsConstructor
public class CurrencyRateController {
    private final CurrencyRateService currencyRateService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addCurrencyRate(@RequestBody @Valid CurrencyRateDTO dto) {
        currencyRateService.addRate(dto);
    }
}
