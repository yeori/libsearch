package gmail.yeori.seo.libsearch.ui.view;


import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class TreeView implements IView<JScrollPane> {
	private ViewConfig config ;
	private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
	private JTree keywordTree;
	private KeywordTreeModel treeModel;
	
	public TreeView ( ViewConfig config) {
		this.config = config;
		rootNode.setUserObject("검색어");
		rootNode.setAllowsChildren(true);
	}
	
	@Override
	public JScrollPane getViewComponent() {
		keywordTree = initTree();
		return new JScrollPane(keywordTree);		
	}
	
	private JTree initTree( ) {
		treeModel = new KeywordTreeModel(rootNode);
		return new JTree(treeModel);
	}
	
	public void addKeyword(String keyword) {
		DefaultMutableTreeNode keywordNode = new DefaultMutableTreeNode();
		keywordNode.setAllowsChildren(false);
		keywordNode.setUserObject(keyword);
		MutableTreeNode root = (MutableTreeNode) treeModel.getRoot();
		treeModel.insertNodeInto(keywordNode, root, 0);
	}
	
	private static class KeywordTreeModel extends DefaultTreeModel {

		public KeywordTreeModel(TreeNode root) {
			super(root, true);
			
		}
		
	}

}
