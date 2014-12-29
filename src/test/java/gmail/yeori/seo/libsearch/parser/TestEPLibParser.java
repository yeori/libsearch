package gmail.yeori.seo.libsearch.parser;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import gmail.yeori.seo.libsearch.model.SearchResult;

import org.junit.Before;
import org.junit.Test;

public class TestEPLibParser {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testParse() throws LibParserException {
		EPLibParser parser = new EPLibParser();
		List<SearchResult> results = parser.parse("총균쇠");
		for (Iterator iterator = results.iterator(); iterator.hasNext();) {
			SearchResult searchResult = (SearchResult) iterator.next();
			System.out.println(searchResult);
		}
	}

}
