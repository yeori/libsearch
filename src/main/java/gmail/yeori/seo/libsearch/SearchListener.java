package gmail.yeori.seo.libsearch;

import gmail.yeori.seo.libsearch.model.SearchResult;

import java.util.List;

public interface SearchListener {

	void searchResults(String keyword, List<SearchResult> results);
	

}
