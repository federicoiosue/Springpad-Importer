package exceptions;

public class ImportException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1224449299267954878L;


	public ImportException(String message) {
		this(message, null);
	}


	public ImportException(String message, Throwable e) {
		super(message, e);
		message = e.getMessage();
	}
}
