package com.compass.uol.customer.mscustomer.validacao;

import com.compass.uol.customer.mscustomer.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ErroValidacaoHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroFormDto> handler(MethodArgumentNotValidException exception) {

        List<ErroFormDto> dto = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroFormDto erro = new ErroFormDto(e.getField(), mensagem);
            dto.add(erro);
        });

        return dto;


    }

//    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(InvalidFormatException.class)
//    public List<ErroFormDto> handle(MethodArgumentNotValidException exception) {
//
//        List<ErroFormDto> dto = new ArrayList<>();
//        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
//
//        fieldErrors.forEach(e -> {
//            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
//            ErroFormDto erro = new ErroFormDto(e.getField(), mensagem);
//            dto.add(erro);
//        });
//
//        return dto;
//
//    }

    //valida o problema do id
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ErrorMessage(

                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getLocalizedMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase());
                //request.getDescription(false)); mostra a uri no erro
    }

    @ResponseStatus (HttpStatus.BAD_REQUEST)
    @ExceptionHandler (HttpMessageNotReadableException.class)
    public ErrorMessage httpMessageNotReadableException (HttpMessageNotReadableException ex){
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getLocalizedMessage(),
                ex.getMessage());

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler (EntityExistsException.class)
    public ErrorMessage entityExistsException (EntityExistsException ex){
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase());
    }
}
