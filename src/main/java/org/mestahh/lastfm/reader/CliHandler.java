package org.mestahh.lastfm.reader;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CliHandler {
	protected CommandLine createCommandLineWithOptions(String[] args) throws ParseException {
		Options options = new Options();
		options.addOption("m", true, "method name[bio|similar]");
		options.addOption("a", true, "artist name");
		options.addOption("k", true, "api key");

		CommandLineParser parser = new BasicParser();
		CommandLine cmd = parser.parse(options, args);
		return cmd;
	}

	protected boolean optionsAreNotDefined(CommandLine cmd) {
		return getApiKey(cmd) == null || getArtist(cmd) == null || getMethod(cmd) == null;
	}

	protected String getApiKey(CommandLine cmd) {
		return cmd.getOptionValue("k");
	}

	protected String getMethod(CommandLine cmd) {
		return cmd.getOptionValue("m");
	}

	protected String getArtist(CommandLine cmd) {
		return cmd.getOptionValue("a");
	}

}
