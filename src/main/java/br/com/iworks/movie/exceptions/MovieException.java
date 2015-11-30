package br.com.iworks.movie.exceptions;

public class MovieException extends RuntimeException {
    private static final long serialVersionUID = -6299175182809658885L;

    public MovieException() {
    }

    public MovieException(String message) {
        super(message);
    }

    public MovieException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieException(Throwable cause) {
        super(cause);
    }

    public MovieException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}