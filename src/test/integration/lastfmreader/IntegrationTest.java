package lastfmreader;

import org.junit.Test;
import org.mestahh.lastfm.reader.CliHandler;
import org.mestahh.lastfm.reader.Main;

public class IntegrationTest {

	private static final String TEST_API_KEY = "37be6c106e0df038465a880c7b65b15b";

	@Test
	public void returns_artist_info() {
		runMain("bio", "Cher");
	}

	@Test
	public void returns_similar_artists() {
		runMain("similar", "Metallica");
	}

	@Test
	public void returns_bio_for_artists_with_white_spaces_in_their_names() {
		runMain("bio", "The Offspring");
	}

	@Test
	public void returns_similar_artists_for_artists_with_white_spaces_in_their_names() {
		runMain("bio", "Johhny Cash");
	}

	private void runMain(String method, String artist) {
		String[] args = { "-" + CliHandler.METHOD_OPTION, method, "-" + CliHandler.ARTIST_OPTION, artist,
				"-" + CliHandler.API_KEY_OPTION, TEST_API_KEY };
		Main.main(args);
	}

}
