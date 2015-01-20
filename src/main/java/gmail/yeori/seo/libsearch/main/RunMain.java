package gmail.yeori.seo.libsearch.main;

import java.util.List;

import gmail.yeori.seo.libsearch.engine.IFilter;
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
			public void searchRequested(String searchWord, List<IFilter> filters) {
				/* FIXME 필터를 계속해서 달고 다니게 되므로 인터페이스에도 필터를 추가해주어야 함.
				 * 아래쪽 페이지 요청에서도 똑같이 필터를 계속 넘겨받고 있음.
				 * 
				 * 또한 매번 필터가 존재하는지 확인하는 보기 안좋은 IF-ELSE가 계속해서 등장하게 됨.
				 * 
				 * 이를 개선할 방법은?
				 * 
				 */
				if ( filters == null || filters.size() == 0 ) {
					ENGINE.search(searchWord);
				} else if ( filters.size() == 1) {					
					ENGINE.search(searchWord, filters.get(0));
				} else {
					// COMMENT Composite filter 필요함.
				}
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
		public void pageRequested(String keyword, int pageIndex, List<IFilter> filters ) {
			System.out.println("검색 요청 : " + keyword + "[" + pageIndex + "]" + ", " + filters);
			if ( filters == null || filters.size() ==0 ) {
				ENGINE.search(keyword, pageIndex );
			} else {
				ENGINE.search(keyword, pageIndex, filters.get(0) );		
			}
		}
		
	}

}
