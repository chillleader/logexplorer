package ru.bepis.logexplorer.ui;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TextView extends JPanel {

    private JTabbedPane tabbedPane = new JTabbedPane();
    private JLabel placeholder = new JLabel("You will see the file contents here");

    TextView() {
        setLayout(new BorderLayout());
        this.add(tabbedPane, BorderLayout.CENTER);
        tabbedPane.addTab("File 1", new TextTab());
    }

}
