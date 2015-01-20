package gmail.yeori.seo.libsearch.ui;

import gmail.yeori.seo.libsearch.model.SearchResult;
import gmail.yeori.seo.libsearch.ui.view.FormView;
import gmail.yeori.seo.libsearch.ui.view.TabView;
import gmail.yeori.seo.libsearch.ui.view.TreeView;
import gmail.yeori.seo.libsearch.ui.view.ViewConfig;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.JTabbedPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LibSwingFrame extends JFrame {

	private JPanel contentPane;
	
	private FormView formView;
	private TreeView treeView;
	private TabView tabView;

	private ViewConfig config;
	
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LibSwingFrame frame = new LibSwingFrame("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public LibSwingFrame(String lnfName, IPageRequestAction actionHandler) throws Exception {
		config = new ViewConfig();
		config.setPageActionHandler ( actionHandler);
		formView = new FormView(config); // 검색창
		treeView = new TreeView(config); // 왼쪽창
		tabView = new TabView(config); // 오른쪽 탭패널
		
		UIManager.setLookAndFeel(lnfName);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmTab = new JMenuItem("Tab");
		mntmTab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnFile.add(mntmTab);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		contentPane.add(formView.getViewComponent(), BorderLayout.NORTH);

		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		splitPane.setLeftComponent(treeView.getViewComponent());
		splitPane.setRightComponent(tabView.getViewComponent());
	}

	public void showFrame() {
		this.setVisible(true);
		System.out.println("frame init and shown");
	}

	public void showSearchResults(String searchWord, List<SearchResult> results, int pageIndex, int pageSize) {
		treeView.addKeyword(searchWord);
		tabView.updateTable(searchWord, results, pageIndex, pageSize);
	}

	public FormView getFormView() {
		return formView;
		
	}

	public void openFilterDialog() {
		// TODO Auto-generated method stub
		FilterDialog dialog = new FilterDialog(this, config);
		dialog.setSize(350, 250);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}
}
