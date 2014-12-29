package gmail.yeori.seo.libsearch.ui.view;

import gmail.yeori.seo.libsearch.ui.SearchRequestListener;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FormView implements IView<JPanel> {
	private List<SearchRequestListener> listeners;
	private ViewConfig config;
	private JTextField field;
	public FormView ( ViewConfig config) {
		this.config = config;
		listeners = new ArrayList<>();
	}
	@Override
	public JPanel getViewComponent() {
		JPanel panel = new JPanel(true);
		panel.setLayout(new BorderLayout(10, 10));
		JLabel leftLabel = new JLabel("검색어");
		panel.add(leftLabel, BorderLayout.WEST);
		
		field = new JTextField();
		field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkEnterPressed(e);
			}
			
		});
		panel.add(field, BorderLayout.CENTER);
		return panel;
	}
	
	private void checkEnterPressed (KeyEvent e) {
		if ( e.getKeyCode() == KeyEvent.VK_ENTER){
			String keyword = field.getText();
			notifySearchRequest(keyword);
			field.setText("");
		}
	}
	
	private void notifySearchRequest(String keyword) {
		List<SearchRequestListener> cloned ;
		synchronized (listeners) {
			cloned = new ArrayList<>(listeners);
		}
		for( SearchRequestListener srl : cloned ) {
			srl.searchRequested(keyword);
		}
		
	}
	public void addSearchRequestListener(SearchRequestListener listener) {
		listeners.add(listener);
	}

}
