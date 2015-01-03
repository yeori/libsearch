package gmail.yeori.seo.libsearch.model;

/**
 * 대출 가능 상태
 * STATUS.BORROWED    : 대출되었음
 * STATUS.AVAILABLE   : 도서관에 있음
 * STATUS.NOT_ALLOWED : 이용 불가능, 또는 대출 안되는 매체
 * STATUS.UNKNOWN     : 확인불가
 * @author chminseo
 *
 */
public enum STATUS {BORROWED, AVAILABLE, NOT_ALLOWED, UNKNOWN }