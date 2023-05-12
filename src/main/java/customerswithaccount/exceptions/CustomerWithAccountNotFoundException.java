package customerswithaccount.exceptions;

public class CustomerWithAccountNotFoundException extends CustomerWithAccountException {

    public CustomerWithAccountNotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }
}
