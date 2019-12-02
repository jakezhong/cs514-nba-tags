package com.example.getstarted.basicactions.post;

import com.example.getstarted.daos.DatastorePostTagDao;
import com.example.getstarted.daos.interfaces.PostTagDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
public class DeletePostTagServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostTagDao daoPostTag = (PostTagDao) this.getServletContext().getAttribute("dao-post-tag");

        String[] personTags = req.getParameterValues("person-tags");
        String[] groupTags = req.getParameterValues("group-tags");

        Long postId = Long.valueOf(req.getParameter("postId"));

        //   [START GroupBuilder]
        for (String person: personTags) {
            Long personId = Long.parseLong(person);
            //[END AssociationBuilder]
            try {
                daoPostTag.deletePostTagByPostIdPersonId(postId, personId);
            } catch (Exception e) {
                throw new ServletException("Error creating association", e);
            }
        }
        resp.sendRedirect("/posts/user");   // read what we just wrote

    }


}
