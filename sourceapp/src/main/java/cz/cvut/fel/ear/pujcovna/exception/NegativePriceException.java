package cz.cvut.fel.ear.pujcovna.exception;

public class NegativePriceException extends RuntimeException {

    public NegativePriceException(String message) {
        super(message);
    }
}
