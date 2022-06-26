package transaction.exceptions;

public class TransactionNotFoundException extends TransactionException{
	public TransactionNotFoundException(String message, Object ...args) {
		super(String.format(message, args));
	}

}
