package com.example.getstarted.basicactions;

import com.example.getstarted.daos.PersonDao;
import com.example.getstarted.daos.UserDao;
import com.example.getstarted.objects.OurUser;
import com.example.getstarted.objects.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// [START example]
@SuppressWarnings("serial")
/**
 * To view all information of a specific person
 */
public class ReadUserServlet extends HttpServlet {

  /**
   * To view all information of a specific person
   * @param req HttpServletRequest
   * @param resp HttpServletResponse
   * @throws IOException
   * @throws ServletException
   */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    String id = req.getParameter("id");
    OurUser currentUser = null;
    HttpSession session = req.getSession();
    UserDao daoUser = (UserDao) this.getServletContext().getAttribute("dao-user");
    if (id == null || id.isEmpty()) {
        if (session.getAttribute("userEmail") != null) { // Does the user have a logged in session?
            resp.sendRedirect("/");
        } else {
            currentUser= (OurUser) this.getServletContext().getAttribute("login-user");
        }
    } else {
        try {
            Long userId = Long.decode(id);
            currentUser = daoUser.readUser(userId);
        } catch (Exception e) {
            resp.sendRedirect("/");
        }
    }
    req.setAttribute("user", currentUser);
    req.setAttribute("page", "view-user");
    req.getRequestDispatcher("/base.jsp").forward(req, resp);
  }
}
// [END example]
