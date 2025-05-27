package cz.cvut.fel.nss.gardeningrental.branchservice.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}