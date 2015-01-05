package gmail.yeori.seo.libsearch.engine;

import gmail.yeori.seo.libsearch.LibSearchEvent;
import gmail.yeori.seo.libsearch.SearchListener;
import gmail.yeori.seo.libsearch.engine.Session.PageContext;
import gmail.yeori.seo.libsearch.model.SearchResult;
import gmail.yeori.seo.libsearch.parser.ILibParser;
import gmail.yeori.seo.libsearch.parser.LibParserException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchEngine {
	private List<SearchListener> listeners ;
	
	private List<ILibParser> parsers;
	private SessionManager sessionManager = new SessionManager();
	
	public SearchEngine() {
		parsers = new ArrayList<>();
		listeners = new ArrayList<>();
	}
	
	public void addParser ( ILibParser parser) {
		parsers.add(parser);
	}
	
	public void search ( String keyword ) {
		// 페이지 정보가 없으면 0번째 페이지라고 가정함.
		search ( keyword, 0);
	}
	
	public void search ( String keyword , int pageIndex) {
		Session s = sessionManager.findSession(keyword);
		if ( s == null) {
			s = new Session(keyword, pageIndex);
			sessionManager.saveSession(keyword, s);
		}
		s.setPageIndex(pageIndex);
		searchWithSession(s);
	}
	
	private void searchWithSession ( Session session) {
		String keyword = session.getKeyword();
		
		Iterator<ILibParser> itr = parsers.iterator();
		ILibParser parser ;
		while ( itr.hasNext()) {
			/* FIXME
			 * 
			 * 모든 PARSER에게 검색 요청을 보내고 있음.
			 * 
			 * pagenation 정보를 추가했기 때문에 PARSER가 검색할 수 있는 
			 * 범위를 벗어난 경우에는 건너 뛰어야 함.
			 * 
			 * EX) P1 : [0 | 1 | 2 | 3 | 4 | 5 ]
			 *     P2 : [0 | 1 | 2 ]
			 *     p3 : [0 | 1 | 2 | 3 | 4]
			 *     
			 *     pageIndex가 4일 경우 P2는 생략함.
			 *     pageIndex가 5일 경우 P2, P3는 생략함.
			 */
			try {
				parser = itr.next();
				List<SearchResult> results = parser.parse(session);
				notifySearchResult(keyword, 
						results, 
						session, 
						session.getPageContext(parser) );
			} catch (LibParserException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void notifySearchResult(String keyword, 
			List<SearchResult> results, 
			Session session, 
			PageContext pctx) {
		List<SearchListener> cloned = null;
		synchronized (listeners) {
			cloned = new ArrayList<>(listeners);
		}
		LibSearchEvent event = new LibSearchEvent(keyword, session, pctx, results);
		for( int i = 0 ; i < cloned.size() ; i++) {
			cloned.get(i).searchResults(event);
		}
	}

	public void addSearchListener(SearchListener listener) {
		listeners.add(listener);
		
	}
}