package org.mestahh.lastfm.reader;

import java.io.IOException;
import java.util.List;

import org.jdom.JDOMException;

public class Main {

	public static void main(String[] args) throws IOException, JDOMException {
		RestReader restReader = new RestReader("37be6c106e0df038465a880c7b65b15b");
		InfoMapper mapper = new InfoMapper();
		Reader reader = new Reader(restReader, mapper);
		
		print(reader.getSimilarArtists("Metallica"));
	}

	private static void print(List<String> similarArtists) {
		for (String artist: similarArtists) {
			System.out.println(artist);
		}
	}

}
