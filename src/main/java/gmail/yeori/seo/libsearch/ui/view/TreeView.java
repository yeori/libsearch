package gmail.yeori.seo.libsearch.ui.view;


import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class TreeView implements IView<JScrollPane> {
	private ViewConfig config ;
	private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
	private JTree keywordTree;
	private KeywordTreeModel treeModel;
	
	public TreeView ( ViewConfig config) {
		this.config = config;
		config.registerView("treeview", this);
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
		DefaultMutableTreeNode keywordNode = findByNodeName(keyword);
		if ( keywordNode != null ) {
			TreeNode [] paths = treeModel.getPathToRoot(keywordNode);
			keywordTree.setSelectionPath(new TreePath(paths));
			return ;
		}
		keywordNode = new DefaultMutableTreeNode();
		keywordNode.setAllowsChildren(false);
		keywordNode.setUserObject(keyword);
		MutableTreeNode root = (MutableTreeNode) treeModel.getRoot();
		treeModel.insertNodeInto(keywordNode, root, 0);
	}
	
	private DefaultMutableTreeNode findByNodeName(String keyword) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
		int cnt = treeModel.getChildCount(root);
		for ( int i = 0 ; i < cnt ; i++ ) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
			if ( child.getUserObject().equals(keyword) ){
				return child;
			}
		}
		return null;
	}

	private static class KeywordTreeModel extends DefaultTreeModel {

		public KeywordTreeModel(TreeNode root) {
			super(root, true);
			
		}
		
	}

}
