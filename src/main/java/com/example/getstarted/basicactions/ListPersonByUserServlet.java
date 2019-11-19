package com.example.getstarted.basicactions;

import com.example.getstarted.daos.PersonDao;
import com.example.getstarted.daos.UserDao;
import com.example.getstarted.objects.OurUser;
import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Result;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// [START example]
@SuppressWarnings("serial")
/**
 * To List persons by user according to userId
 */
public class ListPersonByUserServlet extends HttpServlet {

  /**
   * list all persons created by specific user according to userID
   * display all persons by cursor( fetch 10 per time)
   * @param req HttpServletRequest
   * @param resp HttpServletResponse
   * @throws IOException
   * @throws ServletException
   */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    /*
            Load user first
         */
    String id = req.getParameter("id");
    OurUser currentUser = null;
    UserDao daoUser = (UserDao) this.getServletContext().getAttribute("dao-user");
    if (id == null || id.isEmpty()) {
      if (req.getSession().getAttribute("userEmail") == null || ((String) req.getSession().getAttribute("userEmail")).isEmpty()) {
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
    /*
        Load persons after user
     */
    PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
    String startCursor = req.getParameter("cursor");
    List<Person> persons = null;
    String endCursor = null;
    try {
      Result<Person> result = daoPerson.listPersonsByUser((String) req.getSession().getAttribute("userId"), startCursor);
      persons = result.result;
      endCursor = result.cursor;
    } catch (Exception e) {
      throw new ServletException("Error listing persons", e);
    }
    req.setAttribute("user", currentUser);
    req.getSession().getServletContext().setAttribute("persons", persons);
    req.getSession().setAttribute("cursor", endCursor);
    req.getSession().setAttribute("page", "view-user-persons");
    req.getRequestDispatcher("/base.jsp").forward(req, resp);
  }
}
// [END example]
