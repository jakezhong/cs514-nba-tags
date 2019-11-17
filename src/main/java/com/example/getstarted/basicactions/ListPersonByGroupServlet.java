package com.example.getstarted.basicactions;

import com.example.getstarted.daos.DatastoreAssociationDao;
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
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            ServletException {
        DatastoreAssociationDao daoAssociation = (DatastoreAssociationDao) this.getServletContext().getAttribute("dao-association");
        String startCursor = req.getParameter("cursor");
        Long groupId = Long.decode(req.getParameter("id"));
        System.out.println(groupId);
        List<Person> persons = null;
        String endCursor = null;
        try {
            Result<Person> result = daoAssociation.listPersonsByGroup(groupId,startCursor);
            persons=result.result;
            System.out.println(persons);
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
