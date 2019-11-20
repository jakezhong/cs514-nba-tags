package com.example.getstarted.basicactions;

import com.example.getstarted.daos.PostDao;
import com.example.getstarted.objects.Post;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// [START example]
@SuppressWarnings("serial")
/**
 * To view all information of a specific post
 */
public class ReadPostServlet extends HttpServlet {

  /**
   * To view all information of a specific post
   * @param req HttpServletRequest
   * @param resp HttpServletResponse
   * @throws IOException
   * @throws ServletException
   */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    Long id = Long.decode(req.getParameter("id"));
    PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");
    try {
      Post post = daoPost.readPost(id);

      req.setAttribute("post", post);
      req.setAttribute("page", "view-post");
      req.getRequestDispatcher("/base.jsp").forward(req, resp);
    } catch (Exception e) {
      throw new ServletException("Error reading post", e);
    }
  }
}
// [END example]
