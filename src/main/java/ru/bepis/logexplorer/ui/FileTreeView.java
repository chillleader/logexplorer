package ru.bepis.logexplorer.ui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileTreeView extends JPanel {

    FileTreeView() {
        super();
        setLayout(new BorderLayout());
        this.add(new JTextField("Nothing to read yet"), BorderLayout.CENTER);
    }

}
