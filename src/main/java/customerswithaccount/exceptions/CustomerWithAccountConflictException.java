package customerswithaccount.exceptions;

import costumers.exceptions.CustomerConflictException;

public class CustomerWithAccountConflictException extends CustomerConflictException{

    public CustomerWithAccountConflictException(String message, Object... args) {
        super(String.format(message, args));
    }
}
