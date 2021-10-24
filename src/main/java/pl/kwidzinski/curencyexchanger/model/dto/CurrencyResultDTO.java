package pl.kwidzinski.curencyexchanger.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.kwidzinski.curencyexchanger.model.Currency;
import pl.kwidzinski.curencyexchanger.model.CurrencyRate;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CurrencyResultDTO {
    private LocalDate date;
    private String from;
    private String to;
    private String result;

    public static CurrencyResultDTO buildCalculationDTO(Currency currency, CurrencyRate rate, int quantity, double result) {
        return CurrencyResultDTO.builder()
                .date(currency.getDate())
                .from(currency.getSymbol())
                .to(String.format("%s: %,.4f", rate.getSymbol(),rate.getRate()))
                .result(String.format("%d %s to %s -> %,.2f", quantity, currency.getSymbol(), rate.getSymbol(), result))
                .build();
    }
}
