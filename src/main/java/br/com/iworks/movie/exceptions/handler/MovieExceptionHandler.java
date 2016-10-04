package br.com.iworks.movie.exceptions.handler;

import br.com.iworks.movie.exceptions.ConflictException;
import br.com.iworks.movie.exceptions.ListNotFoundException;
import br.com.iworks.movie.exceptions.MovieException;
import br.com.iworks.movie.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class MovieExceptionHandler {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MovieException.class)
    @ResponseBody
    public MessageError handleException(MovieException ex) {
        log.error("Business error: {}", ExceptionUtils.getMessage(ex));
        return new MessageError(ex.getLocalizedMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public MessageError handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Validation error: {}", ExceptionUtils.getMessage(ex));
        return new MessageError(ex.getLocalizedMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public MessageError handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ExceptionUtils.getMessage(ex));
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<String> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
        String error;
        for (FieldError fieldError : fieldErrors) {
            error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
            errors.add(error);
        }
        return new MessageError(errors);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    @ResponseBody
    public MessageError handleDuplicateKeyException(ConflictException ex) {
        log.error("Duplicate key error: {}", ExceptionUtils.getMessage(ex));
        return new MessageError(ex.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public MessageError handleExceptionInternal(Exception ex) {
        log.error("Exception not expected: {}", ExceptionUtils.getMessage(ex), ex);
        return new MessageError(ex.getLocalizedMessage());
    }

    @ExceptionHandler(ListNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HttpEntity<MessageError> handlerListNotFoundException(ListNotFoundException ex) {
        log.error("No Content: {}", ExceptionUtils.getMessage(ex));
        return this.getMessageErrorHttpEntity(ex, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpEntity<MessageError> handlerResourceNotFoundException(final ResourceNotFoundException ex) {
        log.error("Resource Not Found: {}", ExceptionUtils.getMessage(ex));
        return this.getMessageErrorHttpEntity(ex, HttpStatus.NOT_FOUND);
    }

    private HttpEntity<MessageError> getMessageErrorHttpEntity(MovieException ex, HttpStatus noContent) {
        MessageError messageError = new MessageError(ExceptionUtils.getMessage(ex));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>(messageError, responseHeaders, noContent);
    }
}