package gmail.yeori.seo.libsearch.parser;

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
		// TODO Auto-generated method stub
		return null;
	}


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
