package gmail.yeori.seo.libsearch.engine;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import gmail.yeori.seo.libsearch.LibSearchEvent;
import gmail.yeori.seo.libsearch.SearchListener;
import gmail.yeori.seo.libsearch.model.SearchResult;
import gmail.yeori.seo.libsearch.parser.ILibParser;
import gmail.yeori.seo.libsearch.parser.LibParserException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TestSearchEngine {

	private SearchEngine engine;

	@Before
	public void setUp() throws Exception {
		engine = new SearchEngine();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_registering_listeners_and_check_them_called() throws LibParserException {
		ILibParser stubParser = Mockito.mock(ILibParser.class);
		SearchListener listener_one = Mockito.mock(SearchListener.class);
		SearchListener listener_two = Mockito.mock(SearchListener.class);
		
		engine.addParser(stubParser);
		engine.addSearchListener(listener_one);
		engine.addSearchListener(listener_two);
		
		engine.search("자바", 0);
		verify(listener_one, Mockito.times(1)).searchResults(any(LibSearchEvent.class));
		verify(listener_two, Mockito.times(1)).searchResults(any(LibSearchEvent.class));
	}

	private List<SearchResult> loadHtml(String keyword, int i) {
		
		return null;
	}

}
