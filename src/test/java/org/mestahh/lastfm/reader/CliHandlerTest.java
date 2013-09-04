package org.mestahh.lastfm.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;

public class CliHandlerTest {

	private static final String HYPHON = "-";
	private CliHandler testObj;

	@Before
	public void setUp() {
		testObj = new CliHandler();
	}

	@Test
	public void returns_false_if_all_options_are_defined() throws ParseException {
		String[] args = { HYPHON + CliHandler.METHOD_OPTION, "bio", HYPHON + CliHandler.ARTIST_OPTION, "artist",
				HYPHON + CliHandler.API_KEY_OPTION, "key" };
		CommandLine cmd = testObj.createCommandLineWithOptions(args);
		assertFalse(testObj.optionsAreNotDefined(cmd));
	}

	@Test
	public void returns_true_if_the_method_option_is_missing() throws ParseException {
		assertOptionExistence(HYPHON + CliHandler.ARTIST_OPTION, "artist", HYPHON + CliHandler.API_KEY_OPTION, "key");
	}

	@Test
	public void returns_true_if_the_artist_option_is_missing() throws ParseException {
		assertOptionExistence(HYPHON + CliHandler.METHOD_OPTION, "bio", HYPHON + CliHandler.API_KEY_OPTION, "key");
	}

	@Test
	public void returns_true_if_the_api_key_option_is_missing() throws ParseException {
		assertOptionExistence(HYPHON + CliHandler.METHOD_OPTION, "bio", HYPHON + CliHandler.ARTIST_OPTION, "artist");
	}

	private void assertOptionExistence(String... args2) throws ParseException {
		CommandLine cmd = testObj.createCommandLineWithOptions(args2);
		assertTrue(testObj.optionsAreNotDefined(cmd));
	}

	@Test
	public void creates_the_command_line_object_with_zero_options_if_there_is_no_argument() throws ParseException {
		String[] args = { "" };
		CommandLine cmd = testObj.createCommandLineWithOptions(args);

		assertEquals(0, cmd.getOptions().length);
	}

	@Test
	public void returns_the_usage() {
		assertEquals("Usage: java -jar lastfmreader.jar -k <api_key> -m <method name> -a <artist name>",
				testObj.getUsage());
	}

	@Test
	public void adds_the_method_option_if_there_is_an_argument_for_that() throws ParseException {
		String[] args = { HYPHON + CliHandler.METHOD_OPTION, "bio" };
		CommandLine cmd = testObj.createCommandLineWithOptions(args);

		assertEquals(1, cmd.getOptions().length);
		assertEquals(new Option(CliHandler.METHOD_OPTION, true, "method name[bio|similar]"), cmd.getOptions()[0]);
	}

	@Test
	public void adds_the_artist_name_option_if_there_is_an_argument_for_that() throws ParseException {
		String[] args = { HYPHON + CliHandler.ARTIST_OPTION, "Metallica" };
		CommandLine cmd = testObj.createCommandLineWithOptions(args);

		assertEquals(1, cmd.getOptions().length);
		assertEquals(new Option(CliHandler.ARTIST_OPTION, true, "artist name"), cmd.getOptions()[0]);
	}

	@Test
	public void adds_the_api_key_option_if_there_is_an_argument_for_that() throws ParseException {
		String[] args = { HYPHON + CliHandler.API_KEY_OPTION, "1234" };
		CommandLine cmd = testObj.createCommandLineWithOptions(args);

		assertEquals(1, cmd.getOptions().length);
		assertEquals(new Option(CliHandler.API_KEY_OPTION, true, "api key"), cmd.getOptions()[0]);
	}

}
