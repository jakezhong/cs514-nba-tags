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
import com.example.getstarted.daos.PersonDao;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// [START example]
@SuppressWarnings("serial")
/**
 * To Delete Group Object
 */
public class DeleteGroupServlet extends HttpServlet {
    /**
     * To delete Group object according by groupId
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        Long id = Long.decode(req.getParameter("id"));
        GroupDao dao = (GroupDao) this.getServletContext().getAttribute("daoGroup");
        try {
            dao.deleteGroup(id);
            resp.sendRedirect("/groups/mine");
        } catch (Exception e) {
            throw new ServletException("Error deleting group", e);
        }
    }
}
// [END example]
