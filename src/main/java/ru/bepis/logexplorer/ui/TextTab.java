package ru.bepis.logexplorer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import ru.bepis.logexplorer.file.FullFileReader;

public class TextTab extends JPanel {

    private JPanel buttonBox = new JPanel();
    private JButton nextButton = new JButton("Next");
    private JButton prevButton = new JButton("Prev");
    private JButton closeButton = new JButton("Close");
    private JButton selectAllButton = new JButton("Select all");
    private JTextArea textArea = new JTextArea(35, 70);
    private JScrollPane scrollPane = new JScrollPane(textArea,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    //private ChunkFileReader fileReader;
    private FullFileReader fullFileReader;

    private List<Long> occurrences;
    private int selectionIndex = 0;

    public TextTab(String filePath, List<Long> occurrences) {
        super();
        this.occurrences = occurrences;
        setLayout(new BorderLayout());
        textArea.setEditable(false);
        textArea.setSelectedTextColor(Color.yellow);
        buttonBox.add(prevButton);
        buttonBox.add(nextButton);
        buttonBox.add(selectAllButton);
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
            selectionIndex++;
            if (selectionIndex >= occurrences.size()) selectionIndex = 0;
            selectText();

        });
        prevButton.addActionListener(e -> {
            selectionIndex--;
            if (selectionIndex < 0) selectionIndex = occurrences.size() - 1;
            selectText();

        });
        closeButton.addActionListener(
            e -> TextView.tabbedPane.removeTabAt(TextView.tabbedPane.getSelectedIndex()));
        selectAllButton.addActionListener(e -> {

        });
    }

    private void selectText() {
        textArea.requestFocus();
        String s = textArea.getText();
        int selectionEnd = occurrences.get(selectionIndex).intValue();
        while (s.charAt(selectionEnd) != '\n' && s.charAt(selectionIndex) != '\r') {
            selectionEnd++;
        }
        textArea.select(occurrences.get(selectionIndex).intValue(),
            selectionEnd);
    }
}
