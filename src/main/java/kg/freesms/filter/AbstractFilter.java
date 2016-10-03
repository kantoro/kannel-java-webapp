package kg.freesms.filter;
 
import java.io.IOException;
 
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
 
public class AbstractFilter {
 
    public AbstractFilter() {
        super();
    }
 
    protected void doLogin(ServletRequest request, ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/pages/public/login.xhtml");
        rd.forward(request, response);
    }
 
    protected void accessDenied(ServletRequest request, ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/pages/public/accessDenied.xhtml");
        rd.forward(request, response);
    }
}
