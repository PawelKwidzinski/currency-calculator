package pl.kwidzinski.curencyexchanger.model.dto;

import lombok.Getter;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class CurrencyCalculateDTO {
    @NotNull(message = "Date of currency rate should be not null")
    private String date;
    @NotNull(message = "Base symbol of currency should be not null")
    private String baseSymbol;
    @NotNull(message = "Quantity of currency should be not null")
    @Positive(message = "Quantity parameter should be a positive digit")
    private int quantity;
    @NotNull(message = "Calculate symbol of currency should be not null")
    private String calculateSymbol;
}
