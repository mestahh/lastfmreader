package org.mestahh.lastfm.reader;

public class RequestErrorException extends Exception {

	private final String errorCode;

	public RequestErrorException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
