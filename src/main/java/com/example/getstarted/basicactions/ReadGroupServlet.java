package com.example.getstarted.basicactions;

import com.example.getstarted.daos.DatastoreAssociationDao;
import com.example.getstarted.daos.GroupDao;
import com.example.getstarted.daos.PersonDao;
import com.example.getstarted.objects.Group;
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
 * To view all information of a specific group
 */
public class ReadGroupServlet extends HttpServlet {

    /**
     * To view all information of a specific group according by groupId
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            ServletException {
        Long id = Long.decode(req.getParameter("id"));
        GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");
        PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
        try {
            Group group = daoGroup.readGroup(id);

            DatastoreAssociationDao associationDao = (DatastoreAssociationDao) this.getServletContext().getAttribute("dao-association");
            String startCursor = req.getParameter("cursor");
            Long groupId = Long.decode(req.getParameter("id"));
            List<Person> persons = new ArrayList<>();
            List<Long> personsId =null;
            String endCursor = null;

            try {
                Result<Long> result = associationDao.listPersonsByGroup(groupId,startCursor);
                personsId=result.result;

                System.out.println(personsId);

                for(Long personId: personsId){
                    Person person=daoPerson.readPerson(personId);
                    persons.add(person);

                }


                endCursor = result.cursor;
            } catch (Exception e) {
                throw new ServletException("Error listing persons", e);
            }

            req.setAttribute("group", group);
            req.getSession().getServletContext().setAttribute("persons", persons);
            req.setAttribute("cursor", endCursor);
            req.setAttribute("page", "view-group");

            req.getRequestDispatcher("/base.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error reading group", e);
        }
    }
}
// [END example]
