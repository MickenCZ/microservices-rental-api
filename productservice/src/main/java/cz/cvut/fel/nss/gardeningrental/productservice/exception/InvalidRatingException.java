package cz.cvut.fel.nss.gardeningrental.productservice.exception;

public class InvalidRatingException extends RuntimeException {
    public InvalidRatingException(String message) {
        super(message);
    }
}
