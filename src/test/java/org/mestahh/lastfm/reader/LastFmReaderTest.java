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
public class LastFmReaderTest {

	@Mock
	private RestRequestExecutor restRequestExecutor;
	@Mock
	private ResponseMapper mapper;
	private final String apiKey = "apiKey";
	private String answer;
	private String request;
	private LastFmReader testObj;

	@Before
	public void setUp() {
		testObj = new LastFmReader(restRequestExecutor, mapper);
		answer = "answer";
	}

	@Test
	public void retrieves_the_bio_via_the_last_fm_api() throws IOException, JDOMException, RequestErrorException {
		prepareExpectations("getinfo", answer);
		when(mapper.retrieveBio(answer)).thenReturn("bio");
		String bio = testObj.getBio("Metallica");

		assertEquals("bio", bio);
		verify(restRequestExecutor).sendRequest(request);
		verify(mapper).retrieveBio(answer);
	}

	@Test
	public void retrieves_the_similar_artists_from_the_last_fm_api() throws IOException, JDOMException,
			RequestErrorException {
		prepareExpectations("getsimilar", answer);
		testObj.getSimilarArtists("Metallica");

		verify(restRequestExecutor).sendRequest(request);
		verify(mapper).retrieveSimilarArtists(answer);
	}

	@Test
	public void returns_bio_if_it_was_cached_before() throws IOException, JDOMException, RequestErrorException {
		prepareExpectations("getinfo", "cachedAnswer");

		when(mapper.retrieveBio("cachedAnswer")).thenReturn("cachedBio");
		testObj.getBio("Metallica");
		LastFmReader testObj2 = new LastFmReader(restRequestExecutor, mapper);
		String cachedResult = testObj2.getBio("Metallica");

		assertEquals("cachedBio", cachedResult);
		verify(restRequestExecutor, times(1)).sendRequest(request);
	}

	@Test
	public void returns_similar_artists_if_it_was_cached_before() throws IOException, JDOMException,
			RequestErrorException {
		prepareExpectations("getsimilar", "cachedAnswer");

		when(mapper.retrieveSimilarArtists("cachedAnswer")).thenReturn(Arrays.asList("Pantera"));

		testObj.getSimilarArtists("Metallica");
		LastFmReader testObj2 = new LastFmReader(restRequestExecutor, mapper);
		List<String> similarArtists = testObj2.getSimilarArtists("Metallica");

		assertEquals(Arrays.asList("Pantera"), similarArtists);
		verify(restRequestExecutor, times(1)).sendRequest(request);
	}

	private void prepareExpectations(String method, String expectedAnswer) throws IOException {
		request = "artist." + method + "&artist=Metallica";

		when(restRequestExecutor.getApiKey()).thenReturn(apiKey);
		when(restRequestExecutor.sendRequest(request)).thenReturn(expectedAnswer);
	}

	@After
	public void tearDown() {
		LastFmReader.cleanCaches();
	}

}
