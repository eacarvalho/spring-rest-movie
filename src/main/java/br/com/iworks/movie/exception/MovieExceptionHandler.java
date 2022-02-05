package br.com.iworks.movie.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MovieExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());

        return new ResponseEntity<>(error, NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    protected ResponseEntity<ErrorResponse> handleArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> details = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> details.add(error.getDefaultMessage()));
        ErrorResponse error = new ErrorResponse("Validation Failed", details);

        return new ResponseEntity<>(error, BAD_REQUEST);
    }
}