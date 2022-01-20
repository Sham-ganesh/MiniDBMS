import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;


public class databaseServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        res.setContentType("text/html");

        try {
            String name = req.getParameter("name");
            String act = req.getParameter("act");

            if(act == null) {
                DBUtils.createDB(name);
                //res.sendRedirect("login");
                PrintWriter out = res.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("alert('DataBase created pls login again');");
                out.println("location='index.html';");
                out.println("</script>");
            }else if(act.equals("select")){
                DBUtils.useDB(name);
                System.out.println(Main.currentDB.DBName);
                //res.sendRedirect("login");
                PrintWriter out = res.getWriter();
                out.println("<button onclick=\"history.back()\">Go Back</button>");
            }else if(act.equals("delete")) {
                DBUtils.deleteDB(name);
                res.sendRedirect("index.html");
            }else if (act.equals("export")) {
                BufferedReader reader = new BufferedReader(new FileReader("userDetails.txt"));
                String data = reader.readLine();
                reader.close();
                String credentials[] = data.split(",");
                DataBase db = new DataBase(name, DBUtils.DBFolderPath+"/"+name);
                MailService.sendEmail(credentials[0], name, db.tables);
            }else {
                PrintWriter out = res.getWriter();
                out.println("<br><br><br><center><h1>BAD REQUEST</h1></center>");
            }
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}
