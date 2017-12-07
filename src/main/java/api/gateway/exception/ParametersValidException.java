package api.gateway.exception;

public class ParametersValidException extends Exception {
 /**
	 * 
	 */
	private static final long serialVersionUID = 5524032158432965496L;
/**
	 * 
	 */

private String errorMessage;

public String getErrorMessage() {
	return errorMessage;
}

public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
}

public ParametersValidException(String message, Throwable cause) {

    super(message, cause);
	this.errorMessage=message;
}
public ParametersValidException(String message) {
    super(message);
    this.errorMessage=message;
}
}
