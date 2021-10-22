package pl.kwidzinski.curencyexchanger.model.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
public class CurrencyUpdateDTO {

    @NotNull(message = "Quantity for currency should be not null")
    @Positive(message = "Quantity parameter should be a positive digit")
    private Integer quantity;

    @NotNull(message = "Main currency symbol should be not null")
    private String mainSymbol;

    @NotNull(message = "Currency rate symbol should be not null")
    private String rateSymbol;

    @NotNull(message = "Currency rate should be not null")
    private Double rate;

    @NotNull(message = "Date should be not null")
    private LocalDate date;
}
