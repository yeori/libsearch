package gmail.yeori.seo.libsearch.engine.filter;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gmail.yeori.seo.libsearch.LibSearchEvent;
import gmail.yeori.seo.libsearch.SearchListener;
import gmail.yeori.seo.libsearch.engine.SearchEngine;
import gmail.yeori.seo.libsearch.engine.Session;
import gmail.yeori.seo.libsearch.engine.filter.PubYearFilter.METHOD;
import gmail.yeori.seo.libsearch.model.HOLDS;
import gmail.yeori.seo.libsearch.model.STATUS;
import gmail.yeori.seo.libsearch.model.SearchResult;
import gmail.yeori.seo.libsearch.parser.EPLibParser;
import gmail.yeori.seo.libsearch.parser.LibParserException;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TestPubYearFilter {

	private SearchEngine engine;
	@Before
	public void setUp() throws Exception {
		engine = new SearchEngine();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_GREAT_OR_EQUAL_filter() throws LibParserException {
		EPLibParser eplib = Mockito.mock(EPLibParser.class);
		when(eplib.parse(any(Session.class))).thenReturn(sampleResults());
		
		engine.addParser(eplib);
		engine.addSearchListener(new SearchListener() {
			@Override
			public void searchResults(LibSearchEvent event) {
				assertEquals ( event.getSearchResults().size(), 2);
			}
		});
		// 출판년도가 2005년 이후(2005년 포함)
		engine.search("anything", 0, new PubYearFilter(2005, METHOD.GE));
	}
	
	@Test
	public void test_LESS_THAN_filter() throws LibParserException {
		EPLibParser eplib = Mockito.mock(EPLibParser.class);
		when(eplib.parse(any(Session.class))).thenReturn(sampleResults());
		
		engine.addParser(eplib);
		engine.addSearchListener(new SearchListener() {
			@Override
			public void searchResults(LibSearchEvent event) {
				assertEquals ( event.getSearchResults().size(), 1);
			}
		});
		// 출판년도가 2005년 이전(2005년 포함)
		engine.search("less than", 0, new PubYearFilter(2005, METHOD.LT));
	}

	private List<SearchResult> sampleResults() {
		ArrayList<SearchResult> results = new ArrayList<>();
		results.add(new SearchResult("", "Title-0", "author-0", "pub-A", "http://www.eplib.or.kr/00", STATUS.AVAILABLE, HOLDS.NO, null, "응암", "2004"));
		results.add(new SearchResult("", "Title-1", "author-K", "pub-A", "http://www.eplib.or.kr/01", STATUS.AVAILABLE, HOLDS.NO, null, "은평", "2005"));
		results.add(new SearchResult("", "Title-2", "author-K", "pub-B", "http://www.eplib.or.kr/02", STATUS.AVAILABLE, HOLDS.NO, null, "은평", "2010"));
		
		return results;
	}

}
