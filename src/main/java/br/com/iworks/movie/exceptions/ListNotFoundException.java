package br.com.iworks.movie.exceptions;

public class ListNotFoundException extends MovieException {

    public ListNotFoundException(String message) {
        super(message);
    }

    public int getHttpErrorCode() {
        return 204;
    }
}