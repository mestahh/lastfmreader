package org.mestahh.lastfm.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RestRequestExecutor {

	private final String apiKey;

	public RestRequestExecutor(String apiKey) {
		this.apiKey = apiKey;
	}

	public String sendRequest(String request) throws IOException {
		URL lastFmApi = createURL(request);
		URLConnection connection = lastFmApi.openConnection();
		BufferedReader input = craeteBufferedReader(connection);
		return readLines(input);
	}

	protected String readLines(BufferedReader in) throws IOException {
		String inputLine;
		String answer = "";
		while ((inputLine = in.readLine()) != null) {
			answer += inputLine;
		}
		in.close();
		return answer;
	}

	protected BufferedReader craeteBufferedReader(URLConnection connection) throws IOException {
		return new BufferedReader(new InputStreamReader(connection.getInputStream()));
	}

	protected URL createURL(String request) throws MalformedURLException {
		return new URL("http://ws.audioscrobbler.com/2.0/?method=" + request + "&api_key=" + getApiKey());
	}

	public String getApiKey() {
		return apiKey;
	}

}
