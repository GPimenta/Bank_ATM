package accounts.exceptions;

public class AccountWithoutFundsException extends AccountException {
    public AccountWithoutFundsException(String message, Object ...args){
        super(String.format(message, args));
    }
}

