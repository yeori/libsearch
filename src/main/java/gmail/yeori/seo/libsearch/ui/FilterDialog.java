package gmail.yeori.seo.libsearch.ui;

import gmail.yeori.seo.libsearch.engine.IFilter;
import gmail.yeori.seo.libsearch.engine.filter.PubYearFilter;
import gmail.yeori.seo.libsearch.ui.view.ViewConfig;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JCheckBox;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class FilterDialog extends JDialog {
	private ViewConfig config; 
	private JComboBox cbMethod;
	private JTextField tfYear;
	private JCheckBox chckbxPublishingYear;
	public FilterDialog(JFrame parent, ViewConfig config) {
		super(parent, true);
		this.config = config;		
		
		initComponents();
		renderFilter ( config.getFilters());
	}
	
	private void initComponents() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.weighty = 1.0;
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.weightx = 1.0;
		gbc_panel.gridy = 0;
		gbc_panel.gridx = 0;
		getContentPane().add(createPubYearPanel(), gbc_panel);
	}

	private JPanel createPubYearPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0, 0};
		gbl_panel.rowHeights = new int[] {0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0};
		panel.setLayout(gbl_panel);
		
		chckbxPublishingYear = new JCheckBox("publishing year");
		chckbxPublishingYear.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				JCheckBox check = (JCheckBox) e.getSource();
				if ( check.isSelected()) {
					enablePubYearControll();
				} else {
					disablePubYearControll();
				}
				
			}
		});
		GridBagConstraints gbc_chckbxPublishingYear = new GridBagConstraints();
		gbc_chckbxPublishingYear.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxPublishingYear.gridx = 0;
		gbc_chckbxPublishingYear.gridy = 0;
		panel.add(chckbxPublishingYear, gbc_chckbxPublishingYear);
		ComboBoxModel<String> model = new DefaultComboBoxModel<>(new String[]{"eq", "ne", "gt", "lt", "ge", "le"});
		cbMethod = new JComboBox(model);
		cbMethod.setEnabled(false);
		GridBagConstraints gbc_cbMethod = new GridBagConstraints();
		gbc_cbMethod.anchor = GridBagConstraints.NORTH;
		gbc_cbMethod.weighty = 1.0;
		gbc_cbMethod.insets = new Insets(0, 0, 0, 5);
		gbc_cbMethod.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbMethod.gridx = 0;
		gbc_cbMethod.gridy = 1;
		panel.add(cbMethod, gbc_cbMethod);
		tfYear = new JTextField();
		tfYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("ok?");
				installFilter();
			}
		});
		tfYear.setEnabled(false);
		GridBagConstraints gbc_tfYear = new GridBagConstraints();
		gbc_tfYear.anchor = GridBagConstraints.NORTH;
		gbc_tfYear.weighty = 1.0;
		gbc_tfYear.insets = new Insets(0, 0, 0, 5);
		gbc_tfYear.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfYear.gridx = 1;
		gbc_tfYear.gridy = 1;
		panel.add(tfYear, gbc_tfYear);
		tfYear.setColumns(10);
		return panel;
		
	}
	
	private void renderFilter ( List<IFilter> filters) {
		if ( filters == null || filters.size() == 0 ) {
			disablePubYearControll();
		} else {
			chckbxPublishingYear.setSelected(true);
			PubYearFilter filter = (PubYearFilter) filters.get(0);
			tfYear.setText("" + filter.getYear());
			cbMethod.setSelectedItem(filter.getComparingMethod().key());
		}
	}
	
	private void enablePubYearControll(){
		cbMethod.setEnabled(true);
		tfYear.setEnabled(true);
		tfYear.setEditable(true);
	}
	private void disablePubYearControll() {
		cbMethod.setEnabled(false);
		tfYear.setEditable(false);
		config.clearFilters();
	}
	private void installFilter () {
		String method = (String) cbMethod.getSelectedItem();
		String strYear = tfYear.getText();
		int year = 0;
		try {
			year = Integer.parseInt(strYear);
			config.clearFilters();
			config.addFilter(new PubYearFilter(year, method));
		} catch ( NumberFormatException nfE ) {
			tfYear.requestFocus();
		}
	}
	
	
}