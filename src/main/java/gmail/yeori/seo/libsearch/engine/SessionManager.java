package gmail.yeori.seo.libsearch.engine;

import java.util.HashMap;
import java.util.Map;
/**
 * session을 관리하는 역할을 함.
 * @author chminseo
 *
 */
class SessionManager {
	// 검색어마다 하나의 Session 인스턴스가 대응됨. 
	private Map<String, Session> sessions = new HashMap<>();
	
	public Session findSession(String id) {
		return sessions.get(id);
	}

	public void saveSession(String keyword, Session session) {
		sessions.put(keyword, session);
	}
}
