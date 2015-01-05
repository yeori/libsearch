package gmail.yeori.seo.libsearch;


public interface SearchListener {

//	void searchResults(String keyword, List<SearchResult> results);
	void searchResults(LibSearchEvent event);
	

}
