package org.mestahh.lastfm.reader;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.filter.ElementFilter;
import org.jdom.input.SAXBuilder;

public class ResponseMapper {

	private final SAXBuilder builder;

	public ResponseMapper() {
		builder = new SAXBuilder();
	}

	public String retrieveBio(String answer) throws JDOMException, IOException {

		Document info = builder.build(new StringReader(answer));
		Iterator<Element> content = getDecendantElements(info, "summary");
		while (content.hasNext()) {
			return content.next().getText();
		}
		return "";
	}

	public List<String> retrieveSimilarArtists(String answer) throws JDOMException, IOException {
		Document artistsDoc = builder.build(new StringReader(answer));
		Iterator<Element> artistsElements = getDecendantElements(artistsDoc, "artist");

		List<String> artists = new ArrayList<String>();
		while (artistsElements.hasNext()) {
			String name = artistsElements.next().getChildText("name");
			artists.add(name);
		}
		return artists;
	}

	private Iterator<Element> getDecendantElements(Document response, String tagName) {
		ElementFilter elementFilter = new ElementFilter(tagName);
		return response.getRootElement().getDescendants(elementFilter);
	}

}
