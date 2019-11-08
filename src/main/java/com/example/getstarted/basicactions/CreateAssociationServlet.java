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

import com.example.getstarted.daos.AssociationDao;
import com.example.getstarted.daos.GroupDao;
import com.example.getstarted.daos.PersonDao;
import com.example.getstarted.objects.Association;
import com.example.getstarted.objects.Group;
import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Result;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// [START example]
@SuppressWarnings("serial")
public class CreateAssociationServlet extends HttpServlet {
    // [START setup]
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = Long.decode(req.getParameter("id"));
            PersonDao personDao = (PersonDao) this.getServletContext().getAttribute("dao-person");
            String startCursor = req.getParameter("cursor");
            List<Person> persons = null;
            String endCursor = null;
            try {
                Result<Person> result = personDao.listPersons(startCursor);
                persons = result.result;
                endCursor = result.cursor;
            } catch (Exception e) {
                throw new ServletException("Error listing persons", e);
            }
            req.getSession().getServletContext().setAttribute("persons", persons);
            req.setAttribute("cursor", endCursor);
            req.setAttribute("groupId", id);
            req.setAttribute("action", "Add");          // Part of the Header in form-association.jsp
            req.setAttribute("destination", "create");  // The urlPattern to invoke (this Servlet)
            req.setAttribute("page", "form-association");           // Tells base.jsp to include form-association.jsp
            req.getRequestDispatcher("/base.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error reading group", e);
        }

    }
    // [END setup]

    // [START formpost]
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        assert ServletFileUpload.isMultipartContent(req);
        CloudStorageHelper storageHelper = (CloudStorageHelper) getServletContext().getAttribute("storageHelper");

        String personId = req.getParameter("personId");
        String groupId = req.getParameter("groupId");

        // [START createdBy]
        String createdByString = "";
        String createdByIdString = "";
        HttpSession session = req.getSession();
        if (session.getAttribute("userEmail") != null) { // Does the user have a logged in session?
            createdByString = (String) session.getAttribute("userEmail");
            createdByIdString = (String) session.getAttribute("userId");
        }
        // [END createdBy]

        // [START GroupBuilder]
        Association association = new Association.Builder()
                .personId(Long.parseLong(personId))
                .groupId(Long.parseLong(groupId))
                .build();
        // [END AssociationBuilder]

        try {
            resp.sendRedirect("/group/read?id=" + groupId);   // read what we just wrote
        } catch (Exception e) {
            throw new ServletException("Error creating association", e);
        }
    }
    // [END formpost]
}
// [END example]
