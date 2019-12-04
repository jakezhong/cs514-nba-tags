package com.example.getstarted.basicactions.group;

import com.example.getstarted.daos.DatastoreAssociationDao;
import com.example.getstarted.daos.interfaces.AssociationDao;
import com.example.getstarted.daos.interfaces.GroupDao;
import com.example.getstarted.daos.interfaces.PersonDao;
import com.example.getstarted.objects.Association;
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
 * Create Association between person and group
 */
public class AddGroupMemberServlet extends HttpServlet {

    // [START setup]

    /**
     * When user request Join group, redirect to form-association jsp
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long groupId = Long.decode(req.getParameter("id"));
        String userId = (String) req.getSession().getAttribute("userId");

        PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
        AssociationDao associationDao = (AssociationDao) this.getServletContext().getAttribute("dao-association");

        /* Initialize group list */
        List<Long> personsAlreadyAdded;
        List<Person> Allpersons;
        List<Person> persons = new ArrayList<>();

        /* List all groups */
        try {
            Result<Person> result = daoPerson.listAllPersonsByUser(userId);
            Allpersons = result.result;
            personsAlreadyAdded = associationDao.listAllPersonsByGroup((Long.valueOf(groupId))).result;
            for(Person person: Allpersons){
                if(personsAlreadyAdded.indexOf(person.getId()) < 0){ // not added
                    persons.add(person);
                }
            }
        } catch (Exception e) {
            throw new ServletException("Error listing persons", e);
        }
        req.getSession().getServletContext().setAttribute("persons", persons);
        req.setAttribute("groupId", groupId);
        req.setAttribute("action", "Add");          // Part of the Header in form-association.jsp
        req.setAttribute("destination", "/association/create");  // The urlPattern to invoke (this Servlet)
        req.setAttribute("page", "add-member");           // Tells base.jsp to include form-association.jsp
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
    // [END setup]

    // [START formpost]

    /**
     * To create association entity and store in corresponding kind
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] personIds = req.getParameterValues("persons");

        Long groupId = Long.valueOf(req.getParameter("groupId"));

        for (String person: personIds) {
            Long personId = Long.parseLong(person);
            Association association = new Association.Builder()
                .personId(personId)
                .groupId(groupId)
                .build();
            AssociationDao daoAssociation = new DatastoreAssociationDao();
            try {
                daoAssociation.createAssociation(association);
            } catch (Exception e) {
                throw new ServletException("Error creating association", e);
            }
        }
        resp.sendRedirect("/group/read?id=" + groupId);   // read what we just wrote
    }
}