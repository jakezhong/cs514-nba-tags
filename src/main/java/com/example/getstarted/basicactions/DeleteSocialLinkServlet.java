package com.example.getstarted.basicactions;

import com.example.getstarted.daos.DatastorePersonDao;
import com.example.getstarted.daos.interfaces.PostDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteSocialLinkServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       DatastorePersonDao personDao = (DatastorePersonDao) this.getServletContext().getAttribute("dao-person");

        String personId = req.getParameter("personId");
        String  socialLinkName =req.getParameter("socialLinkName");


        try {
            personDao.deleteSocialLink(Long.parseLong(personId),socialLinkName);

        } catch (Exception e) {
            System.out.println("error deleting comment!");

        }

        resp.sendRedirect("/person/read?id="+personId);   // read what we just wrote
    }
}
