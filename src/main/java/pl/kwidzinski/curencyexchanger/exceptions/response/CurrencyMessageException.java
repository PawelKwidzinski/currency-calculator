package pl.kwidzinski.curencyexchanger.exceptions.response;


import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class CurrencyMessageException {

    private final String message;
    private final HttpStatus httpStatus;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime localDateTime;

    public CurrencyMessageException(String message, HttpStatus httpStatus, LocalDateTime localDateTime) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.localDateTime = localDateTime;
    }
}
