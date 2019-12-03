package com.example.getstarted.basicactions;

import com.example.getstarted.daos.DatastoreSocialDao;
import com.example.getstarted.objects.SocialLink;

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

        SocialLink socialLink = new SocialLink.Builder()
                .socialLinkName(socialLinkName)
                .socialLinkUrl(socialLinkUrl)
                .personId(Long.valueOf(personId))
                .build();

        DatastoreSocialDao socialDao = new DatastoreSocialDao();

        try{
            if(socialDao.findSocialLink(socialLink)!=null){
                SocialLink socialLinkFound = socialDao.findSocialLink(socialLink);
                socialDao.updateSocialLink(socialLinkFound,socialLinkUrl);
            }else{
                socialDao.createSocialLink(socialLink);
            }


            resp.sendRedirect("/person/read?id=" + personId);
        }catch (Exception e){
            throw new ServletException("error creating person",e);
        }

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

    }
}
