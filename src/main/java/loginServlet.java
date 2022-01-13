import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class loginServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            String userName = req.getParameter("uname");
            String pwd = req.getParameter("pass");

//            File userDetailsFile = new File("userdetails.txt");
//            if( userDetailsFile.exists()) {
//                System.out.println("===== FILE EXISTS ======");
//                System.out.println("PATH: " + userDetailsFile.getAbsolutePath());
//            }

            BufferedReader in = new BufferedReader(new FileReader("userDetails.txt"));
            String line;int flag=0;
            String[] str=new String[20];
            while((line=in.readLine())!=null)
            {
                str=line.split(",");
                if(str[0].equals(userName))
                {
                    if(str[1].equals(pwd)) {
                        System.out.println("Successsfully logged in");
                        Main user = new Main();
                        ArrayList<String> names = new ArrayList<>();
                        for(String name : user.DataBases.keySet())
                            names.add(name);
                        req.setAttribute("DBNames", names);
                        RequestDispatcher view = req.getRequestDispatcher("database.jsp");
                        view.forward(req, res);
                    }
                    else
                        System.out.println("Recheck your Password");
                    flag=1;
                }
            }
            if(flag==0)
                System.out.println("Invalid Username!");
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}