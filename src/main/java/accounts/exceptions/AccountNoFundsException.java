package accounts.exceptions;

public class AccountNoFundsException extends AccountException{
	public AccountNoFundsException(String message, Object ...args) {
		super(String.format(message, args));
	}
}
