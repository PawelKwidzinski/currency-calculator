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

        Currency currencyUSD1 = addCurrency(1, "Dolar amerykański", "USD", LocalDate.of(2021, 10, 18));
        Currency currencyUSD2 = addCurrency(1, "Dolar amerykański", "USD", LocalDate.of(2021, 10, 19));
        Currency currencyUSD3 = addCurrency(1, "Dolar amerykański", "USD", LocalDate.of(2021, 10, 20));
        Currency currencyEUR1 = addCurrency(1, "Euro", "EUR", LocalDate.of(2021, 10, 18));
        Currency currencyEUR2 = addCurrency(1, "Euro", "EUR", LocalDate.of(2021, 10, 19));
        Currency currencyEUR3 = addCurrency(1, "Euro", "EUR", LocalDate.of(2021, 10, 20));
        Currency currencyGBP1 = addCurrency(1, "Funt szterling", "GBP", LocalDate.of(2021, 10, 18));
        Currency currencyGBP2 = addCurrency(1, "Funt szterling", "GBP", LocalDate.of(2021, 10, 19));
        Currency currencyGBP3 = addCurrency(1, "Funt szterling", "GBP", LocalDate.of(2021, 10, 20));
        Currency currencyCHF1 = addCurrency(1, "Frank szwajcarski", "CHF", LocalDate.of(2021, 10, 18));
        Currency currencyCHF2 = addCurrency(1, "Frank szwajcarski", "CHF", LocalDate.of(2021, 10, 19));
        Currency currencyCHF3 = addCurrency(1, "Frank szwajcarski", "CHF", LocalDate.of(2021, 10, 20));
        Currency currencyPLN1 = addCurrency(1, "Polski złoty", "PLN", LocalDate.of(2021, 10, 18));
        Currency currencyPLN2 = addCurrency(1, "Polski złoty", "PLN", LocalDate.of(2021, 10, 19));
        Currency currencyPLN3 = addCurrency(1, "Polski złoty", "PLN", LocalDate.of(2021, 10, 20));

        currencyRates(currencyUSD1, "EUR", 0.8618, "GBP", 0.7277, "CHF", 0.923, "PLN", 3.9449);
        currencyRates(currencyUSD2, "EUR", 0.8518, "GBP", 0.7477, "CHF", 0.903, "PLN", 3.9049);
        currencyRates(currencyUSD3, "EUR", 0.8718, "GBP", 0.7377, "CHF", 0.893, "PLN", 3.8949);
        currencyRates(currencyEUR1, "USD", 1.1604, "GBP", 0.8444, "CHF", 1.071, "PLN", 4.5775);
        currencyRates(currencyEUR2, "USD", 1.1404, "GBP", 0.8554, "CHF", 1.001, "PLN", 4.5475);
        currencyRates(currencyEUR3, "USD", 1.1504, "GBP", 0.8594, "CHF", 1.091, "PLN", 4.5575);
        currencyRates(currencyGBP1, "EUR", 1.185, "USD", 1.3799, "CHF", 1.2751, "PLN", 5.4289);
        currencyRates(currencyGBP2, "EUR", 1.195, "USD", 1.3899, "CHF", 1.2851, "PLN", 5.5289);
        currencyRates(currencyGBP3, "EUR", 1.175, "USD", 1.3999, "CHF", 1.2951, "PLN", 5.4589);
        currencyRates(currencyCHF1, "EUR", 0.9293, "USD", 1.0821, "GBP", 0.7842, "PLN", 4.2574);
        currencyRates(currencyCHF2, "EUR", 0.9393, "USD", 1.0521, "GBP", 0.7942, "PLN", 4.274);
        currencyRates(currencyCHF3, "EUR", 0.9493, "USD", 1.0721, "GBP", 0.8042, "PLN", 4.2874);
        currencyRates(currencyPLN1, "EUR", 0.2183, "USD", 0.2542, "GBP", 0.1842, "CHF", 0.2349);
        currencyRates(currencyPLN2, "EUR", 0.2283, "USD", 0.2642, "GBP", 0.1942, "CHF", 0.2549);
        currencyRates(currencyPLN3, "EUR", 0.2383, "USD", 0.2742, "GBP", 0.1992, "CHF", 0.2449);
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
