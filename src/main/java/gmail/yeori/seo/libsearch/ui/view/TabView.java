package gmail.yeori.seo.libsearch.ui.view;

import gmail.yeori.seo.libsearch.model.SearchResult;
import gmail.yeori.seo.libsearch.ui.IPageRequestAction;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
/**
 * <pre>
 * 오른쪽 tab pannel 의 swing component를 관리하는 view
 * 
 * 새로운 검색 요청이 들어올때마다 검색 결과를 보여줄 table과
 * pagenation 정보를 보여줄 page panel을 생성하고 추가함.
 * </pre>
 * 
 * @author chminseo
 *
 */
public class TabView implements IView<JPanel> {

	private ViewConfig config;
	private JTabbedPane tabbedPane;
	private JPanel pagePanel ;
	private IPageRequestAction action ;
	
	private Map<String, LibSearchTableModel<SearchResult>> tableModelMap = 
			new HashMap<String, TabView.LibSearchTableModel<SearchResult>>();
	
	public TabView ( ViewConfig config) {
		this.config = config;
		this.action = config.getPageActionHandler();
	}
	
	@Override
	public JPanel getViewComponent() {
		JPanel root = new JPanel();
		root.setLayout(new BorderLayout());
		tabbedPane= new JTabbedPane(JTabbedPane.TOP);
		root.add(tabbedPane, BorderLayout.CENTER);
		return root;
	}
	
	private LibSearchTableModel<SearchResult> findTableModel(String keyword) {
		return tableModelMap.get(keyword);
	}
	private void addTableModel( String keyword, LibSearchTableModel<SearchResult> model){
		tableModelMap.put(keyword, model);
	}
	/**
	 * <pre>
	 * 검색 결과를 화면에 보여줌(가운데 테이블, 아래쪽에 page 정보)
	 * 
	 * 에 메서드는 RunMain 에서 SearchEngine에 등록한 SearchListener에 의해서 호출됩니다.
	 * </pre>
	 * @see RunMain.#main(String[])
	 * @param keyword
	 * @param results
	 * @param pageIndex
	 * @param pageSize
	 */
	public void updateTable(String keyword, List<SearchResult> results, int pageIndex, int pageSize) {
		LibSearchTableModel<SearchResult> model = findTableModel(keyword);
		if ( model == null) {
			model = new LibSearchTableModel<>();
			// create new table-pagenation panel
			JPanel tablePanel = createTablePanel(model, keyword, pageIndex, pageSize);
			tabbedPane.addTab(keyword, null, tablePanel, keyword );			
			addTableModel ( keyword, model);
		}
		
		/* FIXME
		 * n개의 parser가 등록되면 updateTable 메소드는 n번 호출됨.
		 * 이러한 경우 n개의 검색 결과를 계속해서 출력해줘야 함.
		 * 여기서 테이블 모델을 초기화하는 clear() 를 호출하게되면
		 * 마지막 n-1번째 파서의 결과만 화면에 나타나게 됨.
		 * 
		 * 해결책[1]
		 * 
		 * 요청의 유형(type)을 기록하는 방식
		 * 요청을 구분하는 필드(새로운 검색 요청인지, 페이지 이동 요청인지)를 추가함.
		 * 
		 * 문제점 : search engine 인터페이스를 뜯어 고쳐야함.
		 *         : LibSearchEvent에도 요청의 유형을 반영하도록 고쳐야 함
		 *         : ILibParser 구현체들도 영향을 받을 수 있음
		 *         : view에서 해결해야할 문제를 다른 컴포넌트에 전가하고 있음.
		 * 
		 * 해결책[2]
		 * 
		 * page 검색 요청을 발생시킨 쪽에서 명시적으로 테이블 내용을 초기화 시켜줌.
		 * 
		 * 여기서는 PagePanel.sendPageRequest 메소드가 페이지 검색 요청을 보내기 때문에
		 * 이 메소드 안에서 테이블 내용을 초기화해주면 될 듯.
		 *  
		 */ 
//		model.clear();
		Iterator<SearchResult> itr = results.iterator();
		while ( itr.hasNext()) {
			SearchResult result = itr.next();
			model.addSearchResult(result);
		}
		
	}
	
