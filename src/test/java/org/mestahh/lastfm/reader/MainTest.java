package org.mestahh.lastfm.reader;

import static org.junit.Assert.assertEquals;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

public class MainTest {

	@Test
	public void creates_the_command_line_object_with_zero_options_if_there_is_no_argument() throws ParseException {
		String[] args = { "" };
		CommandLine cmd = Main.createCommandLineWithOptions(args);

		assertEquals(0, cmd.getOptions().length);
	}

	@Test
	public void adds_the_method_option_if_there_is_an_argument_for_that() throws ParseException {
		String[] args = { "-m", "bio" };
		CommandLine cmd = Main.createCommandLineWithOptions(args);

		assertEquals(1, cmd.getOptions().length);
		assertEquals(new Option("m", true, "method name[bio|similar]"), cmd.getOptions()[0]);
	}

	@Test
	public void adds_the_artist_name_option_if_there_is_an_argument_for_that() throws ParseException {
		String[] args = { "-a", "Metallica" };
		CommandLine cmd = Main.createCommandLineWithOptions(args);

		assertEquals(1, cmd.getOptions().length);
		assertEquals(new Option("a", true, "artist name"), cmd.getOptions()[0]);
	}

	@Test
	public void adds_the_api_key_option_if_there_is_an_argument_for_that() throws ParseException {
		String[] args = { "-k", "1234" };
		CommandLine cmd = Main.createCommandLineWithOptions(args);

		assertEquals(1, cmd.getOptions().length);
		assertEquals(new Option("k", true, "api key"), cmd.getOptions()[0]);
	}

}
