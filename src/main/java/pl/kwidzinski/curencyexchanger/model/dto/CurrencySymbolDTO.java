package pl.kwidzinski.curencyexchanger.model.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class CurrencySymbolDTO {

    @NotBlank(message = "Currency symbol should be not blank")
    @Size(min = 3, max = 3,message = "Symbol should have 3 capital letters")
    private String symbol;

    @NotBlank(message = "Currency name should be not blank")
    @Size(min = 3, message = "Currency name is to short, should be more than 3 letters")
    private String name;
}
