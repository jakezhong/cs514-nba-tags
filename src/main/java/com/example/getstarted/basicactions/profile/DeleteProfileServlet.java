package com.example.getstarted.basicactions.profile;

import com.example.getstarted.daos.interfaces.ProfileDao;
import com.example.getstarted.objects.Profile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
/**
    * Delete profile object according to profileId
*/
public class DeleteProfileServlet extends HttpServlet {
    /**
        * To delete profile object according by profileId
        * @param req HttpServletRequest
        * @param resp HttpServletResponse
        * @throws ServletException
        * @throws IOException
    */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long profileId = Long.decode(req.getParameter("id"));
        ProfileDao daoProfile = (ProfileDao) this.getServletContext().getAttribute("dao-profile");

        /* If the current user is not the person author, redirect */
        try {
            Profile profile = daoProfile.readProfile(profileId);
            if (!profile.getCreatedById().equals(req.getSession().getAttribute("userId"))) {
                resp.sendRedirect("/login");
                return;
            }
        } catch (Exception e) {
            resp.sendRedirect("/profile/user");
            return;
        }

        try {
            daoProfile.deleteProfile(profileId);
            resp.sendRedirect("/profile/user");
        } catch (Exception e) {
            throw new ServletException("Error deleting profile", e);
        }
    }
}