package pl.kwidzinski.curencyexchanger.model.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class CurrencyDTO {

    @NotNull(message = "Currency symbol should be no null")
    private String symbol;

    @NotNull(message = "Creation date should be no null")
    private LocalDate creationDate;
}
