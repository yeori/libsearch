package gmail.yeori.seo.libsearch.model;

/**
 * 도서관 검색 결과를 나타내는 클래스.
 * 
 * @author chminseo
 *
 */
public class SearchResult {
	/**
	 * 대출 가능 상태
	 * STATUS.BORROWED    : 대출되었음
	 * STATUS.AVAILABLE   : 도서관에 있음
	 * STATUS.NOT_ALLOWED : 이용 불가능, 또는 대출 안되는 매체
	 * STATUS.UNKNOWN     : 확인불가
	 * @author chminseo
	 *
	 */
	public enum STATUS {BORROWED, AVAILABLE, NOT_ALLOWED, UNKNOWN };
	
	/**
	 * 예약 상태
	 * HOLDS.YES     : 예약자 있음
	 * HOLDS.NO      : 예약자 없음
	 * HOLDS.UNKNOWN : 확인불가
	 * @author chminseo
	 *
	 */
	public enum HOLDS { YES, NO, UNKNOWN};
	
	/**
	 * 책 이미지 URL
	 */
	private String thunmailUrl;
	/**
	 * 책 제목
	 */
	private String title;
	/**
	 * 책 지은이(저자, 옮김, 각색, 그림 등)
	 */
	private String author;
	/**
	 * 출판사
	 */
	private String publisherName; // 출판사
	/**
	 * 상세 페이지 URL
	 */
	private String detailUrl;
	/**
	 * 책의 대출 상태
	 */
	private STATUS borrowingStatus = STATUS.UNKNOWN;
	/**
	 * 책의 예약 상태
	 */
	private HOLDS holdingStatus = HOLDS.UNKNOWN;
	/**
	 * 반납일(대출된 상태일 경우에만 값이 존재)
	 */
	private String dueDate ;
	/**
	 * 소장처
	 */
	private String locationName;
	/**
	 * 출판년
	 */
	private String publishingYear = "0000";
	/**
	 * 현재 시간을 기준으로 대출 가능 여부
	 * @return
	 */
	public boolean isBorrowable(){
		// 이미 대출 중이면 불가능
		if ( borrowingStatus == STATUS.BORROWED || borrowingStatus == STATUS.UNKNOWN){
			return false;
		}
		// 대출 중이 아니더라도 예약자가 있으면 불가능
		if ( holdingStatus == HOLDS.YES ) {
			return false;
		}
		
		return true;
	}
	/**
	 * 예약 가능 여부
	 * @return
	 */
	public boolean isHoldable() {
		return borrowingStatus == STATUS.BORROWED;
	}
	
	public SearchResult(){
		
	}
	public SearchResult(String thunmailUrl, String title, String author,
			String publisherName, String detailUrl, STATUS borrowingStatus,
			HOLDS holdingStatus, String dueDate, String locationName,
			String publishingYear) {
		super();
		this.thunmailUrl = thunmailUrl;
		this.title = title;
		this.author = author;
		this.publisherName = publisherName;
		this.detailUrl = detailUrl;
		this.borrowingStatus = borrowingStatus;
		this.holdingStatus = holdingStatus;
		this.dueDate = dueDate;
		this.locationName = locationName;
		this.publishingYear = publishingYear;
	}
	/* start : getter/setter */
	public String getThunmailUrl() {
		return thunmailUrl;
	}
	public void setThunmailUrl(String thunmailUrl) {
		this.thunmailUrl = thunmailUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	public STATUS getBorrowingStatus() {
		return borrowingStatus;
	}
	public void setBorrowingStatus(STATUS borrowingStatus) {
		this.borrowingStatus = borrowingStatus;
	}
	public HOLDS getHoldingStatus() {
		return holdingStatus;
	}
	public void setHoldingStatus(HOLDS holdingStatus) {
		this.holdingStatus = holdingStatus;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getPublishingYear() {
		return publishingYear;
	}
	public void setPublishingYear(String publishingYear) {
		this.publishingYear = publishingYear;
	}
	@Override
	public String toString() {
		return "SearchItem [thunmailUrl=" + thunmailUrl + ", title=" + title
				+ ", author=" + author + ", publisherName=" + publisherName
				+ ", detailUrl=" + detailUrl + ", borrowingStatus="
				+ borrowingStatus + ", holdingStatus=" + holdingStatus
				+ ", dueDate=" + dueDate + ", locationName=" + locationName
				+ ", publishingYear=" + publishingYear + "]";
	}
	
	
}
