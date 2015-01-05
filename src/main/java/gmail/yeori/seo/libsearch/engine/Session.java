package gmail.yeori.seo.libsearch.engine;

import java.util.HashMap;
import java.util.Map;

import gmail.yeori.seo.libsearch.parser.ILibParser;

public class Session {
	private int pageIndex;
	private String keyword;
	
	private Map<ILibParser, PageContext> pagingMap = new HashMap<>();
	public Session(String keyword, int pageIndex) {
		super();
		this.pageIndex = pageIndex;
		this.keyword = keyword;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public void setPageContext(int total, int pageSize, ILibParser parser) {
		PageContext pgn = pagingMap.get(parser);
		if ( pgn == null ){
			pgn = new PageContext();
			pagingMap.put(parser, pgn);
		}
		pgn.setTotal(total);
		pgn.setLpp(pageSize);
	}
	
	public static class PageContext {
		private int total;
		private int lpp ;
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public int getLpp() {
			return lpp;
		}
		public void setLpp(int lpp) {
			/*
			 * FIXME 맨 마지막 페이지는 lpp보다 작을 수 있음.
			 * 
			 * total(137), lpp(20)라면 맨 마지막 페이지에는 7개의 검색 결과가 존재함.
			 * lpp(7)로 변경하면 전체 page size는 20이 되어 문제 발생.
			 */
			this.lpp = lpp;
		}
		public int getPageSize() {
			return total/lpp + (total%lpp > 0 ? 1 : 0 );
		}
		/**
		 * 주어진 페이지가 유효한가?
		 * @param pageIndex - zero-base page index
		 * @return
		 */
		public boolean containsPage(int pageIndex) {
			return pageIndex < getPageSize();
		}
		
		
	}

	public PageContext getPageContext(ILibParser parser) {
		return pagingMap.get(parser);
	}
	
	
}
