package org.mestahh.lastfm.reader;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jdom.JDOMException;

public class Reader {

	private final RestReader restReader;
	private final InfoMapper mapper;
	private static final Map<String, String> bios = new Hashtable<String, String>();
	private static final Map<String, List<String>> similarArtists = new Hashtable<String, List<String>>();

	public Reader(RestReader restReader, InfoMapper mapper) {
		this.restReader = restReader;
		this.mapper = mapper;
	}

	public synchronized String getBio(String artist) throws IOException, JDOMException {
		String bio = bios.get(artist);
		if (bio == null) {
			String answer = restReader.getAnswer("method=artist.getinfo&api_key=" + restReader.getApiKey() + "&artist="
					+ artist);
			bio = mapper.retrieveBio(answer);
			bios.put(artist, bio);
		}
		return bio;
	}

	public synchronized List<String> getSimilarArtists(String artist) throws IOException, JDOMException {
		List<String> similar = similarArtists.get(artist);
		if (similar == null) {
			String answer = restReader.getAnswer("method=artist.getsimilar&api_key=" + restReader.getApiKey()
					+ "&artist=" + artist);
			similar = mapper.retrieveSimilarArtists(answer);
			similarArtists.put(artist, similar);
		}
		return similar;

	}

	public static void cleanCaches() {
		bios.clear();
		similarArtists.clear();
	}

}
