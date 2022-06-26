package cards.exceptions;

public class CardNotFoundException extends CardException{

	CardNotFoundException(String message, Object ...args) {
		super(String.format(message, args));
	}



}
