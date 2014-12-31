package gmail.yeori.seo.libsearch.parser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import gmail.yeori.seo.libsearch.model.SearchResult;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TestEPLibParser {

	String host = "www.eplib.or.kr";
	String url =  host + "/service/search.asp";
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testParse() throws LibParserException, IOException {
		EPLibParser parser = new EPLibParser();
		
		List<SearchResult> results = parser.parseInternal(loadSample("자바"));
		assertEquals( 20, results.size());
		for (Iterator iterator = results.iterator(); iterator.hasNext();) {
			SearchResult searchResult = (SearchResult) iterator.next();
//			System.out.println(searchResult);
		}
	}
	
	private String loadSample(String keyword) {
		String file = host.replace('.', '_') + "/" + keyword + ".html";
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
