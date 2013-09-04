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
	private RestReader restReader;
	@Mock
	private InfoMapper mapper;
	private final String apiKey = "apiKey";
	private String answer;
	private String request;
	private Reader testObj;

	@Before
	public void setUp() {
		testObj = new Reader(restReader, mapper);
		answer = "answer";
	}

	@Test
	public void retrieves_the_bio_via_the_last_fm_api() throws IOException, JDOMException {
		prepareExpectations("getinfo", answer);
		when(mapper.retrieveBio(answer)).thenReturn("bio");
		String bio = testObj.getBio("Metallica");

		assertEquals("bio", bio);
		verify(restReader).getAnswer(request);
		verify(mapper).retrieveBio(answer);
	}

	@Test
	public void retrieves_the_similar_artists_from_the_last_fm_api() throws IOException, JDOMException {
		prepareExpectations("getsimilar", answer);
		testObj.getSimilarArtists("Metallica");

		verify(restReader).getAnswer(request);
		verify(mapper).retrieveSimilarArtists(answer);
	}

	@Test
	public void returns_bio_if_it_was_cached_before() throws IOException, JDOMException {
		prepareExpectations("getinfo", "cachedAnswer");

		when(mapper.retrieveBio("cachedAnswer")).thenReturn("cachedBio");
		testObj.getBio("Metallica");
		Reader testObj2 = new Reader(restReader, mapper);
		String cachedResult = testObj2.getBio("Metallica");

		assertEquals("cachedBio", cachedResult);
		verify(restReader, times(1)).getAnswer(request);
	}

	@Test
	public void returns_similar_artists_if_it_was_cached_before() throws IOException, JDOMException {
		prepareExpectations("getsimilar", "cachedAnswer");

		when(mapper.retrieveSimilarArtists("cachedAnswer")).thenReturn(Arrays.asList("Pantera"));

		testObj.getSimilarArtists("Metallica");
		Reader testObj2 = new Reader(restReader, mapper);
		List<String> similarArtists = testObj2.getSimilarArtists("Metallica");

		assertEquals(Arrays.asList("Pantera"), similarArtists);
		verify(restReader, times(1)).getAnswer(request);
	}

	private void prepareExpectations(String method, String expectedAnswer) throws IOException {
		request = "method=artist." + method + "&api_key=" + apiKey + "&artist=Metallica";

		when(restReader.getApiKey()).thenReturn(apiKey);
		when(restReader.getAnswer(request)).thenReturn(expectedAnswer);
	}

	@After
	public void tearDown() {
		Reader.cleanCaches();
	}

}
