package ru.bepis.logexplorer.ui;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class FileTreeView extends JPanel {

    private JTree tree;
    private final String PLACEHOLDER_TEXT = "You will see search result here";

    FileTreeView(Map<String, List<Long>> searchResult) {
        super();
        setLayout(new BorderLayout());
        TreeNode root = new DefaultMutableTreeNode("Search results");
        tree = new JTree(root);
        tree.setFont(MainUI.getAppFont());

        for (String path : searchResult.keySet()) {
            addNode(path);
        }

        tree.addTreeSelectionListener(e -> {
            TreePath treepath = e.getPath();
            if (treepath.getParentPath() == null) {
                return;
            }
            System.out.println(treepath.getParentPath().getLastPathComponent());
            System.out.println(treepath.getLastPathComponent());
        });

        JScrollPane scrollpane = new JScrollPane();
        scrollpane.getViewport().add(tree);
        add(BorderLayout.CENTER, scrollpane);
    }

    FileTreeView() {
        super();
        add(new JLabel(PLACEHOLDER_TEXT));
    }

    void addNode(String pathString) {
        // split path
        String[] parts = pathString
            .split(Matcher.quoteReplacement(System.getProperty("file.separator")));
        DefaultMutableTreeNode curNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            stringBuilder.append(parts[i]);
            DefaultMutableTreeNode child = null;
            for (int j = 0; j < curNode.getChildCount(); j++) {
                if (curNode.getChildAt(j).toString().equals(parts[i])) {
                    child = (DefaultMutableTreeNode) curNode.getChildAt(j);
                    break;
                }
            }
            if (child == null) {
                child = new DefaultMutableTreeNode(parts[i]);
            }
            curNode.add(child);
            curNode = child;
            stringBuilder.append("\\");
        }
        tree.expandPath(
            tree.getPathForRow(tree.getRowCount() > 0 ? tree.getRowCount() - 1 : 0));
    }

}
