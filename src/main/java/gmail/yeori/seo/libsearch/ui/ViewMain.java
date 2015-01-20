package gmail.yeori.seo.libsearch.ui;

import gmail.yeori.seo.libsearch.SearchRequestListener;
import gmail.yeori.seo.libsearch.model.HOLDS;
import gmail.yeori.seo.libsearch.model.STATUS;
import gmail.yeori.seo.libsearch.model.SearchResult;
import gmail.yeori.seo.libsearch.ui.view.FormView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class ViewMain {
	/**
	 * @param args
	 */
	static LibSwingFrame window = null;

	private IPageRequestAction pageAction;
	public static void main(String[] args) {
		/*try {
			window = new LibSwingFrame("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			System.out.println("Look and Feel error");
			e.printStackTrace();
		}
		window.showFrame();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
//		window.showSearchResults("자바", loadTestResult());
	}

	
	public ViewMain (IPageRequestAction action)  {
		this.pageAction = action;
		showView();
		bindKeys();
	}
	private void bindKeys() {
		window.getRootPane().registerKeyboardAction(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						showFileDialog();
					}
				}, 
				KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK), 
				JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	private void showFileDialog() {
		window.openFilterDialog();
	}
	public void showView() {
		try {
			window = new LibSwingFrame("com.sun.java.swing.plaf.windows.WindowsLookAndFeel", pageAction);
		} catch (Exception e) {
			System.out.println("Look and Feel error");
			e.printStackTrace();
		}
		window.showFrame();
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}

	public static List<SearchResult> loadTestResult() {
		ArrayList<SearchResult> results = new ArrayList<>();
		results.add(new SearchResult(
				"thumnail-url", 
				"Enterprise Architecture", 
				"Martin Fowler", 
				"Publisher", 
				"http://www.naver.com", 
				STATUS.AVAILABLE, HOLDS.NO, 
				"0000-00-00 00:00:00", 
				"Eunpyeong", 
				"2001"));
		
		return results;
	}

	public void addSearchRequestListener(SearchRequestListener listener) {
		FormView view = window.getFormView();
		view.addSearchRequestListener( listener );
	}

	public void update(String keyword, List<SearchResult> results, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		window.showSearchResults(keyword, results, pageIndex, pageSize);
	}

	public void setPageAction(IPageRequestAction action) {
		this.pageAction = action;
	}


}
