package gmail.yeori.seo.libsearch.main;

public class RunMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("test run");
		
		LibraryEngine engine = new LibraryEngine(
				"http://www.eplib.or.kr",
				"/service/search.asp",
				"POST");
//		engine.setUrl("");

	}
	
	public static class LibraryEngine {
		private String host;
		private String formUrl;
		private String method;
		public LibraryEngine(String host, String formUrl, String method) {
			// TODO Auto-generated constructor stub
		}
		
	}

}
