import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.*;

public class loginServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            String userName = req.getParameter("uname");
            String pwd = req.getParameter("pass");

            Main user = new Main();

            ArrayList<String> names = new ArrayList<>();
            for(String name : user.DataBases.keySet())
                names.add(name);

            req.setAttribute("DBNames", names);
            RequestDispatcher view = req.getRequestDispatcher("database.jsp");
            view.forward(req, res);

        }catch (Exception e) {
            System.out.println(e);
        }
    }
}