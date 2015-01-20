package gmail.yeori.seo.libsearch.ui.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gmail.yeori.seo.libsearch.engine.IFilter;
import gmail.yeori.seo.libsearch.ui.IPageRequestAction;

public class ViewConfig {
	private IPageRequestAction actionHandler;
	private List<IFilter> filters = new ArrayList<>();
	private Map<String, IView<?>> views = new HashMap<String, IView<?>>();
	
	public void setPageActionHandler(IPageRequestAction actionHandler) {
		this.actionHandler = actionHandler;
	}
	public IPageRequestAction getPageActionHandler() {
		return this.actionHandler;
	}
	
	public void addFilter ( IFilter filter) {
		
		if ( filter != null ){
			this.filters.add(filter);			
		}
	}
	
	public void clearFilters() {
		this.filters.clear();
	}
	
	public List<IFilter> getFilters() {
		return this.filters;
	}
	public void registerView(String name, IView<?> view) {
		views.put(name, view);
	}
	public IView getView(String name) {
		return views.get(name);
	}

}
