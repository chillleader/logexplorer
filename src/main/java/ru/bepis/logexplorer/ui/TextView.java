package ru.bepis.logexplorer.ui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextView extends JPanel {

    TextView() {
        super();
        setLayout(new BorderLayout());
        this.add(new JTextField("Nothing to read yet"), BorderLayout.CENTER);
    }

}
