package org.mestahh.lastfm.reader;

import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.jdom.JDOMException;
import org.mestahh.lastfm.reader.constants.ImplementedMethods;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		try {
			System.out.println("Trying to download the requested info...");
			main.run(args);
		} catch (RequestErrorException e) {
			System.out.println("Error in request: " + e.getMessage() + ", errorCode: " + e.getErrorCode() + ".");
		} catch (Exception e) {
			System.out
					.println("An error occured. Please check your query and your internet connection.\nA company proxy could be your enemy.\nContact me on : http://github.com/mestahh/lastfmreader");
		}
	}

	protected void run(String[] args) throws ParseException, IOException, JDOMException, RequestErrorException {
		CliHandler cliHandler = new CliHandler();
		CommandLine cmd = cliHandler.createCommandLineWithOptions(args);

		if (cliHandler.optionsAreNotDefined(cmd)) {
			usage(cliHandler);
			return;
		}
		String apiKey = cliHandler.getApiKey(cmd);
		LastFmReader reader = createReader(apiKey);

		if (cliHandler.getMethod(cmd).equals(ImplementedMethods.BIO.getParamName())) {
			print(reader.getBio(cliHandler.getArtist(cmd)));
		}
		if (cliHandler.getMethod(cmd).equals(ImplementedMethods.SIMILAR.getParamName())) {
			printList(reader.getSimilarArtists(cliHandler.getArtist(cmd)));
		}

	}

	protected LastFmReader createReader(String apiKey) {
		RestRequestExecutor restReader = new RestRequestExecutor(apiKey);
		ResponseMapper mapper = new ResponseMapper();
		LastFmReader reader = new LastFmReader(restReader, mapper);
		return reader;
	}

	protected void print(String string) {
		System.out.println(string);
	}

	private void usage(CliHandler cliHandler) {
		print(cliHandler.getUsage());
	}

	private void printList(List<String> similarArtists) {
		for (String artist : similarArtists) {
			print(artist);
		}
	}

}
