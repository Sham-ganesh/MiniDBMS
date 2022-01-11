import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashMap;

public class App extends JFrame implements ActionListener, KeyListener {
    Main user;
    JTable table;
    JScrollPane tablePanel;
    JPanel topPanel;
    JTextField cli;
    Table currentTable = null;
    JButton dropBtn;

    App(Main user) {
        this.user = user;
        initGUI();
    }
    public void initGUI() {
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Main.currentDB.DBName);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        add(topPanel);

        JPanel btnPanel = new JPanel();
        btnPanel.setMaximumSize(new Dimension(200,500));
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));

        HashMap<String, Table> tables = Main.currentDB.tables;
        for(String tableName:tables.keySet()) {
            JButton btn = new JButton(tableName);
            btn.setMaximumSize(new Dimension(200,100));
            btn.addActionListener(this);
            btnPanel.add(btn);
        }

        topPanel.add(btnPanel);

        String[][] data = new String[0][0];
        String[] column = new String[0];

        table = new JTable(data, column);
        table.setMaximumSize(new Dimension(400,500));
        tablePanel = new JScrollPane(table);
        tablePanel.setMaximumSize(new Dimension(400,500));
        topPanel.add(tablePanel);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        cli = new JTextField();
        cli.addKeyListener(this);
        cli.setPreferredSize(new Dimension(600,130));
        bottomPanel.add(cli);
        dropBtn = new JButton("DROP");
        dropBtn.setMaximumSize(new Dimension(600,50));
        dropBtn.addActionListener(this);
        bottomPanel.add(dropBtn);
        add(bottomPanel);

        setVisible(true);
    }
    public void updateTable(String[][] data, String[] column) {
        table = new JTable(data, column);

        topPanel.remove(tablePanel);

        tablePanel = new JScrollPane(table);
        tablePanel.setMaximumSize(new Dimension(400,500));
        topPanel.add(tablePanel);

        topPanel.revalidate();
        topPanel.repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(((JButton)e.getSource()).getText() == "DROP") {
            Main.currentDB.dropTable(currentTable.tableName);
            updateTable(new String[0][0], new String[0]);
            //updateBtnPanel();
        }
        else {
            String tableName = ((JButton) e.getSource()).getText();
            currentTable = Main.currentDB.tables.get(tableName);
            try {
                currentTable.query_processing("select all");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            //updateTable();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            String query = cli.getText();
            System.out.println(query);
            cli.setText("");

            try {
                currentTable.query_processing(query);
                currentTable.query_processing("select all");
                //updateTable();
            }catch (IOException exp) {
                System.out.println(exp);
            };
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}