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

	private CliHandler testObj;

	@Before
	public void setUp() {
		testObj = new CliHandler();
	}

	@Test
	public void returns_false_if_all_options_are_defined() throws ParseException {
		String[] args = { "-m", "bio", "-a", "artist", "-k", "key" };
		CommandLine cmd = testObj.createCommandLineWithOptions(args);
		assertFalse(testObj.optionsAreNotDefined(cmd));
	}

	@Test
	public void returns_true_if_the_method_option_is_missing() throws ParseException {
		assertOptionExistence("-a", "artist", "-k", "key");
	}

	@Test
	public void returns_true_if_the_artist_option_is_missing() throws ParseException {
		assertOptionExistence("-m", "bio", "-k", "key");
	}

	@Test
	public void returns_true_if_the_api_key_option_is_missing() throws ParseException {
		assertOptionExistence("-m", "bio", "-a", "artist");
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
	public void adds_the_method_option_if_there_is_an_argument_for_that() throws ParseException {
		String[] args = { "-m", "bio" };
		CommandLine cmd = testObj.createCommandLineWithOptions(args);

		assertEquals(1, cmd.getOptions().length);
		assertEquals(new Option("m", true, "method name[bio|similar]"), cmd.getOptions()[0]);
	}

	@Test
	public void adds_the_artist_name_option_if_there_is_an_argument_for_that() throws ParseException {
		String[] args = { "-a", "Metallica" };
		CommandLine cmd = testObj.createCommandLineWithOptions(args);

		assertEquals(1, cmd.getOptions().length);
		assertEquals(new Option("a", true, "artist name"), cmd.getOptions()[0]);
	}

	@Test
	public void adds_the_api_key_option_if_there_is_an_argument_for_that() throws ParseException {
		String[] args = { "-k", "1234" };
		CommandLine cmd = testObj.createCommandLineWithOptions(args);

		assertEquals(1, cmd.getOptions().length);
		assertEquals(new Option("k", true, "api key"), cmd.getOptions()[0]);
	}

}
