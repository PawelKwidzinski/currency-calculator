package pl.kwidzinski.curencyexchanger.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
public class CurrencyDTO {

    @NotNull(message = "Quantity for currency should be not null")
    @Positive(message = "Quantity parameter should be a positive digit")
    private Integer quantity;

    @NotNull(message = "Currency symbol should be not null")
    private String symbol;

    @NotNull(message = "Creation date should be not null")
    private LocalDate creationDate;
}
