package costumers.exceptions;

public class CustomerNotFoundException extends CustomerException{
	public CustomerNotFoundException(String message, Object ...args) {
		super(String.format(message, args));
	}
}
