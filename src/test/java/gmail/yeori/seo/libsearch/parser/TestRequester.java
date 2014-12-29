package gmail.yeori.seo.libsearch.parser;

import static org.junit.Assert.*;

import gmail.yeori.seo.libsearch.model.SearchResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

public class TestRequester {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws IOException {
		String host = "www.eplib.or.kr";
		String url =  host + "/service/search.asp";
		String file = url.replace('/', '$') + ".html";
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("searchdata/" + file);
		
//		Connection conn = Jsoup.connect(url);
//		conn.data("lib", "MA");
//		conn.data("main", "Y");
//		conn.data("kind", "");
//		conn.data("txt", "총균쇠");
//		conn.data("x", "0");
//		conn.data("y", "0");
//		
//		conn.method(Method.POST);
//		Document doc = conn.get();
		
		Document doc = Jsoup.parse(in, "utf-8", "");
		
		String cssBookNodes = ".result_list li";
		String cssBookImage = ".res_img img";
		String cssTitle = ".res_dl a";
		String cssAuthor = ".author";
		String cssDetailUrl = ".res_dl dt a";
		String cssLoc = ".res_dl dd p#booklib${idx} span";
		
		Elements aLi = doc.select(cssBookNodes);
		Iterator<Element> itr = aLi.iterator();
		SearchResult item = new SearchResult();
		int idx = 0;
		while ( itr.hasNext()) {
			Element li = itr.next();
			System.out.println("image  : " + li.select(cssBookImage).attr("src"));
			item.setThunmailUrl(li.select(cssBookImage).attr("src"));

			System.out.println("title  : " + li.select(cssTitle).text());
			item.setTitle(li.select(cssTitle).text());
			
			String tmp = li.select(cssAuthor).text(); // '저자|출판사'
			System.out.println("author : " + tmp.substring(0, tmp.indexOf('|')));
			System.out.println("press  : " + tmp.substring(tmp.indexOf('|')+1));
			item.setAuthor(tmp.substring(0, tmp.indexOf('|')));
			item.setPublisherName(tmp.substring(tmp.indexOf('|')+1));
			
			String detailUrl = url.substring(0, url.lastIndexOf('/')) + normailize(li.select(cssDetailUrl).attr("href"));
			System.out.println("detail : " + detailUrl);
			item.setDetailUrl(detailUrl);

			/*
				소장처 : json 요청을 보내야 함.
			
				Closure
				booklocation: "booklib0"
				isbn: "8990109140"
				reckey: "70156274"
				volcode: ""

				url    : '/search/json_relay.asp' 
				params :'cmd=LIB_BOOK_STATUS&isbn='+ isbn +'&recKey='+ reckey +'&volCode='+ volcode +''
			 */
			tmp = cssLoc.replace("${idx}", String.valueOf(idx));
			String location = li.select( tmp ).html();
			System.out.println("location css : " + tmp);
			System.out.println("location : " + location);
//			if ( !itr.hasNext() ){
//				System.out.println("====");
//				String params = detailUrl.substring(detailUrl.indexOf('?')+1);
//				String [] dd = params.split("[=&]");
//				System.out.println(Jsoup.connect("http://" + detailUrl.substring(0, detailUrl.indexOf('?')))
//						.data(dd).get()
//						.body().html());
//				System.out.println("====");				
//			}
			/* 소장 정보*/
			
			System.out.println(item);
			idx ++ ;
		}
	}
	private String normailize ( String url ){
		
		if ( url.startsWith("/")) {
			return url;
		} else {
			return "/" + url;
		}
	}

}
