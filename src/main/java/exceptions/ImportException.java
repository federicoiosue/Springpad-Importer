package exceptions;

import it.feio.android.springpadimporter.models.SpringpadElement;

public class ImportException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1224449299267954878L;

	private SpringpadElement springpadElement;


	public ImportException(String message) {
		this(message, null);
	}


	public ImportException(String message, Throwable e) {
		super(message, e);
		message = e.getMessage();
	}


	public SpringpadElement getSpringpadElement() {
		return springpadElement;
	}


	public void setSpringpadElement(SpringpadElement springpadElement) {
		this.springpadElement = springpadElement;
	}
}
