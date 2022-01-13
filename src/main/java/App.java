import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class App extends JFrame implements ActionListener, KeyListener {
    Main user;
    JTable table;
    JScrollPane tablePanel;
    JPanel topPanel,btnPanel;
    JTextField cli;
    Table currentTable = null;
    JButton dropBtn,createBtn;

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

        addTableToPanel();

        topPanel.add(btnPanel);

        String[][] data = new String[0][0];
        String[] column = new String[0];

        table = new JTable(data, column);
        table.setMaximumSize(new Dimension(400,500));
        tablePanel = new JScrollPane(table);
        tablePanel.setMaximumSize(new Dimension(400,500));
        topPanel.add(tablePanel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setPreferredSize(new Dimension(600,100));
        cli = new JTextField();
        cli.addKeyListener(this);
        cli.setMaximumSize(new Dimension(400,100));
        bottomPanel.add(cli);
        dropBtn = new JButton("DROP");
        dropBtn.setMaximumSize(new Dimension(100,100));
        dropBtn.addActionListener(this);
        bottomPanel.add(dropBtn);
        createBtn = new JButton("CREATE");
        createBtn.setMaximumSize(new Dimension(100,100));
        createBtn.addActionListener(this);
        bottomPanel.add(createBtn);
        add(bottomPanel);

        setVisible(true);
    }
    public void updateTable(ArrayList<String> selectedData) {
        String[][] data;
        String[] column = new String[0];
        if(selectedData.size() == 0)
            data = new String[0][0];
        else {
            data = new String[selectedData.size() - 1][];
            column = selectedData.get(0).split(",");
            for (int i = 1; i < selectedData.size(); i++) {
                data[i - 1] = selectedData.get(i).split(",");
            }
        }
        System.out.println(column + " " + data);
        table = new JTable(data, column);

        topPanel.remove(tablePanel);

        tablePanel = new JScrollPane(table);
        tablePanel.setMaximumSize(new Dimension(400,500));
        topPanel.add(tablePanel);

        topPanel.revalidate();
        topPanel.repaint();
    }
    public void updateBtnPanel() {
        topPanel.remove(btnPanel);

        addTableToPanel();

        topPanel.revalidate();
        topPanel.repaint();
    }
    public void addTableToPanel() {
        btnPanel = new JPanel();
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
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(((JButton)e.getSource()).getText() == "DROP") {
            Main.currentDB.dropTable(currentTable.tableName);
            updateBtnPanel();
            updateTable(new ArrayList<>());
        }
        else if (((JButton)e.getSource()).getText() == "CREATE") {
            String query = cli.getText();
            cli.setText("");
            Main.currentDB.processCreateQuery(query);
            updateBtnPanel();
            updateTable(new ArrayList<>());
        }
        else {
            String tableName = ((JButton) e.getSource()).getText();
            currentTable = Main.currentDB.tables.get(tableName);
            setTitle(Main.currentDB.DBName + "->" + tableName);
            try {
                ArrayList<String> data = currentTable.query_processing("table select all");
                updateTable(data);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
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
                ArrayList<String> data = currentTable.query_processing("table select all");
                updateTable(data);
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