package pl.kwidzinski.curencyexchanger.model.dto;

import lombok.Getter;

@Getter
public class CurrencyRateDTO {
    private String symbol;
    private Double value;
}
