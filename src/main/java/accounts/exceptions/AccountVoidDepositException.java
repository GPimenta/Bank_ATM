package accounts.exceptions;

public class AccountVoidDepositException extends AccountException{
	
	public AccountVoidDepositException(String message, Object ...args) {
		super(String.format(message, args));
	}

}
