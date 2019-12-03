package com.example.getstarted.basicactions;

import com.example.getstarted.daos.DatastorePersonDao;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "AddSocialLinkServlet")
public class AddSocialLinkServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String personId = req.getParameter("personId");

        String socialLinkName = req.getParameter("socialLink");
        String socialLinkUrl = req.getParameter("socialLinkUrl");

        DatastorePersonDao personDao = (DatastorePersonDao) this.getServletContext().getAttribute("dao-person");

     try{
            personDao.addSocialLinkInPerson(Long.parseLong(personId),socialLinkName,socialLinkUrl);
            resp.sendRedirect("/person/read?id=" + personId);
     }catch (Exception e){
         throw new ServletException("Error adding socialLink!!!");
     }

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

    }
}
