package br.com.a2dm.spdm.exceptions;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = -7979396198598876171L;

	public ApiException(String s) {
		super(s);
	}
	
	public ApiException(String s, Throwable throwable) {
		super(s, throwable);
	}
}