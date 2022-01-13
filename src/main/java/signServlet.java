import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class signServlet extends HttpServlet{
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int flag1=0,flag2=0,flag3=0;
        String uname = req.getParameter("uname");
        String str1=req.getParameter("pwd"),str2=req.getParameter("rpwd");;
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
                    //System.out.println("Password Valid");
                    BufferedWriter bw = null;
                    try {
                        bw = new BufferedWriter(new FileWriter("userDetails.txt", true));
                        PrintWriter out = new PrintWriter(bw);
                        String temp=uname+","+str1;
                        out.println(temp);
                        System.out.println(temp);
                        out.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    //JOptionPane.showMessageDialog(f,"The password you have entered is valid");
                    res.sendRedirect("index.html");
                }
                else {
                    if (flag1 == 0){
                        PrintWriter out = res.getWriter();
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Password must Contain atleast one special character');");
                        out.println("location='index.html';");
                        out.println("</script>");
                    }
                    if (flag2 == 0){
                        PrintWriter out = res.getWriter();
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Password must be Contain atleast one Capital character');");
                        out.println("location='index.html/';");
                        out.println("</script>");
                    }
                }
            }
            else {
                PrintWriter out = res.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Password must be Contain atleast eight character');");
                out.println("location='index.html';");
                out.println("</script>");
            }
        }
        else{
            PrintWriter out = res.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Retype your password correctly');");
            out.println("location='index.html';");
            out.println("</script>");
        }
    }
}