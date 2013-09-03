package org.mestahh.lastfm.reader;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

public class RestReaderTest {

	private String apiKey;
	private RestReader restReader;

	@Before
	public void setUp() {
		apiKey = "key";
		restReader = new RestReader(apiKey);
	}

	@Test
	public void stores_the_api_key() {
		assertEquals(apiKey, restReader.getApiKey());
	}
	
	@Test
	public void returns_artist_info() throws IOException {
		String answer = restReader.getAnswer("method=artist.getinfo&artist=Cher&api_key=37be6c106e0df038465a880c7b65b15b");
		System.out.println(answer);
	}
	
	@Test
	public void returns_similar_artists() throws IOException {
		String answer = restReader.getAnswer("method=artist.getsimilar&artist=cher&api_key=37be6c106e0df038465a880c7b65b15b");
		System.out.println(answer);
	}
	
	

	@Test
	public void it_creates_a_URL() throws IOException, URISyntaxException {
		URL url = restReader.createURL("method");
		assertEquals("http://ws.audioscrobbler.com/2.0/?method", url.toURI().toString());
	}

}
