package gmail.yeori.seo.libsearch.parser;

import java.util.List;

import gmail.yeori.seo.libsearch.model.SearchResult;
/**
 * 도서관에 요청을 보내고 응답을 SearchResult로 반환하는 파서의 최상위 인터페이스
 * @author chminseo
 *
 */
public interface ILibParser {
	public <T extends SearchResult>   List<T> parse(String keyword) throws LibParserException;
}
