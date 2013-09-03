package org.mestahh.lastfm.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RestReader {

	private final String apiKey;

	public RestReader(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getAnswer(String request) throws IOException {
		URL oracle = createURL(request);
		URLConnection connection = oracle.openConnection();
		BufferedReader input = craeteBufferedReader(connection);
		return readLines(input);
	}

	private String readLines(BufferedReader in) throws IOException {
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
		return new URL("http://ws.audioscrobbler.com/2.0/?" + request);
	}

	public String getApiKey() {
		return apiKey;
	}

}
