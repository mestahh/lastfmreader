package org.mestahh.lastfm.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.jdom.JDOMException;
import org.junit.Before;
import org.junit.Test;

public class ResponseMapperTest {

	private ResponseMapper testObj;

	@Before
	public void setUp() {
		testObj = new ResponseMapper();
	}

	@Test
	public void returns_an_empty_string_if_there_is_no_content_in_the_bio_section_in_the_xml() throws JDOMException,
			IOException, RequestErrorException {
		String info = readFile("artist_info_without_summary.xml");
		String bio = testObj.retrieveBio(info);
		assertEquals("", bio);
	}

	@Test
	public void it_parses_the_error_response_on_bio_request_and_throws_an_exception() throws IOException, JDOMException {
		String error = readFile("error.xml");
		try {
			testObj.retrieveBio(error);
			fail();
		} catch (RequestErrorException e) {
			assertEquals("10", e.getErrorCode());
			assertEquals("Invalid API Key", e.getMessage());
		}
	}

	@Test
	public void it_parses_the_error_response_on_similar_request_and_throws_an_exception() throws IOException,
			JDOMException {
		String error = readFile("error.xml");
		try {
			testObj.retrieveSimilarArtists(error);
			fail();
		} catch (RequestErrorException e) {
			assertEquals("10", e.getErrorCode());
			assertEquals("Invalid API Key", e.getMessage());
		}
	}

	@Test
	public void creates_a_string_from_the_bio_from_the_xml() throws IOException, JDOMException, RequestErrorException {
		String info = readFile("artist_info.xml");
		String bio = testObj.retrieveBio(info);
		assertEquals("bio summary", bio);
	}

	@Test
	public void creates_a_String_list_from_the_similar_artists_from_the_xml() throws IOException, JDOMException,
			RequestErrorException {
		String similar = readFile("artist_similar.xml");
		List<String> similarArtists = testObj.retrieveSimilarArtists(similar);
		assertEquals(3, similarArtists.size());
		assertEquals("Sonny & Cher", similarArtists.get(0));
		assertEquals("Madonna", similarArtists.get(1));
		assertEquals("Kylie Minogue", similarArtists.get(2));
	}

	private String readFile(String fileName) throws IOException {
		BufferedReader br = null;

		String content = "";
		String currentLine = null;
		br = new BufferedReader(new FileReader("src/test/resources/testfiles/" + fileName));

		while ((currentLine = br.readLine()) != null) {
			content += currentLine;
		}
		br.close();
		return content;
	}

}
