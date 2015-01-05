package gmail.yeori.seo.libsearch.parser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import gmail.yeori.seo.libsearch.engine.Session;
import gmail.yeori.seo.libsearch.engine.Session.PageContext;
import gmail.yeori.seo.libsearch.model.SearchResult;

import org.junit.Before;
import org.junit.Test;

public class TestEPLibParser {

	String host = "www.eplib.or.kr";
	String url =  host + "/service/search.asp";
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test_parseInternal() throws LibParserException, IOException {
		EPLibParser parser = new EPLibParser();
		int pageIndex = 0;
		Session session = new Session("자바", pageIndex);
		List<SearchResult> results = parser.parseInternal(loadSample("자바", pageIndex), session);
		assertEquals( 20, results.size());
		
		PageContext pgn = session.getPageContext(parser);
		assertEquals (20, pgn.getLpp());
		assertEquals (137, pgn.getTotal());
		assertEquals ( 7, pgn.getPageSize());

	}
	
	private String loadSample(String keyword, int pageIndex ) {
		String file = host.replace('.', '_') + "/" + keyword + "/" + (pageIndex+1) + ".html";
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("searchdata/" + file);
		Scanner scanner = new Scanner(in, "utf-8");
		StringBuilder sb = new StringBuilder();
		while ( scanner.hasNext()) {
			sb.append(scanner.nextLine());
			sb.append( System.getProperty("line.separator"));
		}
		scanner.close();
		return sb.toString();
	}

}
