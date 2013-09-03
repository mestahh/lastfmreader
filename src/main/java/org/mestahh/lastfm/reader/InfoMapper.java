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

public class InfoMapper {

	public String retrieveBio(String answer) {
		return null;
	}

	public List<String> retrieveSimilarArtists(String answer) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		List<String> artists = new ArrayList<String>();
		Document artistsDoc = builder.build(new StringReader(answer));
		Iterator<Element> artistsElements = artistsDoc.getRootElement().getDescendants(new ElementFilter("artist"));
		
		while(artistsElements.hasNext()) {
			String name = artistsElements.next().getChildText("name");
			artists.add(name);
		}
		return artists;
	}

}
