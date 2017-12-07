package api.gateway.exception;

public class HandlerException extends Exception {
 /**
	 * 
	 */
	private static final long serialVersionUID = -5473250886677350148L;
private String errorMessage;

public String getErrorMessage() {
	return errorMessage;
}

public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
}

public HandlerException(String message, Throwable cause) {

    super(message, cause);
	this.errorMessage=message;
}
public HandlerException(String message) {
    super(message);
    this.errorMessage=message;
}
}
