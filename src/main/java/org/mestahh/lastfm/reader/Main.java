package org.mestahh.lastfm.reader;

import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jdom.JDOMException;

public class Main {

	public static void main(String[] args) throws IOException, JDOMException, ParseException {
		Options options = new Options();
		options.addOption("m", true, "method name[bio|similar]");
		options.addOption("a", true, "artist name");
		options.addOption("k", true, "api_key");

		CommandLineParser parser = new BasicParser();
		CommandLine cmd = parser.parse(options, args);

		if (optionsAreNotDefined(cmd)) {
			usage();
			return;
		}
		//my key : "37be6c106e0df038465a880c7b65b15b"
		RestReader restReader = new RestReader(getApiKey(cmd));
		InfoMapper mapper = new InfoMapper();
		Reader reader = new Reader(restReader, mapper);

		if (getMethod(cmd).equals("bio")) {
			System.out.println(reader.getBio(getArtist(cmd)));
		}
		if (getMethod(cmd).equals("similar")) {
			print(reader.getSimilarArtists(getArtist(cmd)));
		}
	}

	private static String getMethod(CommandLine cmd) {
		return cmd.getOptionValue("m");
	}

	private static String getArtist(CommandLine cmd) {
		return cmd.getOptionValue("a");
	}

	private static String getApiKey(CommandLine cmd) {
		return cmd.getOptionValue("k");
	}

	private static boolean optionsAreNotDefined(CommandLine cmd) {
		return getApiKey(cmd) == null || getArtist(cmd) == null || getMethod(cmd) == null;
	}

	private static void usage() {
		System.out.println("Usage: java -jar lastfmreader.jar -k <api_key> -m <method name> -a <artist name>");
	}

	private static void print(List<String> similarArtists) {
		for (String artist : similarArtists) {
			System.out.println(artist);
		}
	}

}
