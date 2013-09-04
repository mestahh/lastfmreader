package org.mestahh.lastfm.reader;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.jdom.JDOMException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class MainTest {

	private Main testObj;

	@Before
	public void setUp() {
		testObj = new Main();
	}

	@Test
	public void creates_a_new_instance_of_the_LastFmReader_class() {
		LastFmReader reader = testObj.createReader("fakeApiKey");
		assertNotNull(reader);
	}

	@Ignore
	@Test
	public void returns_artist_info() throws IOException, JDOMException {
		LastFmReader reader = testObj.createReader("37be6c106e0df038465a880c7b65b15b");
		reader.getBio("Cher");
	}

	@Ignore
	@Test
	public void returns_similar_artists() throws IOException, JDOMException {
		LastFmReader reader = testObj.createReader("37be6c106e0df038465a880c7b65b15b");
		reader.getSimilarArtists("Metallica");
	}
}
