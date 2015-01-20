package gmail.yeori.seo.libsearch.ui;

import gmail.yeori.seo.libsearch.engine.IFilter;

import java.util.List;

public interface IPageRequestAction {
	public void pageRequested ( String keyword, int pageIndex, List<IFilter> filters );
}
