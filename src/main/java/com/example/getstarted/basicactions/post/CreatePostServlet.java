package com.example.getstarted.basicactions.post;

import com.example.getstarted.daos.interfaces.PostDao;
import com.example.getstarted.objects.*;
import com.example.getstarted.util.CloudStorageHelper;
import com.google.common.base.Strings;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

@SuppressWarnings("serial")
/**
 * Create Post Object and store, display it
 */
public class CreatePostServlet extends HttpServlet {

  // [START setup]

  /**
   * When adding Post, redirect page to form JSP
   * @param req HttpServletRequest
   * @param resp HttpServletResponse
   * @throws ServletException
   * @throws IOException
   */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

      /* If the user has not logged in, don't allow to create post and redirect */
      if (req.getSession().getAttribute("userId") == null) {
          resp.sendRedirect("/login");
          return;
      }

      req.setAttribute("action", "Add");          // Part of the Header in form-post.jsp
      req.setAttribute("destination", "create");  // The urlPattern to invoke (this Servlet)
      req.setAttribute("page", "form-post");           // Tells base.jsp to include form-post.jsp
      req.getRequestDispatcher("/base.jsp").forward(req, resp);
  }
  // [END setup]

  // [START formpost]
  /**
   * Create post object and store in Post4 kind
   * Check whether if created by specific user
   * @param req HttpServletRequest
   * @param resp HttpServletResponse
   * @throws ServletException
   * @throws IOException
   */
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    assert ServletFileUpload.isMultipartContent(req);
    CloudStorageHelper storageHelper = (CloudStorageHelper) getServletContext().getAttribute("storageHelper");

    String newImageUrl = null;
    Map<String, String> params = new HashMap<String, String>();
    try {
      FileItemIterator iter = new ServletFileUpload().getItemIterator(req);
      while (iter.hasNext()) {
        FileItemStream item = iter.next();
        if (item.isFormField()) {
          params.put(item.getFieldName(), Streams.asString(item.openStream()));
        } else if (!Strings.isNullOrEmpty(item.getName())) {
          newImageUrl = storageHelper.uploadFile(
            item, getServletContext().getInitParameter("personshelf.bucket"));
        }
      }
    } catch (FileUploadException e) {
      throw new IOException(e);
    }

    // [START createdBy]
    String createdByString = "";
    String createdByIdString = "";
    Date date = new Date();
    HttpSession session = req.getSession();

    if (session.getAttribute("userEmail") != null) { // Does the user have a logged in session?
        createdByString = (String) session.getAttribute("userEmail");
        createdByIdString = (String) session.getAttribute("userId");
    }
    // [END createdBy]

    // [START postBuilder]
    Post post = new Post.Builder()
//        .slug(params.get("slug"))
        .title(params.get("title"))
        .introduction(params.get("introduction"))
        .category(params.get("category"))
//        .type(params.get("type"))
        .description(params.get("description"))
        .imageUrl(null == newImageUrl ? params.get("imageUrl") : newImageUrl)
        // [START auth]
        .createdBy(createdByString)
        .createdById(createdByIdString)
        .publishedDate(date)
        // [END auth]
        .like(new ArrayList<String>())
        .liked(false)
        .build();
    // [END postBuilder]


    Long postId;
    PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");

    try {
        postId = daoPost.createPost(post);
    } catch (Exception e) {
        throw new ServletException("Error creating post", e);
    }

    resp.sendRedirect("/post/read?id=" + postId.toString());   // read what we just wrote
  }
  // [END formpost]
}