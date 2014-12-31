package gmail.yeori.seo.libsearch.engine;

import gmail.yeori.seo.libsearch.SearchListener;
import gmail.yeori.seo.libsearch.model.SearchResult;
import gmail.yeori.seo.libsearch.parser.ILibParser;
import gmail.yeori.seo.libsearch.parser.LibParserException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchEngine {
	private List<SearchListener> listeners ;
	
	private List<ILibParser> parsers;
	
	public SearchEngine() {
		parsers = new ArrayList<>();
		listeners = new ArrayList<>();
	}
	
	public void addParser ( ILibParser parser) {
		parsers.add(parser);
	}
	
	public void search ( String keyword ) {
		Iterator<ILibParser> itr = parsers.iterator();
		while ( itr.hasNext()) {
			try {
				List<SearchResult> results = itr.next().parse(keyword);
				notifySearchResult(keyword, results);
			} catch (LibParserException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void notifySearchResult(String keyword, List<SearchResult> results) {
		List<SearchListener> cloned = null;
		synchronized (listeners) {
			cloned = new ArrayList<>(listeners);
		}
		for( int i = 0 ; i < cloned.size() ; i++) {
			cloned.get(i).searchResults(keyword, results);
		}
		
	}

	public void addSearchListener(SearchListener listener) {
		listeners.add(listener);
		
	}
}