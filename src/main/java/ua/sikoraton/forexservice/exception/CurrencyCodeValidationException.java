package ua.sikoraton.forexservice.exception;

public class CurrencyCodeValidationException extends RuntimeException {
    private static final String MESSAGE = "Code validation error. ";
    public CurrencyCodeValidationException(String message) {
        super(MESSAGE.concat(message).concat(" doesn't exist"), new Throwable(MESSAGE));
    }
}
