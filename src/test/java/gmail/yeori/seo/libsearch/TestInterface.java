package gmail.yeori.seo.libsearch;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestInterface {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void eunpyeong_lib() {
		String host = "http://www.eplib.or.kr";
		String formUrl = "/service/search.asp";
		String url = host + formUrl;
		Map<String, String> params = initParams();
		Jsoup.connect(url).data(params);
		// "한글"
	}
	
	private Map<String, String> initParams() {
		HashMap<String, String> params = new HashMap<>();
		
		return params;
		
	}

}
