package com.example.getstarted.basicactions.post;
import com.example.getstarted.daos.interfaces.PostDao;
import com.example.getstarted.daos.interfaces.PostTagDao;
import com.example.getstarted.objects.Post;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@SuppressWarnings("serial")
public class DeletePostTagServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");
        PostTagDao daoPostTag = (PostTagDao) this.getServletContext().getAttribute("dao-postTag");

        Long postId = Long.valueOf(req.getParameter("postId"));




        /* If the current user is not the post author, redirect */
        try {
            Post post = daoPost.readPost(postId);
            if (!post.getCreatedById().equals(req.getSession().getAttribute("userId"))) {
                resp.sendRedirect("/login");
                return;
            }
        } catch (Exception e) {
            resp.sendRedirect("/posts");
            return;
        }

        String[] personIds = req.getParameterValues("persons");
        String[] groupIds = req.getParameterValues("groups");

        if (personIds != null) {
            for (String person: personIds) {
                Long personId = Long.parseLong(person);
                try {
                    daoPostTag.deletePostTagByPostIdPersonId(postId, personId);
                } catch (Exception e) {
                    throw new ServletException("Error deleting person tag", e);
                }
            }
        } else if (groupIds != null) {
            for (String group: groupIds) {
                Long groupId = Long.parseLong(group);
                try {
                    daoPostTag.deletePostTagByPostIdGroupId(postId, groupId);
                } catch (Exception e) {
                    throw new ServletException("Error deleting group tag", e);
                }
            }
        }


        resp.sendRedirect("/post/read?id="+postId);   // read what we just wrote
    }
}
