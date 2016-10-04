package br.com.iworks.movie.exceptions;

public class ConflictException extends MovieException {

    public ConflictException(String message) {
        super(message);
    }

    public int getHttpErrorCode() {
        return 409;
    }
}