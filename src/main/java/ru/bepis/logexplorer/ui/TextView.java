package ru.bepis.logexplorer.ui;

import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import ru.bepis.logexplorer.file.ChunkFileReader;

public class TextView extends JPanel {

    private JPanel buttonBox = new JPanel();
    private JButton nextButton = new JButton("Next");
    private JButton prevButton = new JButton("Prev");
    private JTextArea textArea = new JTextArea(35, 70);
    private JScrollPane scrollPane = new JScrollPane(textArea,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    private String filePath = "C:/Users/easyd/Desktop/test1/alice29.txt";
    private ChunkFileReader fileReader;

    private long lastPageStart = 0;

    TextView() {
        super();
        setLayout(new BorderLayout());
        textArea.setEditable(false);
        buttonBox.add(prevButton);
        buttonBox.add(nextButton);
        this.add(buttonBox, BorderLayout.PAGE_START);
        this.add(scrollPane, BorderLayout.CENTER);
        configureActions();
        try {
            fileReader = new ChunkFileReader(filePath);
            textArea.setText(fileReader.readPageContaining(65500));
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
    }

}
