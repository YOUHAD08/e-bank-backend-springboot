package ma.youhad.backend.exceptions;

public class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
