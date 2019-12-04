package com.example.getstarted.basicactions.person;

import com.example.getstarted.daos.DatastorePersonDao;
import com.example.getstarted.objects.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteSocialLinkServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatastorePersonDao daoPerson = (DatastorePersonDao) this.getServletContext().getAttribute("dao-person");

        Long personId = Long.decode(req.getParameter("personId"));
        String  socialLinkName =req.getParameter("socialLinkName");

        /* If the current user is not the person author, redirect */
        try {
            Person person = daoPerson.readPerson(personId);
            if (!person.getCreatedById().equals(req.getSession().getAttribute("userId"))) {
                resp.sendRedirect("/login");
                return;
            }
        } catch (Exception e) {
            resp.sendRedirect("/persons");
            return;
        }

        /* Delete social link from the current person */
        try {
            daoPerson.deleteSocialLink(personId, socialLinkName);
        } catch (Exception e) {
            throw new ServletException("Error deleting social", e);
        }
        resp.sendRedirect("/person/read?id="+personId);   // read what we just wrote
    }
}