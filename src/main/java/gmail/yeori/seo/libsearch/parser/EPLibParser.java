package gmail.yeori.seo.libsearch.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import gmail.yeori.seo.libsearch.model.SearchResult;

public class EPLibParser implements ILibParser {
	
	private String host = "www.eplib.or.kr";
	private String url = host + "/service/search.asp";
	
	@Override
	public List<SearchResult> parse(String keyword) throws LibParserException {
		Connection conn = Jsoup.connect("http://" + url);
		conn.data("lib", "MA");
		conn.data("main", "Y");
		conn.data("kind", "");
		conn.data("txt", keyword);
		conn.data("x", "0");
		conn.data("y", "0");
		
		conn.method(Method.POST);

		try {
			Document doc = conn.get();
			
			String cssBookNodes = ".result_list li";
			String cssBookImage = ".res_img img";
			String cssTitle = ".res_dl a";
			String cssAuthor = ".author";
			String cssDetailUrl = ".res_dl dt a";
			Elements aLi = doc.select(cssBookNodes);
			ArrayList<SearchResult> results = new ArrayList<>();

			Iterator<Element> itr = aLi.iterator();
			while ( itr.hasNext()) {
				Element li = itr.next();

				SearchResult item = new SearchResult();
				item.setThunmailUrl(li.select(cssBookImage).attr("src"));
				item.setTitle(li.select(cssTitle).text());
				
				String tmp = li.select(cssAuthor).text(); // '저자|출판사'
				item.setAuthor(tmp.substring(0, tmp.indexOf('|')));
				item.setPublisherName(tmp.substring(tmp.indexOf('|')+1));
				
				String detailUrl = url.substring(0, url.lastIndexOf('/')) + normailize(li.select(cssDetailUrl).attr("href"));
				item.setDetailUrl(detailUrl);
				
				
				results.add(item);
			}
			
			return results;
		} catch (IOException e) {
			throw  new LibParserException("IOException occured", e);
		}
	}
	
	private String normailize(String url) {

		if (url.startsWith("/")) {
			return url;
		} else {
			return "/" + url;
		}
	}

}
