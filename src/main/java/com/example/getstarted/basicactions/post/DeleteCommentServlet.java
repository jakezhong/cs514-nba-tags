package com.example.getstarted.basicactions.post;

import com.example.getstarted.daos.interfaces.PostDao;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCommentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* If the user has not logged in, don't allow to delete comment and redirect */
        if (req.getSession().getAttribute("userId") == null) {
            resp.sendRedirect("/login");
            return;
        }
        PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");

        Long postId = Long.valueOf(req.getParameter("postId"));
        String commentKey = req.getParameter("commentKey");

        try {
            daoPost.deleteComment(postId,commentKey);
        } catch (Exception e) {
            System.out.println("error deleting comment!");
        }
        resp.sendRedirect("/post/read?id="+postId);   // read what we just wrote
    }
}