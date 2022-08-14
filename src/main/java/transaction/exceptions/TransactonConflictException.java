package transaction.exceptions;

public class TransactonConflictException extends TransactionException{

    public TransactonConflictException(String message, Object ...args) {
        super(String.format(message, args));
    }

}
