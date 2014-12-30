package gmail.yeori.seo.libsearch.parser;

import static org.junit.Assert.*;

import gmail.yeori.seo.libsearch.model.SearchResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
	public void test() throws IOException, ParseException {
		String host = "www.eplib.or.kr";
		String url =  host + "/service/search.asp";
		String file = host.replace('.', '_') + "/" + "자바.html";
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
//		String cssLoc = ".res_dl dd p#booklib${idx} span"; xhr요청
		String jsGetLib = ".res_dl dd script";
		Elements aLi = doc.select(cssBookNodes);
		Iterator<Element> itr = aLi.iterator();
		SearchResult item = new SearchResult();
		int p0, p1, p2;
		while ( itr.hasNext()) {
			Element li = itr.next();
			item.setThunmailUrl(li.select(cssBookImage).attr("src"));

			item.setTitle(li.select(cssTitle).text());
			
			String tmp = li.select(cssAuthor).text(); // '저자|출판사(yyyy)'
			p0 = tmp.indexOf('|');
			p1 = tmp.indexOf('(');
			p2 = tmp.indexOf(')');
			item.setAuthor(tmp.substring(0, p0));
			item.setPublisherName(tmp.substring(p0+1, p1));
			item.setPublishingYear(tmp.substring(p1+1, p2));
			
			String detailUrl = url.substring(0, url.lastIndexOf('/')) + normailize(li.select(cssDetailUrl).attr("href"));
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
				
				getLib("booklib0","49557","8956741093","1797116","");
				
				function getLib(booklocation,bookid,isbn,reckey,volcode){
					
				}
			 */
			String locUrl = "/search/json_relay.asp?cmd=LIB_BOOK_STATUS&isbn=${isbn}&recKey=${reckey}&volCode=${volcode}";
			
			String getLibScript = li.select(jsGetLib).outerHtml();
			locUrl = replaceUrl(locUrl, getLibScript);
			
			String location = loadLocation(host, locUrl); // li.select( tmp ).html();
			item.setLocationName(location);
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
		}
	}
	
	/* 
	 * parsing json response
	 * 
	 * http://stackoverflow.com/questions/7133118/jsoup-requesting-json-response
	 * 
	 * {"kind_data":[{"lib_status":"은평/"}]}
	 */
	private String loadLocation(String host, String locUrl) throws IOException, ParseException {
		String json = Jsoup.connect("http://" + host + locUrl).ignoreContentType(true).execute().body();
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(json);
		JSONArray status = (JSONArray) obj.get("kind_data");
		
		return (String) ((JSONObject)status.get(0)).get("lib_status");
	}
	/**
	 * getLib("booklib0","49557","8956741093","1797116","");
	 * @param locUrl
	 * @param getLibScript
	 * @return
	 */
	private String replaceUrl(String locUrl, String getLibScript) {
		try {
			int p0 = getLibScript.indexOf('(') + 1;
			int p1 = getLibScript.indexOf(')', p0);
			String paramStr = getLibScript.substring(p0, p1); // "booklib0","49557","8956741093","1797116",""
			
			String [] params = paramStr.replace('"', '\0').split("[,]");
			String url = locUrl.replace("${isbn}", params[2].trim());
			url = url.replace("${reckey}", params[3].trim());
			url = url.replace("${volcode}", params[4].trim());
			return url;
		} catch (Exception e) {
			System.out.println("[ERROR] " + getLibScript);
			throw e;
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
