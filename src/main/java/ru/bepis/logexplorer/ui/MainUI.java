package ru.bepis.logexplorer.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

// Singleton UI class
public class MainUI extends JFrame {

    private static MainUI INSTANCE;

    public static MainUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainUI();
        }
        return INSTANCE;
    }

    private JMenuBar menuBar;
    private JMenu fileMenu;

    private JPanel leftPanelSlot = new FileTreeView();
    private JPanel rightPanelSlot = new TextView();
    private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanelSlot,
        rightPanelSlot);


    private MainUI() {
        super("Log Explorer");
        this.setBounds(100, 100, 800, 800);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //splitPane.setDividerLocation(0.5);
        //splitPane.setResizeWeight(0.5);
        //splitPane.setOneTouchExpandable(true);
        leftPanelSlot.setMinimumSize(new Dimension(150, 400));
        rightPanelSlot.setMinimumSize(new Dimension(400, 400));
        //this.add(splitPane);
        this.add(leftPanelSlot);
        this.add(rightPanelSlot);
        pack();
        setVisible(true);
    }


}
