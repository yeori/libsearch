package gmail.yeori.seo.libsearch.parser;

import gmail.yeori.seo.libsearch.model.SearchResult;

import java.io.IOException;
import java.util.List;

public abstract class AbstractParser implements ILibParser {

	@Override
	public <T extends SearchResult> List<T> parse(String keyword)
			throws LibParserException {
		try {
			String html = loadHtml(keyword);
			return parseInternal( html );
		} catch (IOException e) {
			throw new LibParserException("fail to load html : " + keyword, e);
		}
	}
	
	protected abstract <T extends SearchResult> List<T> parseInternal(String html ) throws LibParserException;
	protected abstract String loadHtml(String keyword) throws IOException;
	

}
