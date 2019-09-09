package ru.bepis.logexplorer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class FileTreeView extends JPanel {

    private JTree tree;
    private TreeNode root;
    private final String PLACEHOLDER_TEXT = "You will see search result here";

    FileTreeView(Map<String, List<Long>> searchResult) {
        super();
        setLayout(new BorderLayout());
        root = new DefaultMutableTreeNode("Search results");
        tree = new JTree(root);
        tree.setFont(MainUI.getAppFont());

        for (String path : searchResult.keySet()) {
            addNode(path);
        }
        expandAll(tree, new TreePath(tree.getModel().getRoot()), true);

        tree.addTreeSelectionListener(e -> {
            TreePath treepath = e.getPath();
            if (treepath.getParentPath() == null) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < treepath.getPath().length; i++) {
                sb.append(treepath.getPath()[i]);
                if (i != treepath.getPath().length - 1) sb.append('\\');
            }
            File file = new File(sb.toString());
            if (!file.exists() || file.isDirectory()) return;
            System.out.println(sb.toString());
            MainUI.getInstance().openFile(sb.toString(), searchResult.get(sb.toString()));
        });

        JScrollPane scrollpane = new JScrollPane(tree);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(BorderLayout.CENTER, scrollpane);
    }

    FileTreeView() {
        super();
        JLabel label = new JLabel(PLACEHOLDER_TEXT);
        label.setFont(MainUI.getAppFont());
        add(label);
        setPreferredSize(new Dimension(300, 300));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    void addNode(String pathString) {
        // split path
        String[] parts = pathString
            .split(Matcher.quoteReplacement(System.getProperty("file.separator")));
        DefaultMutableTreeNode curNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
        DefaultMutableTreeNode child;
        for (int i = 0; i < parts.length; i++) {
            child = null;
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
        }
    }

    private void expandAll(JTree tree, TreePath path, boolean expand) {
        TreeNode node = (TreeNode) path.getLastPathComponent();

        if (node.getChildCount() >= 0) {
            Enumeration enumeration = node.children();
            while (enumeration.hasMoreElements()) {
                TreeNode n = (TreeNode) enumeration.nextElement();
                TreePath p = path.pathByAddingChild(n);

                expandAll(tree, p, expand);
            }
        }

        if (expand) {
            tree.expandPath(path);
        } else {
            tree.collapsePath(path);
        }
    }

}
