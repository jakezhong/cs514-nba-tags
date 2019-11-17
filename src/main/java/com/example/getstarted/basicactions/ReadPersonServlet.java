package com.example.getstarted.basicactions;

import com.example.getstarted.daos.PersonDao;
import com.example.getstarted.objects.Person;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// [START example]
@SuppressWarnings("serial")
/**
 * To view all information of a specific person
 */
public class ReadPersonServlet extends HttpServlet {

  /**
   * To view all information of a specific person
   * @param req HttpServletRequest
   * @param resp HttpServletResponse
   * @throws IOException
   * @throws ServletException
   */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
      ServletException {
    Long id = Long.decode(req.getParameter("id"));
    PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
    try {
      Person person = daoPerson.readPerson(id);
      req.setAttribute("person", person);
      req.setAttribute("page", "view-person");
      req.getRequestDispatcher("/base.jsp").forward(req, resp);
    } catch (Exception e) {
      throw new ServletException("Error reading person", e);
    }
  }
}
// [END example]
