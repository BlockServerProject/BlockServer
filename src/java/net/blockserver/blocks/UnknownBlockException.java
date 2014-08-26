package net.blockserver.blocks;

/**
 * An exception to be thrown when an UnknownBlock is given.
 * @author BlockServer Team
 */
@SuppressWarnings("serial")
public class UnknownBlockException extends Exception {
	
	private String message = null;
 
	/**
	 * Constructor for the exception, no message.
	 */
    public UnknownBlockException() {
        super();
    }
 
    /**
     * Constructor for the exception, requiring a message to be shown.
     * @param message The message to be shown, when the exception is called.
     */
    public UnknownBlockException(String message) {
        super(message);
        this.message = message;
    }
 
    /**
     * Returns the message of this exception.
     */
    @Override
    public String toString() {
        return message;
    }
 
    @Override
    /**
     * Also returns the message of this exception.
     */
    public String getMessage() {
        return message;
    }
}
