package com.example.getstarted.basicactions;

import com.example.getstarted.daos.PersonDao;
import com.example.getstarted.daos.AssociationDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
/**
    * Delete person object according to personId
*/
public class DeletePersonServlet extends HttpServlet {
    /**
        * To delete person object according by personId
        * @param req HttpServletRequest
        * @param resp HttpServletResponse
        * @throws ServletException
        * @throws IOException
    */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.decode(req.getParameter("id"));
        PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
        AssociationDao daoAssociation =(AssociationDao) this.getServletContext().getAttribute("dao-association");

        try {
            daoPerson.deletePerson(id);
            daoAssociation.deleteAssociationByPersonId(id);
            resp.sendRedirect("/person/mine");
        } catch (Exception e) {
            throw new ServletException("Error deleting person", e);
        }
    }
}