package ru.bepis.logexplorer.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TextView extends JPanel {

    static JTabbedPane tabbedPane = new JTabbedPane();
    private List<Long> placeholderList = new ArrayList<>();
    private String placeholderText = "Text";

    TextView() {
        tabbedPane.setFont(MainUI.getAppFont());
        setLayout(new BorderLayout());
        this.add(tabbedPane, BorderLayout.CENTER);
        placeholderList.add(0L);
        placeholderList.add(50L);
        tabbedPane.addTab("Start Page",
            new TextTab("C:/Users/easyd/Desktop/test1/alice29.txt", placeholderList));
    }

    public void openFile(String path, List<Long> occurrences) {
        tabbedPane.addTab(path, new TextTab(path, occurrences));
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
    }

}
