package com.example.getstarted.basicactions.group;

import com.example.getstarted.daos.interfaces.GroupDao;
import com.example.getstarted.objects.Group;
import com.example.getstarted.util.CloudStorageHelper;
import com.google.common.base.Strings;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

@SuppressWarnings("serial")
/**
 * To update group info
 */
public class UpdateGroupServlet extends HttpServlet {
    /**
     * To redirect to page formGroup jsp
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");
        Long groupId = Long.decode(req.getParameter("id"));

        /* If the current user is not the post author, redirect */
        try {
            Group group = daoGroup.readGroup(groupId);
            if (!group.getCreatedById().equals(req.getSession().getAttribute("userId"))) {
                resp.sendRedirect("/login");
                return;
            }
        } catch (Exception e) {
            resp.sendRedirect("/groups");
            return;
        }

        try {
            Group group = daoGroup.readGroup(Long.decode(req.getParameter("id")));
            req.setAttribute("group", group);
            req.setAttribute("action", "Edit");
            req.setAttribute("destination", "update");
            req.setAttribute("page", "form-group");
            req.getRequestDispatcher("/base.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error loading person for editing", e);
        }
    }

    /**
     * According to updated info to create group object and store it datastore
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");

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
            Group oldGroup = daoGroup.readGroup(Long.decode(params.get("id")));

            // [START personBuilder]
            Group group = new Group.Builder()
                .id(Long.decode(params.get("id")))
                .name(params.get("name"))
                .introduction(params.get("introduction"))
                .category(params.get("category"))
                .status(params.get("status"))
                .description(params.get("description"))
                .imageUrl(null == newImageUrl ? params.get("imageUrl") : newImageUrl)
                // [START auth]
                .createdBy(oldGroup.getCreatedBy())
                .createdById(oldGroup.getCreatedById())
                .createdDate(oldGroup.getCreatedDate())
                // [END auth]
                .build();
            // [END groupBuilder]

            daoGroup.updateGroup(group);
            resp.sendRedirect("read?id=" + params.get("id"));
        } catch (Exception e) {
            throw new ServletException("Error updating group", e);
        }
    }
}