package org.mestahh.lastfm.reader;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.jdom.JDOMException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReaderTest {

	@Mock
	private RestRequestExecutor restReader;
	@Mock
	private ResponseMapper mapper;
	private final String apiKey = "apiKey";
	private String answer;
	private String request;
	private LastFmReader testObj;

	@Before
	public void setUp() {
		testObj = new LastFmReader(restReader, mapper);
		answer = "answer";
	}

	@Test
	public void retrieves_the_bio_via_the_last_fm_api() throws IOException, JDOMException {
		prepareExpectations("getinfo", answer);
		when(mapper.retrieveBio(answer)).thenReturn("bio");
		String bio = testObj.getBio("Metallica");

		assertEquals("bio", bio);
		verify(restReader).sendRequest(request);
		verify(mapper).retrieveBio(answer);
	}

	@Test
	public void retrieves_the_similar_artists_from_the_last_fm_api() throws IOException, JDOMException {
		prepareExpectations("getsimilar", answer);
		testObj.getSimilarArtists("Metallica");

		verify(restReader).sendRequest(request);
		verify(mapper).retrieveSimilarArtists(answer);
	}

	@Test
	public void returns_bio_if_it_was_cached_before() throws IOException, JDOMException {
		prepareExpectations("getinfo", "cachedAnswer");

		when(mapper.retrieveBio("cachedAnswer")).thenReturn("cachedBio");
		testObj.getBio("Metallica");
		LastFmReader testObj2 = new LastFmReader(restReader, mapper);
		String cachedResult = testObj2.getBio("Metallica");

		assertEquals("cachedBio", cachedResult);
		verify(restReader, times(1)).sendRequest(request);
	}

	@Test
	public void returns_similar_artists_if_it_was_cached_before() throws IOException, JDOMException {
		prepareExpectations("getsimilar", "cachedAnswer");

		when(mapper.retrieveSimilarArtists("cachedAnswer")).thenReturn(Arrays.asList("Pantera"));

		testObj.getSimilarArtists("Metallica");
		LastFmReader testObj2 = new LastFmReader(restReader, mapper);
		List<String> similarArtists = testObj2.getSimilarArtists("Metallica");

		assertEquals(Arrays.asList("Pantera"), similarArtists);
		verify(restReader, times(1)).sendRequest(request);
	}

	private void prepareExpectations(String method, String expectedAnswer) throws IOException {
		request = "method=artist." + method + "&api_key=" + apiKey + "&artist=Metallica";

		when(restReader.getApiKey()).thenReturn(apiKey);
		when(restReader.sendRequest(request)).thenReturn(expectedAnswer);
	}

	@After
	public void tearDown() {
		LastFmReader.cleanCaches();
	}

}
