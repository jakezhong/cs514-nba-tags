package com.example.getstarted.basicactions.post;

import com.example.getstarted.daos.interfaces.PostDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AddCommentServlet")
public class AddCommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* If the user has not logged in, don't allow to add comment and redirect */
        if (req.getSession().getAttribute("userId") == null) {
            resp.sendRedirect("/login");
            return;
        }

        String postId = req.getParameter("postId");
        String content = req.getParameter("content");
        String createdByString = "";

        System.out.println(content);
        HttpSession session = req.getSession();
        if (session.getAttribute("userEmail") != null) { // Does the user have a logged in session?
            createdByString = (String) session.getAttribute("userEmail");
        }

        PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");
        try{
            daoPost.addCommentInPost(Long.parseLong(postId),createdByString,content);
            resp.sendRedirect("/post/read?id=" + postId);
        }catch (Exception e){
            throw new ServletException("Error adding comment!!!");
        }
    }
}