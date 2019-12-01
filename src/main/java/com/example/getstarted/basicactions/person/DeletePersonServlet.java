package com.example.getstarted.basicactions.person;

import com.example.getstarted.daos.interfaces.PersonDao;
import com.example.getstarted.daos.interfaces.AssociationDao;
import com.example.getstarted.daos.interfaces.PostTagDao;

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
        PostTagDao dapPostTag =(PostTagDao) this.getServletContext().getAttribute("dao-postTag");

        try {
            daoPerson.deletePerson(id);
            daoAssociation.deleteAssociationByPersonId(id);
            dapPostTag.deletePostTagByPersonId(id);
            resp.sendRedirect("/persons/user");
        } catch (Exception e) {
            throw new ServletException("Error deleting person", e);
        }
    }
}