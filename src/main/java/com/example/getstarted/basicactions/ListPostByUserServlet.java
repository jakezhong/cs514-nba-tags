package com.example.getstarted.basicactions;

import com.example.getstarted.daos.PostDao;
import com.example.getstarted.daos.ProfileDao;
import com.example.getstarted.objects.Post;
import com.example.getstarted.objects.Profile;
import com.example.getstarted.objects.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// [START example]
@SuppressWarnings("serial")
/**
 * To List posts by user according to userId
 */
public class ListPostByUserServlet extends HttpServlet {

    /**
    * list all posts created by specific user according to userID
    * display all posts by cursor( fetch 10 per time)
    * @param req HttpServletRequest
    * @param resp HttpServletResponse
    * @throws IOException
    * @throws ServletException
    */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        /*
            If there's user id parameter, list group by this id
            Otherwise, list group by logged in user id
        */
        String userId;
        try {
            userId = req.getParameter("id") == null || req.getParameter("id").isEmpty() ? (String) req.getSession().getAttribute("userId") : req.getParameter("id");
        } catch (Exception e) {
            userId = (String) req.getSession().getAttribute("userId");
        }

        /* First fetch the user profile information */
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

        PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");
        String startCursor = req.getParameter("cursor");
        List<Post> posts;
        String endCursor;
        try {
            Result<Post> result = daoPost.listPostsByUser(userId, startCursor);
            posts = result.result;
            endCursor = result.cursor;
        } catch (Exception e) {
            throw new ServletException("Error listing posts", e);
        }

        req.setAttribute("profile", profile);
        req.getSession().getServletContext().setAttribute("posts", posts);
        req.getSession().setAttribute("cursor", endCursor);
        req.getSession().setAttribute("page", "list-user-posts");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
}
