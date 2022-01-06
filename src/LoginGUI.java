import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoginGUI implements ActionListener {
    JFrame f;
    JButton b1,b2,b3;
    JTextField t1;
    JPasswordField t2;
    JLabel l1,l2,l3,l4;
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    LoginGUI(){
        f = new JFrame("LOGIN");
        f.setSize(500,250);
        f.setLayout(null);
        int x = (int) ((dimension.getWidth() - f.getWidth()) / 2);
        int y = (int) ((dimension.getHeight()- f.getHeight()) / 2);
        l1 = new JLabel("<HTML><u>LogIn</u></HTML>");
        l1.setFont(new Font("Cambria",Font.BOLD,30));
        l1.setBounds(200,10,150,50);
        l1.setForeground(new Color(0, 0,0));
        l2= new JLabel("USER NAME");
        l2.setBounds(10,70,100,35);
        t1= new JTextField();
        t1.setBounds(150,75,250,25);
        l3 = new JLabel("PASSWORD");
        l3.setBounds(10,105,100,35);
        t2 = new JPasswordField();
        t2.setBounds(150,110,250,25);
        b1 = new JButton("Login");
        b1.setBounds(90,145,100,35);
        b1.addActionListener(this);
        b2 = new JButton("Signup");
        b2.addActionListener(this);
        b2.setBounds(210,145,100,35);
        b3 = new JButton("Cancel");
        b3.addActionListener(this);
        b3.setBounds(330,145,100,35);
        b1.setFocusable(false);
        b2.setFocusable(false);
        b3.setFocusable(false);
        f.setLocation(x,y);
        f.add(l1);
        f.add(l2);
        f.add(t1);
        f.add(l3);
        f.add(t2);
        f.add(b1);
        f.add(b2);
        f.add(b3);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1){
            String uname=t1.getText(),pass=t2.getText();
            try {
                BufferedReader in = new BufferedReader(new FileReader("users.txt"));
                String temp;int flag=0;
                while((temp=in.readLine())!=null)
                {
                    String[] st=new String[3];
                    st=temp.split(",");
                    if(st[0].equals(uname))
                    {
                        if(st[1].equals(pass)) {
                            System.out.println("Successsfully logged in");
                            JOptionPane.showMessageDialog(f, "Succesfull logged in.");
                        }
                        else
                            JOptionPane.showMessageDialog(f,"Recheck your password");
                        flag=1;
                    }
                }
                if(flag==0)
                JOptionPane.showMessageDialog(f,"Invalid Username");
                in.close();

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            t1.setText("");
            t2.setText("");
        }
        if(e.getSource()==b2){
            new SignUpGUI();
            f.dispose();
        }
        if(e.getSource()==b3){
            f.dispose();
        }
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}
