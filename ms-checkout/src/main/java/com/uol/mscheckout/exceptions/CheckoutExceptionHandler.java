package com.uol.mscheckout.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import feign.FeignException;
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

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class CheckoutExceptionHandler {

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

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public List<ErroFormDto> handle(MethodArgumentNotValidException exception) {

        List<ErroFormDto> dto = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroFormDto erro = new ErroFormDto(e.getField(), mensagem);
            dto.add(erro);
        });

        return dto;

    }

    //valida o problema do id
    @ExceptionHandler(PagamentoNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(PagamentoNotFoundException ex, WebRequest request) {
        return new ErrorMessage(

                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getLocalizedMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase());
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

    @ExceptionHandler(UserNotActiveException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage userNotActive(UserNotActiveException ex, WebRequest request) {
        return new ErrorMessage(

                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler(PagamentoNotActiveException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage paymentNotActive(PagamentoNotActiveException ex, WebRequest request) {
        return new ErrorMessage(

                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler(ProdutoNotActiveException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage produtoNotActive(ProdutoNotActiveException ex, WebRequest request) {
        return new ErrorMessage(

                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage stockNotEnough(EstoqueInsuficienteException ex, WebRequest request) {
        return new ErrorMessage(

                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler(FeignException.class)
    public String handleFeignStatusException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return e.getLocalizedMessage();
    }



}
