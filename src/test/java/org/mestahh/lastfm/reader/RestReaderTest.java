package org.mestahh.lastfm.reader;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class RestReaderTest {

	private String apiKey;
	private RestReader testObj;

	@Before
	public void setUp() {
		apiKey = "37be6c106e0df038465a880c7b65b15b";
		testObj = new RestReader(apiKey);
	}

	@Test
	public void stores_the_api_key() {
		assertEquals(apiKey, testObj.getApiKey());
	}

	@Ignore
	@Test
	public void returns_artist_info() throws IOException {
		String answer = testObj.getAnswer("artist.getinfo&artist=Cher");
		System.out.println(answer);
	}

	@Ignore
	@Test
	public void returns_similar_artists() throws IOException {
		String answer = testObj.getAnswer("artist.getsimilar&artist=cher");
		System.out.println(answer);
	}

	@Test
	public void it_creates_a_URL() throws IOException, URISyntaxException {
		URL url = testObj.createURL("method");
		assertEquals("http://ws.audioscrobbler.com/2.0/?method=method&api_key=" + apiKey, url.toURI().toString());
	}

}
