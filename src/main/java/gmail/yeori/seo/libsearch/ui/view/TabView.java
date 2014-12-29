package gmail.yeori.seo.libsearch.ui.view;

import gmail.yeori.seo.libsearch.model.SearchResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class TabView implements IView<JTabbedPane> {

	private ViewConfig config;
	private JTabbedPane tabbedPane;
	public TabView ( ViewConfig config) {
		this.config = config;
	}
	
	@Override
	public JTabbedPane getViewComponent() {
		tabbedPane= new JTabbedPane(JTabbedPane.TOP);
		return tabbedPane;
	}
	
	public void updateTable(String keyword, List<SearchResult> results) {
		LibSearchTableModel<SearchResult> model = new LibSearchTableModel<>();
		TableColumnModel columnModel = initColumnModel();
		JTable searchTable = new JTable(model, columnModel);
		
		JScrollPane scroll = new JScrollPane(searchTable);
		
		Iterator<SearchResult> itr = results.iterator();
		while ( itr.hasNext()) {
			SearchResult result = itr.next();
			model.addSearchResult(result);
		}
		
		tabbedPane.addTab(keyword, null, scroll, "OK tip");
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
	
	private static class LibSearchTableModel<T extends SearchResult> extends AbstractTableModel{
		private List<T> searchList = new ArrayList<>();
		
		public void addSearchResult(T result) {
			searchList.add(result);
			int index = searchList.size();
			fireTableRowsInserted(index, index);
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

}
