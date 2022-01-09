import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;


public class databaseServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        res.setContentType("text/html");

        try {
            String name = req.getParameter("name");
            String act = req.getParameter("act");

            if(act == null) {
                DBUtils.createDB(name);
                res.sendRedirect("login");
            }else if(act.equals("select")){
                DBUtils.useDB(name);
                System.out.println(Main.currentDB.DBName);
            }else if(act.equals("delete")) {
                DBUtils.deleteDB(name);
                //res.sendRedirect("database.jsp");
            }else {
                PrintWriter out = res.getWriter();
                out.println("<br><br><br><center><h1>BAD REQUEST</h1></center>");
            }
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}
