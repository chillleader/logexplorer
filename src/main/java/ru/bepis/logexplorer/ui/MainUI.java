package ru.bepis.logexplorer.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker.StateValue;
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

    private FileTreeView leftPanelSlot = new FileTreeView();
    private TextView rightPanelSlot = new TextView();
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

    // font for labels, menus etc
    static Font getAppFont() {
        return font;
    }

    // font for text in file view
    static Font getTextFont() {
        return textFont;
    }

    private MainUI() {
        super("Log Explorer");
        this.setBounds(100, 100, 800, 800);
        this.setLayout(new BorderLayout());
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
        //leftPanelSlot.setMinimumSize(new Dimension(250, 400));
        rightPanelSlot.setMinimumSize(new Dimension(300, 400));
        //this.add(splitPane);
        this.add(leftPanelSlot, BorderLayout.LINE_START);
        this.add(rightPanelSlot, BorderLayout.CENTER);

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
            SearchWorker task = new SearchWorker();
            List<String> paths = Arrays.stream(fileChooser.getSelectedFiles())
                .map(File::getAbsolutePath).collect(Collectors.toList());
            task.setDirs(paths);
            if (extension.getText() != null && !extension.getText().equals("")) {
                task.setExtension(extension.getText());
            }
            task.setSearchTemplate(searchTemplate.getText());
            task.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(final PropertyChangeEvent event) {
                    switch (event.getPropertyName()) {
                        case "progress":
                            /*searchProgressBar.setIndeterminate(false);
                            searchProgressBar.setValue((Integer) event.getNewValue());*/
                            break;
                        case "state":
                            switch ((StateValue) event.getNewValue()) {
                                case DONE:
                                    /*searchProgressBar.setVisible(false);
                                    searchCancelAction.putValue(Action.NAME, "Search");
                                    try {
                                        final int count = searchWorker.get();
                                        JOptionPane.showMessageDialog(Application.this, "Found: " + count + " words", "Search Words",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    } catch (final CancellationException e) {
                                        JOptionPane.showMessageDialog(Application.this, "The search process was cancelled", "Search Words",
                                            JOptionPane.WARNING_MESSAGE);
                                    } catch (final Exception e) {
                                        JOptionPane.showMessageDialog(Application.this, "The search process failed", "Search Words",
                                            JOptionPane.ERROR_MESSAGE);
                                    }*/
                                    try {
                                        setResult(task.get());
                                        final int fileCount = task.get().keySet().size();
                                        final int entryCount = task.get().values().size();
                                        JOptionPane.showMessageDialog(
                                            null, "Found: " + entryCount + " occurrences in " + fileCount
                                                + " files", "Search results",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    } catch (InterruptedException e1) {
                                        e1.printStackTrace();
                                    } catch (ExecutionException e1) {
                                        e1.printStackTrace();
                                    }
                                    break;
                                case STARTED:
                                case PENDING:
                                    break;
                            }
                            break;
                    }
                }
            });
            task.execute();
        });
        pack();
        setVisible(true);
    }

    private void setResult(Map<String, List<Long>> result) {
        this.remove(leftPanelSlot);
        leftPanelSlot = new FileTreeView(result);
        leftPanelSlot.setMinimumSize(new Dimension(300, 300));
        this.add(leftPanelSlot, BorderLayout.LINE_START);
        this.validate();
        this.repaint();
    }

    public void openFile(String path) {
        rightPanelSlot.openFile(path);
        validate();
        repaint();
    }


}
