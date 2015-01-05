package gmail.yeori.seo.libsearch.main;

import java.util.List;

import gmail.yeori.seo.libsearch.engine.SearchEngine;
import gmail.yeori.seo.libsearch.model.SearchResult;
import gmail.yeori.seo.libsearch.parser.EPLibParser;
import gmail.yeori.seo.libsearch.ui.IPageRequestAction;
import gmail.yeori.seo.libsearch.ui.ViewMain;
import gmail.yeori.seo.libsearch.ui.view.TabView;
import gmail.yeori.seo.libsearch.LibSearchEvent;
import gmail.yeori.seo.libsearch.SearchListener;
import gmail.yeori.seo.libsearch.SearchRequestListener;

public class RunMain {
	private static SearchEngine ENGINE ;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("test run");
		
		ENGINE = new SearchEngine();
		ENGINE.addParser(new EPLibParser());
		
		PageReqAction action = new PageReqAction();
		final ViewMain view = new ViewMain(action);
		
		view.addSearchRequestListener ( new SearchRequestListener(){
			@Override
			public void searchRequested(String searchWord) {
				ENGINE.search(searchWord);
			}
		});
		
		ENGINE.addSearchListener ( new SearchListener() {
			@Override
			public void searchResults(LibSearchEvent event ) {
				String keyword = event.getKeyword();
				List<SearchResult> results = event.getSearchResults();
				int pageIndex = event.getPageIndex();
				int pageSize = event.getPageSize();
				view.update(keyword, results, pageIndex, pageSize);
			}
		});
		
		
//		engine.setUrl("");

	}
	
	/**
	 * 이 리스너는 pagenation을 관리하는 TabView 안에 있는 
	 * 페이지 버튼이 클릭될때 실행됨.
	 * 
	 * @see TabView
	 *  
	 * @author chminseo
	 *
	 */
	static class PageReqAction implements IPageRequestAction {

		@Override
		public void pageRequested(String keyword, int pageIndex) {
			System.out.println("검색 요청 : " + keyword + "[" + pageIndex + "]");
			ENGINE.search(keyword, pageIndex);
		}
		
	}

}
