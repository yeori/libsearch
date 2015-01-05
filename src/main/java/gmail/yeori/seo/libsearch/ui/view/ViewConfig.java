package gmail.yeori.seo.libsearch.ui.view;

import gmail.yeori.seo.libsearch.ui.IPageRequestAction;

public class ViewConfig {
	private IPageRequestAction actionHandler;
	public void setPageActionHandler(IPageRequestAction actionHandler) {
		this.actionHandler = actionHandler;
	}
	public IPageRequestAction getPageActionHandler() {
		return this.actionHandler;
	}

}
