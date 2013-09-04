package org.mestahh.lastfm.reader.constants;

public enum ImplementedMethods {
	BIO("bio", "artist.getinfo"), SIMILAR("similar", "artist.getsimilar");

	private String paramName;
	private final String apiMethod;

	private ImplementedMethods(String paramName, String apiMethod) {
		this.apiMethod = apiMethod;
		this.paramName = paramName;
	}

	public String getParamName() {
		return paramName;
	}

	public String getApiMethod() {
		return apiMethod;
	}

}
