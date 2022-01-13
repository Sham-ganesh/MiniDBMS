import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class loginServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            String userName = req.getParameter("uname");
            String pwd = req.getParameter("pass");

            BufferedReader in = new BufferedReader(new FileReader("users.txt"));
            String line;int flag=0;
            String[] str=new String[20];
            while((line=in.readLine())!=null)
            {
                str=line.split(",");
                if(str[0].equals(userName))
                {
                    if(str[1].equals(pwd)) {
                        in.close();
                        Main user = new Main();
                        ArrayList<String> names = new ArrayList<>();
                        for(String name : user.DataBases.keySet())
                            names.add(name);

                        req.setAttribute("DBNames", names);
                        RequestDispatcher view = req.getRequestDispatcher("database.jsp");
                        view.forward(req, res);
                    }
                    else{
                        PrintWriter out = res.getWriter();
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Password incorrect- never ever try to guess it!!');");
                        out.println("location='index.html';");
                        out.println("</script>");
                    }
                    flag=1;
                }
            }
            if(flag==0) {
                PrintWriter out = res.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('User Not Found - Check your name.');");
                out.println("location='index.html';");
                out.println("</script>");
            }
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}