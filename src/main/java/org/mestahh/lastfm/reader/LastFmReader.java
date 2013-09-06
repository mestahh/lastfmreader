package org.mestahh.lastfm.reader;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jdom.JDOMException;
import org.mestahh.lastfm.reader.constants.ImplementedMethods;

public class LastFmReader {

	private final RestRequestExecutor restExecutor;
	private final ResponseMapper mapper;
	private static final Map<String, String> bios = new Hashtable<String, String>();
	private static final Map<String, List<String>> similarArtists = new Hashtable<String, List<String>>();

	public LastFmReader(RestRequestExecutor restExecutor, ResponseMapper mapper) {
		this.restExecutor = restExecutor;
		this.mapper = mapper;
	}

	public synchronized String getBio(String artist) throws IOException, JDOMException, RequestErrorException {
		String bio = bios.get(artist);
		if (bioWasNotCached(bio)) {
			String response = sendRequest(artist, ImplementedMethods.BIO.getApiMethod());
			bio = mapper.retrieveBio(response);
			bios.put(artist, bio);
		}
		return bio;
	}

	public synchronized List<String> getSimilarArtists(String artist) throws IOException, JDOMException,
			RequestErrorException {
		List<String> similar = similarArtists.get(artist);
		if (similiarArtistsWereNotCached(similar)) {
			String response = sendRequest(artist, ImplementedMethods.SIMILAR.getApiMethod());
			similar = mapper.retrieveSimilarArtists(response);
			similarArtists.put(artist, similar);
		}
		return similar;

	}

	private String sendRequest(String artist, String apiMethod) throws IOException {

		return restExecutor.sendRequest(apiMethod + "&artist=" + artist);
	}

	private boolean bioWasNotCached(String bio) {
		return bio == null;
	}

	private boolean similiarArtistsWereNotCached(List<String> similar) {
		return similar == null;
	}

	public static synchronized void cleanCaches() {
		bios.clear();
		similarArtists.clear();
	}

}
