package gmail.yeori.seo.libsearch.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import gmail.yeori.seo.libsearch.model.SearchResult;

/**
 * 도서 소장처를 알아내기 위해서 xhr 요청을 한번 더 보내야 함.
 * 그런데 isbn 정보가 없는 책의 경우 첫번째 요청의 응답 페이지 안에 
 * 소장처가 명시되어있기 때문에 xhr요청을 보낼 필요가 없음.
 * 
 * @author chminseo
 *
 */
public class EPLibParser extends AbstractParser {
	
	private String host = "www.eplib.or.kr";
	private String url = host + "/service/search.asp";
	private String locUrlTemplate = "/search/json_relay.asp" +
			"?cmd=LIB_BOOK_STATUS" +
			"&isbn=${isbn}" +
			"&recKey=${reckey}" +
			"&volCode=${volcode}";
	
	public Document loadDocument(String keyword) throws LibParserException {
		Connection conn = Jsoup.connect("http://" + url);
		conn.data("lib", "MA");
		conn.data("main", "Y");
		conn.data("kind", "");
		conn.data("txt", keyword);
		conn.data("x", "0");
		conn.data("y", "0");
		
		conn.method(Method.POST);
		
		try {
			return conn.get();
		} catch (IOException e) {
			throw new LibParserException("[EPLibParser error] fail to connect", e);
		}
	}

	@Override
	protected String loadHtml(String keyword) throws IOException {
		Connection conn = Jsoup.connect("http://" + url);
		conn.data("lib", "MA");
		conn.data("main", "Y");
		conn.data("kind", "");
		conn.data("txt", keyword);
		conn.data("x", "0");
		conn.data("y", "0");
		
		conn.method(Method.POST);
		return conn.get().html();
	}

	@Override
	protected List<SearchResult> parseInternal(String html) throws LibParserException {

		try {
			Document doc = Jsoup.parse(html);
			
			String cssBookNodes = ".result_list li";
			String cssBookImage = ".res_img img";
			String cssTitle = ".res_dl a";
			String cssAuthor = ".author";
			String cssDetailUrl = ".res_dl dt a";
			String jsGetLib = ".res_dl dd script";
			String cssLoc = ".res_dl dd #booklib${i}";
			String locUrl ;
			Elements aLi = doc.select(cssBookNodes);
			ArrayList<SearchResult> results = new ArrayList<>();
			int p0, p1, p2;
			Iterator<Element> itr = aLi.iterator();
			int index = 0;
			while ( itr.hasNext()) {
				Element li = itr.next();

				SearchResult item = new SearchResult();
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
				
				//
				
				String getLibScript = li.select(jsGetLib).outerHtml();
//				System.out.println(getLibScript);
				String location = "";
				if ( "".equals(getLibScript) ) {
					// isbn이 없는 책은 xhr요청을 보내는 script가 없음.
					// 대신 본문에 소장처가 삽입되어있음
					location = li.select(cssLoc.replace("${i}", ""+ index)).text();
					
				} else{					
					locUrl = replaceUrl(locUrlTemplate, getLibScript);
					location = loadLocation(host, locUrl); // li.select( tmp ).html();					
				}
				item.setLocationName(location);
				
				results.add(item);
				index ++ ;
			}
			
			return results;
		} catch (IOException e) {
			throw  new LibParserException("IOException occured", e);
		} catch (ParseException e) {
			throw new LibParserException("[JSON error]", e);
		}
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
//			System.out.println("[ERROR] " + getLibScript);
			throw e;
		}
	}
	
	private String loadLocation(String host, String locUrl) throws IOException, ParseException {
		String json = Jsoup.connect("http://" + host + locUrl).ignoreContentType(true).execute().body();
//		System.out.println( "=>" + host + locUrl);
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(json);
		JSONArray status = (JSONArray) obj.get("kind_data");
		
		return (String) ((JSONObject)status.get(0)).get("lib_status");
	}
	
	private String normailize(String url) {

		if (url.startsWith("/")) {
			return url;
		} else {
			return "/" + url;
		}
	}

}
