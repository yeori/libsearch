package gmail.yeori.seo.libsearch;

import gmail.yeori.seo.libsearch.engine.IFilter;

import java.util.List;

public interface SearchRequestListener {
	public void searchRequested ( String searchWord, List<IFilter> filters) ;
}
