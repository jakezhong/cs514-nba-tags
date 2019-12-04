package com.example.getstarted.basicactions.post;

import com.example.getstarted.daos.interfaces.PostDao;
import com.example.getstarted.objects.Post;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")

/**
* To view all information of a specific post
*/
public class LikePostServlet extends HttpServlet {

    /**
    * To view all information of a specific post
    * @param req HttpServletRequest
    * @param resp HttpServletResponse
    * @throws IOException
    * @throws ServletException
    */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        /* If the user has not logged in, don't allow to like post and redirect */
        if (req.getSession().getAttribute("userId") == null) {
            resp.sendRedirect("/login");
            return;
        }

        PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");

        Long postId = Long.decode(req.getParameter("id"));
        String userId = req.getSession().getAttribute("userId").toString();

        /* Post like method */
        try {
            Post post = daoPost.readPost(postId);
            List<String> like = post.getLike();
            if (like == null) {
                like = new ArrayList<String>();
                like.add(userId);
                post.setLiked(true);
            } else {
                if (like.indexOf(userId) < 0) {
                    like.add(userId);
                    post.setLiked(true);
                } else {
                    like.remove(userId);
                    post.setLiked(false);
                }
            }
            post.setLike(like);
            daoPost.likePost(post);

            resp.sendRedirect("/posts");
            return;
        } catch (Exception e) {
            throw new ServletException("Error liking post", e);
        }
    }
}