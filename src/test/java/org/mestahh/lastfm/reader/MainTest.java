package org.mestahh.lastfm.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.cli.ParseException;
import org.jdom.JDOMException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mestahh.lastfm.reader.constants.ImplementedMethods;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MainTest {

	private Main testObj;
	@Mock
	private LastFmReader readerMock;

	@Before
	public void setUp() {
		testObj = new Main();
	}

	@Test
	public void it_prints_out_the_usage_if_an_argument_is_missing() throws ParseException, IOException, JDOMException,
			RequestErrorException {
		MainTester mainTesterObj = new MainTester();
		String[] args = {};
		mainTesterObj.run(args);
		assertEquals("Usage: java -jar lastfmreader.jar -k <api_key> -m <method name> -a <artist name>",
				mainTesterObj.printedMessage);
	}

	@Test
	public void prints_out_the_bio_if_it_was_requested() throws IOException, JDOMException, ParseException,
			RequestErrorException {
		when(readerMock.getBio(any(String.class))).thenReturn("bioFromServer");
		MainTester mainTesterObj = new MainTester();
		String[] args = { "-m", ImplementedMethods.BIO.getParamName(), "-a", "artist", "-k", "key" };
		mainTesterObj.run(args);
		assertEquals("bioFromServer", mainTesterObj.printedMessage);
	}

	@Test
	public void prints_out_the_list_of_similar_artists_if_it_was_requested() throws IOException, JDOMException,
			ParseException, RequestErrorException {
		when(readerMock.getSimilarArtists(any(String.class))).thenReturn(Arrays.asList("similarArtist"));
		MainTester mainTesterObj = new MainTester();
		String[] args = { "-m", ImplementedMethods.SIMILAR.getParamName(), "-a", "artist", "-k", "key" };
		mainTesterObj.run(args);
		assertEquals("similarArtist", mainTesterObj.printedMessage);
	}

	@Test
	public void creates_a_new_instance_of_the_LastFmReader_class() {
		LastFmReader reader = testObj.createReader("fakeApiKey");
		assertNotNull(reader);
	}

	class MainTester extends Main {
		public String printedMessage = "";

		@Override
		protected void print(String message) {
			printedMessage = message;
		}

		@Override
		protected LastFmReader createReader(String apiKey) {
			return readerMock;
		}
	}
}
