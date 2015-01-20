package gmail.yeori.seo.libsearch.engine;

import gmail.yeori.seo.libsearch.model.SearchResult;

public interface IFilter {
	public boolean accept ( SearchResult sr );
}
