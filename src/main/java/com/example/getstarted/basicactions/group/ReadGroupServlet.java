package com.example.getstarted.basicactions.group;

import com.example.getstarted.daos.interfaces.AssociationDao;
import com.example.getstarted.daos.interfaces.GroupDao;
import com.example.getstarted.daos.interfaces.PersonDao;
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
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");
        PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
        AssociationDao daoAssociation = (AssociationDao) this.getServletContext().getAttribute("dao-association");

        Long groupId = Long.decode(req.getParameter("id"));

        try {
            Group group = daoGroup.readGroup(groupId);

            /* If the current person if private and the user is not the author, redirect user */
            if (group.getStatus() != null) {
                if (group.getStatus().equals("private") && !group.getCreatedById().equals(req.getSession().getAttribute("userId"))) {
                    resp.sendRedirect("/groups/user?id="+group.getCreatedById());
                    return;
                }
            }

            String startCursor = req.getParameter("cursor");
            String endCursor;

            List<Person> persons = new ArrayList<>();
            List<Long> personIds;

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