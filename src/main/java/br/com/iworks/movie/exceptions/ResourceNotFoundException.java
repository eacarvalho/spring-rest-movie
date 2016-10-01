package br.com.iworks.movie.exceptions;

public class ResourceNotFoundException extends MovieException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public int getHttpErrorCode() {
        return 404;
    }
}