package ru.bepis.logexplorer.ui;

import java.awt.BorderLayout;
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
    private String template;
    private int selectionIndex = 0;

    public TextTab(String filePath, List<Long> occurrences, String template) {
        super();
        this.occurrences = occurrences;
        this.template = template;
        setLayout(new BorderLayout());
        textArea.setEditable(false);
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
            textArea.requestFocus();
            textArea.select(occurrences.get(selectionIndex).intValue(),
                occurrences.get(selectionIndex).intValue() + template.length());

        });
        prevButton.addActionListener(e -> {
            selectionIndex--;
            if (selectionIndex < 0) selectionIndex = occurrences.size() - 1;
            textArea.requestFocus();
            textArea.select(occurrences.get(selectionIndex).intValue(),
                occurrences.get(selectionIndex).intValue() + template.length());

        });
        closeButton.addActionListener(
            e -> TextView.tabbedPane.removeTabAt(TextView.tabbedPane.getSelectedIndex()));
        selectAllButton.addActionListener(e -> {

        });
    }
}
