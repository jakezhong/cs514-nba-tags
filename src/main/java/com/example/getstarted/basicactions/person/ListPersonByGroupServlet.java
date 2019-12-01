package com.example.getstarted.basicactions.person;

import com.example.getstarted.daos.interfaces.AssociationDao;
import com.example.getstarted.daos.interfaces.PersonDao;
import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Result;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// [START example]
@SuppressWarnings("serial")
/**
 * List all group members in a specific group
 */
public class ListPersonByGroupServlet extends HttpServlet {

    /**
     * list all group member in a specific group according to groupId
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        AssociationDao daoAssociation = (AssociationDao) this.getServletContext().getAttribute("dao-association");
        PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");

        Long groupId = Long.decode(req.getParameter("id"));
        String startCursor = req.getParameter("cursor");

        List<Long> personIds ;
        List<Person> persons = new ArrayList<>();
        String endCursor ;

        try {
            Result<Long> result = daoAssociation.listPersonsByGroup(groupId, startCursor);
            personIds = result.result;

            for(Long personId: personIds){
                Person person = daoPerson.readPerson(personId);
                persons.add(person);
            }
            endCursor = result.cursor;
        } catch (Exception e) {
            throw new ServletException("Error listing persons", e);
        }

        req.getSession().getServletContext().setAttribute("persons", persons);
        req.setAttribute("cursor", endCursor);
        req.setAttribute("page", "list-person");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
}
// [END example]
