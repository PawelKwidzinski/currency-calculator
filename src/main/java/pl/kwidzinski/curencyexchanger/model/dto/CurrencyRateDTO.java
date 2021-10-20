package pl.kwidzinski.curencyexchanger.model.dto;

import lombok.Getter;

@Getter
public class CurrencyRateDTO {
    private Long currencyId;
    private String symbol;
    private Double value;
}
