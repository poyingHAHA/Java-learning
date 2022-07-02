package myCode;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "TryServlet", value = "/TryServlet")
public class TryServlet extends HttpServlet {

    @Override
    public void init() throws ServletException{ // 一開始到網頁
        super.init();
        System.out.println("We are now calling the init method....");
    }

    @Override // 一開始到網頁與重新整理
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        super.service(request, response);
        System.out.println("We are now calling the service method...");
    }

    @Override // server關掉後執行
    public void destroy(){
        super.destroy();
        System.out.println("We are now calling the destroy method...");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<p>This paragraph is from try servlet</p>");
        out.println("<body>");
        out.println("<html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }
}
