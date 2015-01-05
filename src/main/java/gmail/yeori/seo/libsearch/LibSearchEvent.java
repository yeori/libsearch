package gmail.yeori.seo.libsearch;

import java.util.List;

import gmail.yeori.seo.libsearch.engine.Session;
import gmail.yeori.seo.libsearch.engine.Session.PageContext;
import gmail.yeori.seo.libsearch.model.SearchResult;
/**
 * 검색 결과를 나타내는 이벤트
 * 
 * @author chminseo
 *
 */
/*
 * COMMENT
 * pagenation 요구사항을 추가하면서 SearchListener에게 전달해야할
 * 정보가 늘어났기 때문에 이 정보들을 메소드의 파라미터로 전달하면
 * 번거로워짐.
 *    
 *    기존 : 키워드 + 검색결과리스트
 *    변경 : 키워드 + 검색결과리스트 + 현재 페이지 + 전체 페이지 + 총 검색결과 등 (더 추가될 수 있음)
 * 
 * 리스너쪽 메소드 시그니처를 단순하게 하기 위해서 필요한 정보들을
 * 한군데 몰아넣을 목적으로 만들어진 클래스.
 */
public class LibSearchEvent {
	/*
	 * 페이지 번호(화면상에서는 +1 값으로 나타남)
	 */
	private int pageIndex;
	/*
	 * 검색어
	 */
	private String keyword;
	/*
	 * 현재 검색의 session
	 */
	private Session session ;
	/*
	 * 검색 결과 정보의 페이지 정보들
	 */
	private PageContext pctx;
	/*
	 * 검색 결과 리스트
	 */
	private List<SearchResult> results ;
	
	public LibSearchEvent(String keyword, Session session,
			PageContext pctx,
			List<SearchResult> results) {
		this.keyword = keyword;
		this.pctx = pctx;
		this.session = session;
		this.results = results;
	}
	public Session getSession() {
		return session;
	}
	public List<SearchResult> getSearchResults () {
		return this.results;
	}
	public String getKeyword() {
		return keyword;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public int getPageSize() {
		return pctx.getPageSize();
	}
	public int getTotalCount() {
		return pctx.getTotal();
	}
}
