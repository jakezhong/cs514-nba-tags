package com.example.getstarted.basicactions.person;

import com.example.getstarted.daos.interfaces.PersonDao;
import com.example.getstarted.daos.interfaces.AssociationDao;
import com.example.getstarted.daos.interfaces.PostTagDao;
import com.example.getstarted.objects.Person;

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
        Long personId = Long.decode(req.getParameter("id"));
        PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
        AssociationDao daoAssociation =(AssociationDao) this.getServletContext().getAttribute("dao-association");
        PostTagDao dapPostTag =(PostTagDao) this.getServletContext().getAttribute("dao-postTag");

        /* If the current user is not the person author, redirect */
        try {
            Person post = daoPerson.readPerson(personId);
            if (!post.getCreatedById().equals(req.getSession().getAttribute("userId"))) {
                resp.sendRedirect("/login");
                return;
            }
        } catch (Exception e) {
            resp.sendRedirect("/persons");
            return;
        }

        try {
            daoPerson.deletePerson(personId);
            daoAssociation.deleteAssociationByPersonId(personId);
            dapPostTag.deletePostTagByPersonId(personId);
            resp.sendRedirect("/persons/user");
        } catch (Exception e) {
            throw new ServletException("Error deleting person", e);
        }
    }
}