package pl.kwidzinski.curencyexchanger.component;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.kwidzinski.curencyexchanger.model.Currency;
import pl.kwidzinski.curencyexchanger.model.CurrencyRate;
import pl.kwidzinski.curencyexchanger.model.CurrencySymbol;
import pl.kwidzinski.curencyexchanger.repository.CurrencyRateRepository;
import pl.kwidzinski.curencyexchanger.repository.CurrencyRepository;
import pl.kwidzinski.curencyexchanger.repository.CurrencySymbolRepository;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final CurrencyRepository currencyRepository;
    private final CurrencyRateRepository currencyRateRepository;
    private final CurrencySymbolRepository currencySymbolRepository;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        addCurrencySymbol("USD", "Dolar amerykański");
        addCurrencySymbol("EUR", "Euro");
        addCurrencySymbol("PLN", "Polski złoty");
        addCurrencySymbol("CHF", "Frank szwajcarski");
        addCurrencySymbol("GBP", "Funt szterling");

        Currency currencyUSD = addCurrency(1, "Dolar amerykański", "USD", LocalDate.of(2021, 10, 15));
        Currency currencyEUR = addCurrency(1, "Euro", "EUR", LocalDate.of(2021, 10, 15));
        Currency currencyGBP = addCurrency(1, "Funt szterling", "GBP", LocalDate.of(2021, 10, 15));
        Currency currencyCHF = addCurrency(1, "Frank szwajcarski", "CHF", LocalDate.of(2021, 10, 15));
        Currency currencyPLN = addCurrency(1, "Polski złoty", "PLN", LocalDate.of(2021, 10, 15));

        currencyRates(currencyUSD, "EUR", 0.8618, "GBP", 0.7277, "CHF", 0.923, "PLN", 3.9449);
        currencyRates(currencyEUR, "USD", 1.1604, "GBP", 0.8444, "CHF", 1.071, "PLN", 4.5775);
        currencyRates(currencyGBP, "EUR", 1.185, "USD", 1.3799, "CHF", 1.2751, "PLN", 5.4289);
        currencyRates(currencyCHF, "EUR", 0.9293, "USD", 1.0821, "GBP", 0.7842, "PLN", 4.2574);
        currencyRates(currencyPLN, "EUR", 0.2183, "USD", 0.2542, "GBP", 0.1842, "CHF", 0.2349);
    }

    private Currency addCurrency(int quantity, String name, String symbol, LocalDate date) {
        Currency currency = new Currency();
        if (!currencyRepository.existsByDateAndSymbol(date, symbol)) {
            currency.setQuantity(quantity);
            currency.setName(name);
            currency.setSymbol(symbol);
            currency.setDate(date);
        }
        return currencyRepository.save(currency);
    }

    private void currencyRates(Currency cur, String s1, double v1, String s2, double v2, String s3, double v3, String s4, double v4) {
        addCurrencyRate(cur, s1, v1);
        addCurrencyRate(cur, s2, v2);
        addCurrencyRate(cur, s3, v3);
        addCurrencyRate(cur, s4, v4);
    }

    private void addCurrencyRate(Currency currency, String symbol, double value) {
        CurrencyRate rate = new CurrencyRate(symbol, value);
        rate.setCurrency(currency);
        currencyRateRepository.save(rate);
    }

    private void addCurrencySymbol(String symbol, String name) {
        if (!currencySymbolRepository.existsBySymbol(symbol)) {
            CurrencySymbol currencySymbol = new CurrencySymbol();
            currencySymbol.setSymbol(symbol);
            currencySymbol.setName(name);
            currencySymbolRepository.save(currencySymbol);
        }
    }
}
