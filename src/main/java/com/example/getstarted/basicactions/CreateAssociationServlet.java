package com.example.getstarted.basicactions;

import com.example.getstarted.daos.DatastoreAssociationDao;
import com.example.getstarted.daos.DatastorePersonDao;
import com.example.getstarted.daos.PersonDao;
import com.example.getstarted.objects.Association;
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
 * Create Association between person and group
 */
public class CreateAssociationServlet extends HttpServlet {

    // [START setup]

    /**
     * When user request Join group, redirect to form-association jsp
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        try {
            Long id = Long.decode(req.getParameter("id"));
            DatastorePersonDao daoPerson = (DatastorePersonDao) this.getServletContext().getAttribute("dao-person");

            List<Person> persons = null;

            try {
                Result<Person> result = daoPerson.listAllPersons();
                persons = result.result;

            } catch (Exception e) {
                throw new ServletException("Error listing persons", e);
            }
            req.getSession().getServletContext().setAttribute("persons", persons);

            req.setAttribute("groupId", id);
            req.setAttribute("action", "Add");          // Part of the Header in form-association.jsp
            req.setAttribute("destination", "/association/create");  // The urlPattern to invoke (this Servlet)
            req.setAttribute("page", "form-association");           // Tells base.jsp to include form-association.jsp
            req.getRequestDispatcher("/base.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error reading group", e);
        }

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
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        Long personId = Long.valueOf(req.getParameter("personId"));
        Long groupId = Long.valueOf(req.getParameter("groupId"));

      //   [START GroupBuilder]
        Association association = new Association.Builder()
                .personId(personId)
                .groupId(groupId)
                .build();

         //[END AssociationBuilder]
        DatastoreAssociationDao daoAssociation = new DatastoreAssociationDao();
        try {
            daoAssociation.createAssociation(association);
            resp.sendRedirect("/group/read?id=" +groupId.toString());   // read what we just wrote
        } catch (Exception e) {
            throw new ServletException("Error creating association", e);
        }
    }
}
// [END example]

