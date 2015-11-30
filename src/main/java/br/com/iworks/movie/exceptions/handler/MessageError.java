package br.com.iworks.movie.exceptions.handler;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class MessageError implements Serializable {

    private static final long serialVersionUID = -4728781351505492203L;
    private List<String> errors;
    private List<String> warning;

    public MessageError() {

    }

    public MessageError(List<String> errors) {
        this.errors = errors;
    }

    public MessageError(String error) {
        this(Collections.singletonList(error));
    }

    public MessageError(String... errors) {
        this(Arrays.asList(errors));
    }
}