package cards.exceptions;

public class CardConflictException extends CardException{

	public CardConflictException(String message, Object...args) {
		super(String.format(message, args));
	}
}
