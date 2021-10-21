package pl.kwidzinski.curencyexchanger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.kwidzinski.curencyexchanger.exceptions.definition.EntityFoundException;
import pl.kwidzinski.curencyexchanger.exceptions.response.CurrencyMessageException;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {EntityFoundException.class})
    public ResponseEntity<Object> handleCurrencySymbolFound(EntityFoundException ex) {
        HttpStatus badRequestStatus = HttpStatus.FOUND;
        CurrencyMessageException currencyMessageException = new CurrencyMessageException(
                ex.getMessage(),
                badRequestStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(currencyMessageException, badRequestStatus);
    }
}
