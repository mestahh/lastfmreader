package org.mestahh.lastfm.reader;

import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.jdom.JDOMException;

public class Main {

	public static void main(String[] args) throws IOException, JDOMException, ParseException {
		CliHandler cliHandler = new CliHandler();
		CommandLine cmd = cliHandler.createCommandLineWithOptions(args);

		if (cliHandler.optionsAreNotDefined(cmd)) {
			usage();
			return;
		}
		String apiKey = cliHandler.getApiKey(cmd);
		//my key : "37be6c106e0df038465a880c7b65b15b"
		LastFmReader reader = createReader(apiKey);

		if (cliHandler.getMethod(cmd).equals("bio")) {
			print(reader.getBio(cliHandler.getArtist(cmd)));
		}
		if (cliHandler.getMethod(cmd).equals("similar")) {
			printList(reader.getSimilarArtists(cliHandler.getArtist(cmd)));
		}
	}

	protected static LastFmReader createReader(String apiKey) {
		RestRequestExecutor restReader = new RestRequestExecutor(apiKey);
		ResponseMapper mapper = new ResponseMapper();
		LastFmReader reader = new LastFmReader(restReader, mapper);
		return reader;
	}

	private static void print(String string) {
		System.out.println(string);
	}

	private static void usage() {
		print("Usage: java -jar lastfmreader.jar -k <api_key> -m <method name> -a <artist name>");
	}

	private static void printList(List<String> similarArtists) {
		for (String artist : similarArtists) {
			print(artist);
		}
	}

}
