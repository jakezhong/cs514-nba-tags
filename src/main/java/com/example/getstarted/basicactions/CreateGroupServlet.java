/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.getstarted.basicactions;

import com.example.getstarted.daos.GroupDao;
import com.example.getstarted.objects.Group;
import com.example.getstarted.util.CloudStorageHelper;
import com.google.common.base.Strings;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

// [START example]
@SuppressWarnings("serial")
/**
 * Create group object
 */
public class CreateGroupServlet extends HttpServlet {

    // [START setup]

    /**
     * When request add Group, redirect the page to form-group JSP
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        req.setAttribute("action", "Add");          // Part of the Header in person-form.jsp
        req.setAttribute("destination", "group/create");  // The urlPattern to invoke (this Servlet)
        req.setAttribute("page", "form-group");           // Tells base.jsp to include person-form.jsp
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
    // [END setup]

    // [START formpost]
    /**
     * Create  an Group Object and store in Group4 Kind, store image to bucket
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        assert ServletFileUpload.isMultipartContent(req);
        CloudStorageHelper storageHelper =
                (CloudStorageHelper) getServletContext().getAttribute("storageHelper");

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
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        HttpSession session = req.getSession();
        if (session.getAttribute("userEmail") != null) { // Does the user have a logged in session?
            createdByString = (String) session.getAttribute("userEmail");
            createdByIdString = (String) session.getAttribute("userId");
        }
        // [END createdBy]

        // [START personBuilder]
        Group group = new Group.Builder()
                .name(params.get("name"))
                .introduction(params.get("introduction"))
                .category(params.get("category"))
                .type(params.get("type"))
                .linkedin(params.get("linkedin"))
                .facebook(params.get("facebook"))
                .twitter(params.get("twitter"))
                .instagram(params.get("instagram"))
                .youtube(params.get("youtube"))
                .website(params.get("website"))
                .description(params.get("description"))
                .imageUrl(null == newImageUrl ? params.get("imageUrl") : newImageUrl)
                // [START auth]
                .createdBy(createdByString)
                .createdById(createdByIdString)
                .createdDate(date)
                // [END auth]
                .build();
        // [END groupBuilder]

        GroupDao dao = (GroupDao) this.getServletContext().getAttribute("daoGroup");
        try {
            Long id = dao.createGroup(group);
            resp.sendRedirect("group/read?id=" + id.toString());   // read what we just wrote
        } catch (Exception e) {
            throw new ServletException("Error creating group", e);
        }
    }
    // [END formpost]
}
// [END example]
