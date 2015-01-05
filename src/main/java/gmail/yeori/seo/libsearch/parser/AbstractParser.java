package gmail.yeori.seo.libsearch.parser;

import gmail.yeori.seo.libsearch.engine.Session;
import gmail.yeori.seo.libsearch.model.SearchResult;

import java.io.IOException;
import java.util.List;

public abstract class AbstractParser implements ILibParser {

	private String id;
	private String libraryUrl;
	
	protected AbstractParser( String id, String libUrl) {
		this.id = id;
		this.libraryUrl = libUrl;
	}
	
	
	@Override
	public String getLibUrl() {
		return libraryUrl;
	}


	@Override
	public String getPaserId() {
		return id;
	}


	@Override
	public <T extends SearchResult> List<T> parse(Session session )
			throws LibParserException {
		String keyword = session.getKeyword();
		int pageIndex = session.getPageIndex();
		try {
			String html = loadHtml(keyword, pageIndex);
			return parseInternal( html, session );
		} catch (IOException e) {
			throw new LibParserException("fail to load html : " + keyword, e);
		}
	}
	/* COMMENT
	 * 
	 * 기존에는 parse 메소드 한군데에서 검색 결과를 받아오고
	 * 검색 정보를 추출하는 두가지 일을 다 했었음.
	 * 
	 * 테스트가 쉽지 않은 문제점 발생
	 * 
	 * 1. 매번 테스트할때마다 http 요청을 각 도서관 서버로 보내는데
	 *    네트워크 상태가 좋지 않으면 테스트 시간이 길어질 수 있다.
	 * 2. 도서관 검색 페이지를 수정하지 않는다면 동일한 키워드에 대한 검색 내용은
	 *    항상 동일함. 이 내용을 파일로 저장해서 테스트 하는 것이 더 낫지 않을까?
	 *    
	 * 그런데 Jsoup에서 로컬 파일에 접근하는 메소드와 http 요청을 보내서 응답을 처리하는
	 * 메소드가 서로 다름. 
	 * 
	 * 테스트할때는 Jsoup.parse(...)를 쓰고(로컬 테스트 샘플을 읽음) 
	 * 
	 * 실제 배포 버전에서는 Jsoup.connect(...) 를 써야 함(http요청을 보내서 읽어옴)
	 * 
	 * 이런 요구사항을 반영하기 위해서 loadHtml과 parseInternal 로 상세 구현을 또다시 추상화.
	 * 
	 * FIXME loadHtml 의 반환타입과 parseInternal의 매개변수 타입을 String으로 한 것은 적절한가?
	 */
	protected abstract String loadHtml(String keyword, int pageIndex) throws IOException;
	protected abstract <T extends SearchResult> List<T> parseInternal(String html, Session session)
			throws LibParserException;
	

}
