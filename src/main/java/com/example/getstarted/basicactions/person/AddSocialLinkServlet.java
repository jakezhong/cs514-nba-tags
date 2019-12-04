package com.example.getstarted.basicactions.person;

import com.example.getstarted.daos.DatastorePersonDao;
import com.example.getstarted.objects.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Add social links for a person
 */
@WebServlet(name = "AddSocialLinkServlet")
public class AddSocialLinkServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long personId = Long.decode(req.getParameter("personId"));
        String socialLinkName = req.getParameter("socialLink");
        String socialLinkUrl = req.getParameter("socialLinkUrl");

        DatastorePersonDao daoPerson = (DatastorePersonDao) this.getServletContext().getAttribute("dao-person");

        /* If the current user is not the post author, redirect */
        try {
            Person person = daoPerson.readPerson(personId);
            if (!person.getCreatedById().equals(req.getSession().getAttribute("userId"))) {
                resp.sendRedirect("/login");
                return;
            }
        } catch (Exception e) {
            resp.sendRedirect("/person/read?id=" + personId.toString());
            return;
        }

        /* List all the social link from this person */
        try {
            daoPerson.addSocialLinkInPerson(personId, socialLinkName, socialLinkUrl);
            resp.sendRedirect("/person/read?id=" + personId.toString());
        } catch (Exception e){
            throw new ServletException("Error adding socialLink!");
        }
    }
}