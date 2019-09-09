package ru.bepis.logexplorer.ui;

import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import ru.bepis.logexplorer.file.ChunkFileReader;
import ru.bepis.logexplorer.file.FullFileReader;

public class TextTab extends JPanel {

    private JPanel buttonBox = new JPanel();
    private JButton nextButton = new JButton("Next");
    private JButton prevButton = new JButton("Prev");
    private JButton closeButton = new JButton("Close");
    private JTextArea textArea = new JTextArea(35, 70);
    private JScrollPane scrollPane = new JScrollPane(textArea,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    private ChunkFileReader fileReader;
    private FullFileReader fullFileReader;

    public TextTab(String filePath) {
        super();
        setLayout(new BorderLayout());
        textArea.setEditable(false);
        buttonBox.add(prevButton);
        buttonBox.add(nextButton);
        buttonBox.add(closeButton);
        textArea.setFont(MainUI.getTextFont());
        //scrollPane.setPreferredSize(new Dimension(this.getWidth() - 20, this.getHeight() - 20));
        this.add(buttonBox, BorderLayout.PAGE_START);
        this.add(scrollPane, BorderLayout.LINE_END);
        configureActions();
        try {
            //fileReader = new ChunkFileReader(filePath);
            fullFileReader = new FullFileReader(filePath);
            //textArea.setText(fileReader.readNextPage());
            textArea.setText(fullFileReader.readFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureActions() {
        nextButton.addActionListener(e -> {
            try {
                textArea.setText(fileReader.readNextPage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        prevButton.addActionListener(e -> {
            try {
                textArea.setText(fileReader.readPrevPage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        closeButton.addActionListener(e -> {
            TextView.tabbedPane.removeTabAt(TextView.tabbedPane.getSelectedIndex());
        });
    }
}
