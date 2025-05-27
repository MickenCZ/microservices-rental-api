package cz.cvut.fel.ear.pujcovna.exception;

public class ForbiddenDateException extends RuntimeException {
    public ForbiddenDateException(String message) {
        super(message);
    }
}
