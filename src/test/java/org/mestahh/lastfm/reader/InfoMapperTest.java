package org.mestahh.lastfm.reader;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.jdom.JDOMException;
import org.junit.Test;

public class InfoMapperTest {

	@Test
	public void test() throws IOException, JDOMException {
		String similar = readFile("artist_similar.xml");
		InfoMapper mapper = new InfoMapper();
		List<String> similarArtists = mapper.retrieveSimilarArtists(similar);
		assertEquals(3, similarArtists.size());
		assertEquals("Sonny & Cher", similarArtists.get(0));
		assertEquals("Madonna", similarArtists.get(1));
		assertEquals("Kylie Minogue", similarArtists.get(2));
	}
	
	

	private String readFile(String fileName) throws IOException {
		BufferedReader br = null;

		String content = "";
		String currentLine = null;
		br = new BufferedReader(
				new FileReader("src/test/resources/testfiles/" + fileName));

		while ((currentLine = br.readLine()) != null) {
			content += currentLine;
		}
		return content;
	}

}
