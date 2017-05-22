package IngenicoSDK;

public class WsdlHandlerException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WsdlHandlerException(Exception exception) {
        super(exception);
    }
	
	public WsdlHandlerException(String message) {
        super(message);
    }
	
	public WsdlHandlerException(String message, Exception exception) {
        super(message,exception);
    }
}
