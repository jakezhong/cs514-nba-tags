package com.example.getstarted.basicactions;

import com.example.getstarted.daos.PostDao;
import com.example.getstarted.objects.Post;
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
 * To list all posts
 */
public class ListPostServlet extends HttpServlet {

    /**
     *List all posts / display by using cursor (fetch 10 by time)
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            ServletException {
        PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");
        String startCursor = req.getParameter("cursor");
        List<Post> posts;
        posts = null;
        String endCursor = null;
        try {
            Result<Post> result = daoPost.listPosts(startCursor);
            posts = result.result;
            endCursor = result.cursor;
        } catch (Exception e) {
            throw new ServletException("Error listing posts", e);
        }
        req.getSession().getServletContext().setAttribute("posts", posts);
        req.setAttribute("cursor", endCursor);
        req.setAttribute("page", "list-post");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
}
// [END example]
