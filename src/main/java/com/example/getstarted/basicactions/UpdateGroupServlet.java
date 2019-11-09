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

// [START example]
@SuppressWarnings("serial")
public class UpdateGroupServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        GroupDao dao = (GroupDao) this.getServletContext().getAttribute("daoGroup");
        try {
            Group group = dao.readGroup(Long.decode(req.getParameter("id")));
            req.setAttribute("group", group);
            req.setAttribute("action", "Edit");
            req.setAttribute("destination", "updateGroup");
            req.setAttribute("page", "formGroup");
            req.getRequestDispatcher("/base.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error loading person for editing", e);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        GroupDao dao = (GroupDao) this.getServletContext().getAttribute("daoGroup");

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

        try {
            Group oldGroup = dao.readGroup(Long.decode(params.get("id")));

            // [START personBuilder]
            Group group = new Group.Builder()
                    .name(params.get("name"))
                    .description(params.get("description"))
//                    .first(params.get("first"))
                    .imageUrl(null == newImageUrl ? params.get("imageUrl") : newImageUrl)
                    .id(Long.decode(params.get("id")))
                    .createdBy(oldGroup.getCreatedBy())
                    .createdById(oldGroup.getCreatedById())
                    .build();
            // [END groupBuilder]

            dao.updateGroup(group);
            resp.sendRedirect("/readGroup?id=" + params.get("id"));
        } catch (Exception e) {
            throw new ServletException("Error updating group", e);
        }
    }
}
// [END example]
