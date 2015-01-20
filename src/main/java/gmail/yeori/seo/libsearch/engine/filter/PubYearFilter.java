package gmail.yeori.seo.libsearch.engine.filter;

import java.util.HashMap;
import java.util.Map;

import gmail.yeori.seo.libsearch.engine.IFilter;
import gmail.yeori.seo.libsearch.model.SearchResult;

public class PubYearFilter implements IFilter {
	public static enum METHOD {
		EQ{
			@Override
			public String key() {
				return "eq";
			}
			@Override
			boolean compare(int year, SearchResult result) {
				return Integer.parseInt(result.getPublishingYear()) == year;
			}
		},
		NE {
			@Override
			public String key() {
				return "ne";
			}
			@Override
			boolean compare(int year, SearchResult result) {
				return year != Integer.parseInt(result.getPublishingYear());
			}
		}, 
		LT {
			@Override
			public String key() {
				return "lt";
			}
			@Override
			boolean compare(int year, SearchResult result) {
				return Integer.parseInt(result.getPublishingYear()) < year;
			}
		},
		GT{
			@Override
			public String key() {
				return "gt";
			}
			@Override
			boolean compare(int year, SearchResult result) {
				return Integer.parseInt(result.getPublishingYear()) > year ;
			}
		}, 
		LE {
			@Override
			public String key() {
				return "le";
			}
			@Override
			boolean compare(int year, SearchResult result) {
				return Integer.parseInt(result.getPublishingYear()) <= year ;
			} 
		},
		GE {
			@Override
			public String key() {
				return "ge";
			}
			@Override
			boolean compare(int year, SearchResult result) {
				return Integer.parseInt(result.getPublishingYear()) >= year ;
			}
		};
		public abstract String key ( ) ;
		abstract boolean compare (int year, SearchResult result);
	};
	private int year;
	private METHOD method ;
	private static Map<String, METHOD> methodMap = new HashMap<>();
	
	static {
		methodMap.put(METHOD.EQ.key(), METHOD.EQ);
		methodMap.put(METHOD.NE.key(), METHOD.NE);
		methodMap.put(METHOD.LT.key(), METHOD.LT);
		methodMap.put(METHOD.GT.key(), METHOD.GT);
		methodMap.put(METHOD.LE.key(), METHOD.LE);
		methodMap.put(METHOD.GE.key(), METHOD.GE);
	}
	public PubYearFilter ( int year) {
		this.year = year;
		this.method = METHOD.EQ;
	}
	
	public PubYearFilter( int year, METHOD method) {
		this.year = year;
		this.method = method;
	}
	
	public PubYearFilter(int year, String methodKey) {
		this.year = year;
		this.method =  findByKey(methodKey);
		if ( method == null) {
			throw new RuntimeException("unknown filter method: " + methodKey);
		}
	}
	
	private METHOD findByKey(String key) {
		return methodMap.get(key);
	}

	@Override
	public boolean accept(SearchResult sr) {
		return method.compare(year, sr) ;
	}
	
	public int getYear() {
		return year;
	}
	
	public METHOD getComparingMethod() {
		return this.method;
	}

}
