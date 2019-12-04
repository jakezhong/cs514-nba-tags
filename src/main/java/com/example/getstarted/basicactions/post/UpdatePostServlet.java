package com.example.getstarted.basicactions.post;

import com.example.getstarted.daos.interfaces.PostDao;
import com.example.getstarted.objects.Post;
import com.example.getstarted.util.CloudStorageHelper;
import com.google.common.base.Strings;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
/**
* To update post info
*/
public class UpdatePostServlet extends HttpServlet {
    /**
    * To redirect to page form jsp
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

        PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");
        try {
            Post post = daoPost.readPost(Long.decode(req.getParameter("id")));
            req.setAttribute("post", post);
            req.setAttribute("action", "Edit");
            req.setAttribute("destination", "update");
            req.setAttribute("page", "form-post");
            req.getRequestDispatcher("/base.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error loading post for editing", e);
        }
    }

    /**
    * According to updated info to create post object and store it datastore
    * @param req HttpServletRequest
    * @param resp HttpServletResponse
    * @throws ServletException
    * @throws IOException
    */

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");

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

        try {
            Post oldPost = daoPost.readPost(Long.decode(params.get("id")));

            // [START postBuilder]
            Post post = new Post.Builder()
            .id(Long.decode(params.get("id")))
            .slug(params.get("slug"))
            .title(params.get("title"))
            .introduction(params.get("introduction"))
            .category(params.get("category"))
            .status(params.get("status"))
            .description(params.get("description"))
            .imageUrl(null == newImageUrl ? params.get("imageUrl") : newImageUrl)
            .createdBy(oldPost.getCreatedBy())
            .createdById(oldPost.getCreatedById())
            .publishedDate(oldPost.getPublishedDate())
            .like(oldPost.getLike())
            .liked(oldPost.getLiked())
            .build();
            // [END postBuilder]

            daoPost.updatePost(post);
            resp.sendRedirect("read?id=" + params.get("id"));
        } catch (Exception e) {
            throw new ServletException("Error updating post", e);
        }
    }
}