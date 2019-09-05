package ru.bepis.logexplorer.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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

    private JMenuBar menuBar;
    private JMenu fileMenu;

    private JPanel leftPanelSlot = new FileTreeView();
    private JPanel rightPanelSlot = new TextView();
    private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanelSlot,
        rightPanelSlot);

    private JButton invokeSearchDialogB = new JButton("Search");



    private MainUI() {
        super("Log Explorer");
        this.setBounds(100, 100, 1500, 800);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        invokeSearchDialogB.addActionListener(e -> {
            JDialog dialog = createDialog("Модальное", true);
            dialog.setVisible(true);
        });
        this.add(invokeSearchDialogB);
        splitPane.setDividerLocation(getBounds().width / 2);
        leftPanelSlot.setMinimumSize(new Dimension(400, 200));
        rightPanelSlot.setMinimumSize(new Dimension(400, 200));
        this.add(splitPane);
        setVisible(true);
    }

    private JDialog createDialog(String title, boolean modal)
    {
        JTextField firstName = new JTextField();
        JTextField lastName = new JTextField();
        JPasswordField password = new JPasswordField();
        final JComponent[] inputs = new JComponent[] {
            new JLabel("First"),
            firstName,
            new JLabel("Last"),
            lastName,
            new JLabel("Password"),
            password
        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println("You entered " +
                firstName.getText() + ", " +
                lastName.getText() + ", " +
                password.getText());
        } else {
            System.out.println("User canceled / closed the dialog, result = " + result);
        }
        return new JDialog();
    }


}
