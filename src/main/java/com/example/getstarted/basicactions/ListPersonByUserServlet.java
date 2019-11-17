package com.example.getstarted.basicactions;

import com.example.getstarted.daos.PersonDao;
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
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
        ServletException {
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
    req.getSession().getServletContext().setAttribute("persons", persons);
    StringBuilder personNames = new StringBuilder();
    for (Person person : persons) {
      personNames.append(person.getFirst() + " ");
    }
    req.getSession().setAttribute("cursor", endCursor);
    req.getSession().setAttribute("page", "list-person");
    req.getRequestDispatcher("/base.jsp").forward(req, resp);
  }
}
// [END example]
