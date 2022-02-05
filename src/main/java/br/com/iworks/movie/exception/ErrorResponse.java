package br.com.iworks.movie.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private String message;
    private List<String> details;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }
}