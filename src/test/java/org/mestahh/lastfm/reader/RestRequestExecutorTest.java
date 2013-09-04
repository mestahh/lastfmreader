package org.mestahh.lastfm.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RestRequestExecutorTest {

	private String apiKey;
	private RestRequestExecutor testObj;

	@Before
	public void setUp() {
		apiKey = "37be6c106e0df038465a880c7b65b15b";
		testObj = new RestRequestExecutor(apiKey);
	}

	@Test
	public void stores_the_api_key() {
		assertEquals(apiKey, testObj.getApiKey());
	}

	@Test
	public void reads_the_lines_from_the_buffer() throws IOException {
		BufferedReader in = mock(BufferedReader.class);
		when(in.readLine()).thenReturn("firstLine").thenReturn("secondLine").thenReturn(null);

		String result = testObj.readLines(in);
		assertEquals("firstLinesecondLine", result);
		verify(in).close();
	}

	@Test
	public void creates_a_new_BufferedReader_from_the_URLConnection() throws IOException {
		URLConnection connection = mock(URLConnection.class);
		InputStream inputStream = mock(InputStream.class);
		when(connection.getInputStream()).thenReturn(inputStream);
		BufferedReader buffReader = testObj.craeteBufferedReader(connection);
		assertNotNull(buffReader);
	}

	@Ignore
	@Test
	public void returns_artist_info() throws IOException {
		String answer = testObj.sendRequest("artist.getinfo&artist=Cher");
		System.out.println(answer);
	}

	@Ignore
	@Test
	public void returns_similar_artists() throws IOException {
		String answer = testObj.sendRequest("artist.getsimilar&artist=cher");
		System.out.println(answer);
	}

	@Test
	public void it_creates_a_URL() throws IOException, URISyntaxException {
		URL url = testObj.createURL("method");
		assertEquals("http://ws.audioscrobbler.com/2.0/?method=method&api_key=" + apiKey, url.toURI().toString());
	}

	@Test
	public void it_opens_a_connection_to_the_given_resource_and_returns_its_content() throws IOException {
		RestRequestExecutor fakeTestObject = new ExecutorTester("key");
		String response = fakeTestObject.sendRequest("url_test");
		assertEquals("testData", response);
	}

	class ExecutorTester extends RestRequestExecutor {

		public ExecutorTester(String apiKey) {
			super(apiKey);
		}

		@Override
		protected URL createURL(String request) throws MalformedURLException {
			return new File("src/test/resources/testfiles/" + request + ".txt").toURI().toURL();
		}
	}

}
