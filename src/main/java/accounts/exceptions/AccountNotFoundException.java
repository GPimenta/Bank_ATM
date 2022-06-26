package accounts.exceptions;

public class AccountNotFoundException extends AccountException{

	public AccountNotFoundException(String message, Object ...args) {
		super(String.format(message, args));
	}

}
