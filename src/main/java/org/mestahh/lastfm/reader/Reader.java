package org.mestahh.lastfm.reader;

import java.util.List;

public class Reader {

	private final RestReader restReader;
	private final InfoMapper mapper;

	public Reader(RestReader restReader, InfoMapper mapper) {
		this.restReader = restReader;
		this.mapper = mapper;
	}

	public String getBio(String artist) {
		String answer = restReader.getAnswer("http://ws.audioscrobbler.com/2.0/?method=artist.getInfo&api_key="
				+ restReader.getApiKey() + "&artist=" + artist);
		return mapper.retrieveBio(answer);
	}

	public List<String> getSimilarArtists(String artist) {
		String answer = restReader.getAnswer("http://ws.audioscrobbler.com/2.0/?method=artist.getSimilar&api_key="
				+ restReader.getApiKey() + "&artist=" + artist);
		return mapper.retrieveSimilarArtists(answer);

	}
}
