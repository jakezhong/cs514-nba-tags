package com.example.getstarted.basicactions;


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

        String postId = req.getParameter("postId");


        String content = req.getParameter("commentContent");

        String createdByString = "";
       // String createdByIdString = "";
       // Date date = new Date();


        HttpSession session = req.getSession();
        if (session.getAttribute("userEmail") != null) { // Does the user have a logged in session?
            createdByString = (String) session.getAttribute("userEmail");
           // createdByIdString = (String) session.getAttribute("userId");
        }


//      Comment comment = new Comment(Long.parseLong(postId)- 1L,createdByString,createdByIdString,date,commentText);
//                .content(commentText)
//                .createdBy(createdByString)
//                .createdById(createdByIdString)
//                .publishedDate(date)
//                .build();



        PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");


        try{
            daoPost.addCommentInPost(Long.parseLong(postId),createdByString,content);
            resp.sendRedirect("/post/read?id=" + postId);
        }catch (Exception e){
            throw new ServletException("Error adding comment!!!");
        }

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

    }
}
