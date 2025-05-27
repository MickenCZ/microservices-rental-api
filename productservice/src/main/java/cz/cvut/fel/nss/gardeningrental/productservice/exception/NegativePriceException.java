package cz.cvut.fel.nss.gardeningrental.productservice.exception;

public class NegativePriceException extends RuntimeException {

    public NegativePriceException(String message) {
        super(message);
    }
}
