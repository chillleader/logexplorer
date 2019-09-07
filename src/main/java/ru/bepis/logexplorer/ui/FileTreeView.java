package ru.bepis.logexplorer.ui;

import java.awt.BorderLayout;
import java.io.File;
import java.util.Collections;
import java.util.Vector;
import java.util.regex.Matcher;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class FileTreeView extends JPanel {

    private JTree tree;

    FileTreeView() {
        super();
        setLayout(new BorderLayout());
        TreeNode root = new DefaultMutableTreeNode("Search result");
        tree = new JTree(root);

        addNode("C:\\Users\\easyd\\Desktop");

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
                if (curNode.getChildAt(j).toString().equals(stringBuilder.toString())) {
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

    DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {

        String curPath = dir.getPath();
        DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
        if (curTop != null) { // should only be null at root
            curTop.add(curDir);
        }
        Vector ol = new Vector();
        String[] tmp = dir.list();
        for (int i = 0; i < tmp.length; i++) {
            ol.addElement(tmp[i]);
        }
        Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
        File f;
        Vector files = new Vector();
        // Make two passes, one for Dirs and one for Files. This is #1.
        for (int i = 0; i < ol.size(); i++) {
            String thisObject = (String) ol.elementAt(i);
            String newPath;
            if (curPath.equals(".")) {
                newPath = thisObject;
            } else {
                newPath = curPath + File.separator + thisObject;
            }
            if ((f = new File(newPath)).isDirectory()) {
                addNodes(curDir, f);
            } else {
                files.addElement(thisObject);
            }
        }
        // Pass two: for files.
        for (int fnum = 0; fnum < files.size(); fnum++) {
            curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
        }
        return curDir;
    }

}
