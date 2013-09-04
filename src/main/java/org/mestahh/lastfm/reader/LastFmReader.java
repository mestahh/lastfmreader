package org.mestahh.lastfm.reader;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jdom.JDOMException;

public class LastFmReader {

	private final RestRequestExecutor restExecutor;
	private final ResponseMapper mapper;
	private static final Map<String, String> bios = new Hashtable<String, String>();
	private static final Map<String, List<String>> similarArtists = new Hashtable<String, List<String>>();

	public LastFmReader(RestRequestExecutor restExecutor, ResponseMapper mapper) {
		this.restExecutor = restExecutor;
		this.mapper = mapper;
	}

	public synchronized String getBio(String artist) throws IOException, JDOMException {
		String bio = bios.get(artist);
		if (bioWasNotCached(bio)) {
			String answer = restExecutor.sendRequest("method=artist.getinfo&api_key=" + restExecutor.getApiKey() + "&artist="
					+ artist);
			bio = mapper.retrieveBio(answer);
			bios.put(artist, bio);
		}
		return bio;
	}

	private boolean bioWasNotCached(String bio) {
		return bio == null;
	}

	public synchronized List<String> getSimilarArtists(String artist) throws IOException, JDOMException {
		List<String> similar = similarArtists.get(artist);
		if (similiarArtistsWereNotCached(similar)) {
			String answer = restExecutor.sendRequest("method=artist.getsimilar&api_key=" + restExecutor.getApiKey()
					+ "&artist=" + artist);
			similar = mapper.retrieveSimilarArtists(answer);
			similarArtists.put(artist, similar);
		}
		return similar;

	}

	private boolean similiarArtistsWereNotCached(List<String> similar) {
		return similar == null;
	}

	public static void cleanCaches() {
		bios.clear();
		similarArtists.clear();
	}

}
