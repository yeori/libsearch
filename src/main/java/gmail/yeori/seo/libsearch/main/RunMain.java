package gmail.yeori.seo.libsearch.main;

import java.util.List;

import gmail.yeori.seo.libsearch.engine.SearchEngine;
import gmail.yeori.seo.libsearch.model.SearchResult;
import gmail.yeori.seo.libsearch.parser.EPLibParser;
import gmail.yeori.seo.libsearch.ui.ViewMain;
import gmail.yeori.seo.libsearch.SearchListener;
import gmail.yeori.seo.libsearch.SearchRequestListener;

public class RunMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("test run");
		
		final SearchEngine engine = new SearchEngine();
		engine.addParser(new EPLibParser());
		
		final ViewMain view = new ViewMain();
		
		view.addSearchRequestListener ( new SearchRequestListener(){
			@Override
			public void searchRequested(String searchWord) {
				engine.search(searchWord);
			}
		});
		
		engine.addSearchListener ( new SearchListener() {
			@Override
			public void searchResults(String keyword, List<SearchResult> results) {
				view.update(keyword, results);
			}
		});
		
//		engine.setUrl("");

	}

}
