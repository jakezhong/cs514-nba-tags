package com.example.getstarted.basicactions.profile;

import com.example.getstarted.daos.interfaces.ProfileDao;
import com.example.getstarted.objects.Profile;
import com.example.getstarted.objects.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("serial")
/**
 * To view all information of a specific person
 */
public class ReadProfileServlet extends HttpServlet {

    /**
    * To view all information of a specific person
    * @param req HttpServletRequest
    * @param resp HttpServletResponse
    * @throws IOException
    * @throws ServletException
    */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        /*
          If there's user id parameter, list persons by this id
          Otherwise, list persons by logged in user id
       */
        String userId;
        try {
            userId = req.getParameter("id") == null || req.getParameter("id").isEmpty() ? (String) req.getSession().getAttribute("userId") : req.getParameter("id");
        } catch (Exception e) {
            userId = (String) req.getSession().getAttribute("userId");
        }

        ProfileDao daoProfile = (ProfileDao) this.getServletContext().getAttribute("dao-profile");
        List<Profile> profiles;
        Profile profile;

        try {
            Result<Profile> result = daoProfile.findProfile(userId);
            profiles = result.result;
            profile = profiles.get(0);
        } catch (Exception e) {
            profile = null;
        }

        req.setAttribute("profile", profile);
        req.setAttribute("page", "view-profile");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
}