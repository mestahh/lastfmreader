package org.mestahh.lastfm.reader;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class MainTest {

	@Test
	public void creates_a_new_instance_of_the_LastFmReader_class() {

		LastFmReader reader = Main.createReader("fakeApiKey");
		assertNotNull(reader);
	}

}
