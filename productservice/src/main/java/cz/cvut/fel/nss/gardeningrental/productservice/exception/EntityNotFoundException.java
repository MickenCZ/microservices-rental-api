package cz.cvut.fel.nss.gardeningrental.productservice.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
