package compass.uol.mscatalog.validacao;

import compass.uol.mscatalog.exceptions.CategoryNotActiveException;
import compass.uol.mscatalog.exceptions.CategoryNotFoundException;
import compass.uol.mscatalog.exceptions.ProductNotFoundException;
import compass.uol.mscatalog.exceptions.VariacaoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class CatalogExceptionHandler {


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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    public ErrorMessage categoryNotFoundException(CategoryNotFoundException ex){
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getLocalizedMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorMessage productNotFoundException(ProductNotFoundException ex){
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getLocalizedMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CategoryNotActiveException.class)
    public ErrorMessage categoryNotActiveException(CategoryNotActiveException ex){
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase());
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(VariacaoNotFoundException.class)
    public ErrorMessage variacaoNotFoundException(VariacaoNotFoundException ex){
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getLocalizedMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase());
    }




}

