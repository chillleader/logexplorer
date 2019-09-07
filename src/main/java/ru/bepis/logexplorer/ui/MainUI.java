package ru.bepis.logexplorer.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
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

    private JMenuBar menuBar = new JMenuBar();
    private JMenu searchMenu = new JMenu("Menu");
    private JMenuItem newSearch = new JMenuItem("New search");
    private JMenuItem about = new JMenuItem("About");
    private JLabel chosenPath = new JLabel();
    private JFileChooser fileChooser = new JFileChooser();
    private JButton fileChooserButton = new JButton("Choose");

    private JPanel leftPanelSlot = new FileTreeView();
    private JPanel rightPanelSlot = new TextView();
    private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanelSlot,
        rightPanelSlot);

    private static Font font = new Font("Verdana", Font.PLAIN, 15);
    private static Font textFont = new Font("Lucida Console", Font.PLAIN, 13);

    private JTextField searchTemplate = new JTextField();
    private JTextField extension = new JTextField();
    private final JComponent[] inputs = new JComponent[]{
        new JLabel("Text to search"),
        searchTemplate,
        new JLabel("File extension (.log if empty)"),
        extension,
        chosenPath,
        fileChooserButton
    };

    static Font getAppFont() {
        return font;
    }

    static Font getTextFont() {
        return textFont;
    }

    private MainUI() {
        super("Log Explorer");
        this.setBounds(100, 100, 800, 800);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        newSearch.setFont(font);
        about.setFont(font);
        searchMenu.setFont(font);
        searchMenu.add(newSearch);
        searchMenu.add(about);
        menuBar.add(searchMenu);
        setJMenuBar(menuBar);
        for (JComponent c : inputs) {
            c.setFont(font);
        }
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setMultiSelectionEnabled(true);
        //splitPane.setDividerLocation(0.5);
        //splitPane.setResizeWeight(0.5);
        //splitPane.setOneTouchExpandable(true);
        leftPanelSlot.setMinimumSize(new Dimension(150, 400));
        rightPanelSlot.setMinimumSize(new Dimension(400, 400));
        //this.add(splitPane);
        this.add(leftPanelSlot);
        this.add(rightPanelSlot);

        fileChooserButton.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                chosenPath.setText("");
                for (File file : fileChooser.getSelectedFiles()) {
                    chosenPath.setText(chosenPath.getText() + file.getName() + ";");
                }
            }
        });

        newSearch.addActionListener(e -> {
            chosenPath.setText("Folders to search in");
            int result = JOptionPane.showConfirmDialog(null, inputs, "Start search",
                JOptionPane.DEFAULT_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                System.out.println(
                    "Searching " + searchTemplate.getText() + " in " + extension.getText());
            }
        });

        pack();
        setVisible(true);
    }


}
