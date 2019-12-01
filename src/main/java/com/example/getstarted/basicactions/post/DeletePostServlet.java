package com.example.getstarted.basicactions.post;

import com.example.getstarted.daos.interfaces.PostDao;
import com.example.getstarted.daos.interfaces.PostTagDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
/**
    * Delete post object according to postId
*/
public class DeletePostServlet extends HttpServlet {
    /**
        * To delete post object according by postId
        * @param req HttpServletRequest
        * @param resp HttpServletResponse
        * @throws ServletException
        * @throws IOException
    */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.decode(req.getParameter("id"));
        PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");
        PostTagDao dapPostTag =(PostTagDao) this.getServletContext().getAttribute("dao-postTag");

        try {
            daoPost.deletePost(id);
            dapPostTag.deletePostTagByPostId(id);
            resp.sendRedirect("/posts/user");
        } catch (Exception e) {
            throw new ServletException("Error deleting post", e);
        }
    }
}