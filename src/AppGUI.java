import javax.swing.*;
import java.awt.*;

public class AppGUI extends JFrame {
    JTextField cmdTextField;
    JButton insertBtn, deleteBtn, updateBtn, selectBtn;

    AppGUI() {
        initGUI();
    }
    public void initGUI() {
        setLayout(new BorderLayout());
        setSize(600,600);
        setTitle("DBMS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cmdTextField = new JTextField(100);
        add(cmdTextField, BorderLayout.SOUTH);

        insertBtn = new JButton("INSERT");
        deleteBtn = new JButton("DELETE");
        updateBtn = new JButton("UPDATE");
        selectBtn = new JButton("SELECT");

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.add(insertBtn);btnPanel.add(deleteBtn);btnPanel.add(updateBtn);btnPanel.add(selectBtn);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));

        add(contentPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
