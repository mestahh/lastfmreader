package org.mestahh.lastfm.reader;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.jdom.JDOMException;
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
	private Reader reader;

	@Before
	public void setUp() {
		reader = new Reader(restReader, mapper);
		answer = "answer";
	}

	@Test
	public void retrieves_the_bio_via_the_last_fm_api() throws IOException {
		prepareExpectations("getinfo");
		String bio = reader.getBio("Metallica");

		verify(restReader).getAnswer(request);
		verify(mapper).retrieveBio(answer);
	}

	@Test
	public void retrieves_the_similar_artists_from_the_last_fm_api() throws IOException, JDOMException {
		prepareExpectations("getsimilar");
		List<String> similarArtists = reader.getSimilarArtists("Metallica");

		verify(restReader).getAnswer(request);
		verify(mapper).retrieveSimilarArtists(answer);
	}

	private void prepareExpectations(String method) throws IOException {
		request = "method=artist." + method + "&api_key=" + apiKey + "&artist=Metallica";

		when(restReader.getApiKey()).thenReturn(apiKey);
		when(restReader.getAnswer(request)).thenReturn(answer);
	}

}
