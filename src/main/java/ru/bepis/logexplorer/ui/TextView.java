package ru.bepis.logexplorer.ui;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TextView extends JPanel {

    private JTabbedPane tabbedPane = new JTabbedPane();
    private JLabel placeholder = new JLabel("You will see the file contents here");

    TextView() {
        tabbedPane.setFont(MainUI.getAppFont());
        setLayout(new BorderLayout());
        this.add(tabbedPane, BorderLayout.CENTER);
        tabbedPane.addTab("File 1", new TextTab("C:/Users/easyd/Desktop/test1/alice29.txt"));
    }

    public void openFile(String path) {
        tabbedPane.addTab("file", new TextTab(path));
    }

}
