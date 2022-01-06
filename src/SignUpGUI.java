import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SignUpGUI implements ActionListener {
    JFrame f;
    JButton b1,b2,b3;
    JTextField t1;
    JPasswordField t2,t3;
    JLabel l1,l2,l3,l4;
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    SignUpGUI(){
        f = new JFrame("SignUp");
        f.setSize(500,300);
        f.setLayout(null);
        int x = (int) ((dimension.getWidth() - f.getWidth()) / 2);
        int y = (int) ((dimension.getHeight()- f.getHeight()) / 2);
        l1 = new JLabel("<HTML><u>SignUp</u></HTML>");
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
        l4 = new JLabel("CONFIRM PASSWORD");
        l4.setBounds(10,140,150,35);
        t3 = new JPasswordField();
        t3.setBounds(150,145,250,25);
        b1 = new JButton("Register");
        b1.addActionListener(this);
        b1.setBounds(90,195,100,35);
        b2 = new JButton("Login");
        b2.addActionListener(this);
        b2.setBounds(210,195,100,35);
        b3 = new JButton("Cancel");
        b3.addActionListener(this);
        b3.setBounds(330,195,100,35);
        b1.setFocusable(false);
        b2.setFocusable(false);
        b3.setFocusable(false);
        f.setLocation(x,y);
        f.add(l1);
        f.add(l2);
        f.add(t1);
        f.add(l3);
        f.add(t2);
        f.add(l4);
        f.add(t3);
        f.add(b1);
        f.add(b2);
        f.add(b3);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1) {
            int flag1=0,flag2=0,flag3=0;
            String str1=t2.getText(),str2=t3.getText();
            if(str1.equals(str2)){
                if(str1.length()>=8)
                {
                    for(int i=0;i<str1.length();i++)
                    {
                        if(str1.charAt(i)=='@'||str1.charAt(i)=='#'||str1.charAt(i)=='$'||str1.charAt(i)=='&')
                            flag1=1;
                        if(str1.charAt(i)>=65&&str1.charAt(i)<=90)
                            flag2=1;
                    }
                    if(flag1==1&&flag2==1)
                    {
                        System.out.println("Password Valid");
                        BufferedWriter bw = null;
                        try {
                            bw = new BufferedWriter(new FileWriter("users.txt", true));
                            PrintWriter out = new PrintWriter(bw);
                            String temp=t1.getText()+","+t2.getText();
                            out.println(temp);
                            System.out.println(temp);
                            out.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        new LoginGUI();
                        f.dispose();
                        //JOptionPane.showMessageDialog(f,"The password you have entered is valid");
                    }
                    else {
                        if (flag1 == 0)
                            JOptionPane.showMessageDialog(f, "The password must Contain atleast one special character");
                        if (flag2 == 0)
                            JOptionPane.showMessageDialog(f, "The password must be Contain atleast one Capital character");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(f,"The password must be atleast eight character");
                }
            }
            else{
                JOptionPane.showMessageDialog(f,"The password don't match");
            }
        }
        if(e.getSource()==b2){
            new LoginGUI();
            f.dispose();
        }
        if(e.getSource()==b3){
            f.dispose();
        }
    }

    public static void main(String[] args) {
        new SignUpGUI();
    }
}
