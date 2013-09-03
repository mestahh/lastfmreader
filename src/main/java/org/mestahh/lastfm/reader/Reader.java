package org.mestahh.lastfm.reader;

import java.io.IOException;
import java.util.List;

public class Reader {

	private final RestReader restReader;
	private final InfoMapper mapper;

	public Reader(RestReader restReader, InfoMapper mapper) {
		this.restReader = restReader;
		this.mapper = mapper;
	}

	public String getBio(String artist) throws IOException {
		String answer = restReader.getAnswer("method=artist.getinfo&api_key=" + restReader.getApiKey() + "&artist="
				+ artist);
		return mapper.retrieveBio(answer);
	}

	public List<String> getSimilarArtists(String artist) throws IOException {
		String answer = restReader.getAnswer("method=artist.getsimilar&api_key=" + restReader.getApiKey() + "&artist="
				+ artist);
		return mapper.retrieveSimilarArtists(answer);

	}
}
