package org.mestahh.lastfm.reader;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CliHandler {
	public static final String API_KEY_OPTION = "k";
	public static final String ARTIST_OPTION = "a";
	public static final String METHOD_OPTION = "m";
	public static final String DELIMITER = "+";

	protected CommandLine createCommandLineWithOptions(String[] args) throws ParseException {
		Options options = new Options();
		options.addOption(METHOD_OPTION, true, "method name[bio|similar]");
		options.addOption(ARTIST_OPTION, true, "artist name");
		options.addOption(API_KEY_OPTION, true, "api key");

		CommandLineParser parser = new BasicParser();
		CommandLine cmd = parser.parse(options, args);
		return cmd;
	}

	protected boolean optionsAreNotDefined(CommandLine cmd) {
		return getApiKey(cmd) == null || getArtist(cmd) == null || getMethod(cmd) == null;
	}

	protected String getApiKey(CommandLine cmd) {
		return cmd.getOptionValue(API_KEY_OPTION);
	}

	protected String getMethod(CommandLine cmd) {
		return cmd.getOptionValue(METHOD_OPTION);
	}

	protected String getArtist(CommandLine cmd) {
		String optionValue = cmd.getOptionValue(ARTIST_OPTION);
		if (optionValue == null) {
			return null;
		}
		return optionValue.replaceAll(" ", DELIMITER);
	}

	public String getUsage() {
		return "Usage: java -jar lastfmreader.jar -" + API_KEY_OPTION + " <api_key> -" + METHOD_OPTION
				+ " <method name> -" + ARTIST_OPTION + " <artist name>";
	}
}