	private JPanel createTablePanel(TableModel model, String keyword, int pageIndex, int pageSize){
		TableColumnModel columnModel = initColumnModel();
		JScrollPane scroll = new JScrollPane(new JTable(model, columnModel));
		
		// 가운데 : table, 아래 : pagePanel
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.add(scroll, BorderLayout.CENTER);
		tablePanel.add(createPagePanel(keyword, pageIndex, pageSize), BorderLayout.SOUTH);
		return tablePanel;
	}
	
	private TableColumnModel initColumnModel() {
		DefaultTableColumnModel columnModel = new DefaultTableColumnModel();
		//0책제목, 1 지은이, 2 출판사, 3 대출가능, 4 예약여부, 5 반납일, 6 소장처
		TableColumn tc = new TableColumn(0);
		tc.setHeaderValue("제목");
		columnModel.addColumn(tc);
		
		tc = new TableColumn(1);
		tc.setHeaderValue("지은이");
		columnModel.addColumn(tc);

		tc = new TableColumn(2);
		tc.setHeaderValue("출판사");
		columnModel.addColumn(tc);
		
		tc = new TableColumn(3);
		tc.setHeaderValue("대출가능");
		columnModel.addColumn(tc);
		
		tc = new TableColumn(4);
		tc.setHeaderValue("예약여부");
		columnModel.addColumn(tc);
		
		tc = new TableColumn(5);
		tc.setHeaderValue("반납일");
		columnModel.addColumn(tc);
		
		tc = new TableColumn(6);
		tc.setHeaderValue("소장처");
		columnModel.addColumn(tc);
		return columnModel;
		
	}
	

	public PagePanel createPagePanel(String keyword, int pageIndex, int pageSize) {
		PagePanel panel = new PagePanel(this, action, keyword, pageIndex, pageSize);
		return panel;
	}
	
	
	private static class LibSearchTableModel<T extends SearchResult> extends AbstractTableModel{
		private List<T> searchList = new ArrayList<>();
		
		public void addSearchResult(T result) {
			searchList.add(result);
			int index = searchList.size();
			fireTableRowsInserted(index, index);
		}
		
		public void clear() {
			int size = searchList.size();
			searchList.clear();
			fireTableRowsDeleted(0, size);
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return searchList.size();
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			//0책제목, 1지은이, 2출판사, 3대출가능, 4예약여부, 5반납일, 6소장처
			SearchResult result = searchList.get(rowIndex);
			String value = null;
			switch (columnIndex) {
			case 0:
				value = result.getTitle();
				break;
			case 1:
				value = result.getAuthor();
				break;
			case 2:
				value = result.getPublisherName();
				break;
			case 3:
				value = result.isBorrowable() ? "대출가능" : "대출중";
				break;
			case 4:
				value = result.isHoldable() ? "" : "예약가능";
				break;
			case 5:
				value = result.isBorrowable() ? "" : result.getDueDate();
				break;
			case 6:
				value = result.getLocationName();
				break;
			default:
				value = "out of range";
				break;
			}
			return value;
		}
		
	}
	
	/**
	 * 페이지 번호를 보여주는 panel
	 */
	private static class PagePanel extends JPanel {
		private List<JButton> buttons ;
		private int curPageIndex;
		private String keyword ;
		private IPageRequestAction action ;
		private TabView tabView;
		public PagePanel(TabView view, IPageRequestAction buttonAction, String keyword, int pageIndex, int pageSize) {
			this.tabView = view;
			this.action = buttonAction;
			this.keyword = keyword;
			initPageButtons(pageSize);
			curPageIndex = pageIndex;
			disableButtonAt(curPageIndex);
		}
		
		private void initPageButtons(int size) {
			buttons = new ArrayList<>();
			for (int i = 0; i < size ; i++) {				
				JButton btn = new JButton("" + (i+1));
				btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton btn = (JButton)e.getSource();
						sendPageRequest(btn);
					}
				});
				this.add(btn);
				buttons.add(btn);
			}
		}
		
		/*
		 * COMMENT 페이지 요청보내는 메소드
		 */
		private void sendPageRequest(JButton btn) {
			int pageIndex = Integer.parseInt(btn.getText().trim()) -1;
			disableButtonAt(pageIndex);
			tabView.findTableModel(keyword).clear();
			action.pageRequested(keyword, pageIndex);
			
		}
		
		public void disableButtonAt(int index) {			
			buttons.get(curPageIndex).setEnabled(true);
			buttons.get(index).setEnabled(false);
			curPageIndex = index;
		}
	}

}
