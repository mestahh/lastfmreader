package org.mestahh.lastfm.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RestRequestExecutor {

	private static final String LAST_FM_API_URI = "http://ws.audioscrobbler.com/2.0/";
	private final String apiKey;

	public RestRequestExecutor(String apiKey) {
		this.apiKey = apiKey;
	}

	public String sendRequest(String request) throws IOException {
		URL lastFmApi = createURL(request);
		System.out.println(lastFmApi.getQuery());
		URLConnection connection = openConnection(lastFmApi);
		BufferedReader input = craeteBufferedReader(connection);
		return readLines(input);
	}

	protected URLConnection openConnection(URL lastFmApi) throws IOException {
		return lastFmApi.openConnection();
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
		return new URL(LAST_FM_API_URI + "?method=" + request + "&api_key=" + getApiKey());
	}

	public String getApiKey() {
		return apiKey;
	}

}
