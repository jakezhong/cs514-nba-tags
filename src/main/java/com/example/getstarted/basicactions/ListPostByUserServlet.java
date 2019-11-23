package com.example.getstarted.basicactions;

import com.example.getstarted.daos.PostDao;
import com.example.getstarted.daos.UserDao;
import com.example.getstarted.objects.OurUser;
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
 * To List posts by user according to userId
 */
public class ListPostByUserServlet extends HttpServlet {

  /**
   * list all posts created by specific user according to userID
   * display all posts by cursor( fetch 10 per time)
   * @param req HttpServletRequest
   * @param resp HttpServletResponse
   * @throws IOException
   * @throws ServletException
   */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    /*
            Load user first
         */
    String id = req.getParameter("id");
    OurUser currentUser = null;
    UserDao daoUser = (UserDao) this.getServletContext().getAttribute("dao-user");
    if (id == null || id.isEmpty()) {
      if (req.getSession().getAttribute("userEmail") == null || ((String) req.getSession().getAttribute("userEmail")).isEmpty()) {
        resp.sendRedirect("/");
      } else {
        currentUser= (OurUser) this.getServletContext().getAttribute("login-user");
      }
    } else {
      try {
        Long userId = Long.decode(id);
        currentUser = daoUser.readUser(userId);
      } catch (Exception e) {
        resp.sendRedirect("/");
      }
    }
    /*
        Load posts after user
     */
    PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");
    String startCursor = req.getParameter("cursor");
    List<Post> posts = null;
    String endCursor = null;
    try {
      Result<Post> result = daoPost.listPostsByUser((String) req.getSession().getAttribute("userId"), startCursor);
      posts = result.result;
      endCursor = result.cursor;
    } catch (Exception e) {
      throw new ServletException("Error listing posts", e);
    }
    req.setAttribute("user", currentUser);
    req.getSession().getServletContext().setAttribute("posts", posts);
    req.getSession().setAttribute("cursor", endCursor);
    req.getSession().setAttribute("page", "view-user-posts");
    req.getRequestDispatcher("/base.jsp").forward(req, resp);
  }
}
// [END example]
