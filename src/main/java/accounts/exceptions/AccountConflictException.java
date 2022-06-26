package accounts.exceptions;

public class AccountConflictException extends AccountException {

	public AccountConflictException(String message, Object ...args) {
		super(String.format(message, args));
	}
	
}
