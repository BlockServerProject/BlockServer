package net.blockserver.blocks;

@SuppressWarnings("serial")
public class UnknownBlockException extends Exception {
	
	private String message = null;
 
    public UnknownBlockException() {
        super();
    }
 
    public UnknownBlockException(String message) {
        super(message);
        this.message = message;
    }
 
    @Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }
}
