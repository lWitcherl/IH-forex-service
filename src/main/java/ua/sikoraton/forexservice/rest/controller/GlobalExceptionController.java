package ua.sikoraton.forexservice.rest.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.sikoraton.forexservice.exception.CurrencyCodeValidationException;
import ua.sikoraton.forexservice.exception.ExceptionInfo;

@ControllerAdvice
public class GlobalExceptionController {
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionController.class);

    @ExceptionHandler
    public ResponseEntity<ExceptionInfo> notFoundException(CurrencyCodeValidationException e){
        ExceptionInfo data = new ExceptionInfo();
        data.setInfo(e.getMessage());
        data.setStatusCode(HttpStatus.BAD_REQUEST.value());
        LOGGER.error(e.getMessage(), e.getCause());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionInfo> unknownException(Exception e){
        ExceptionInfo data = new ExceptionInfo();
        data.setInfo(e.getMessage());
        data.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        LOGGER.error(e.getMessage(), e.getCause());
        return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
