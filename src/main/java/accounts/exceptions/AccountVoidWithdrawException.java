package accounts.exceptions;

public class AccountVoidWithdrawException extends AccountException{
	public AccountVoidWithdrawException(String message, Object ...args) {
		super(String.format(message, args));
	}

}
