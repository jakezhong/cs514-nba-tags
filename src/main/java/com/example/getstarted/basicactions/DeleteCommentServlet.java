package com.example.getstarted.basicactions;

import com.example.getstarted.daos.interfaces.PostDao;


import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
