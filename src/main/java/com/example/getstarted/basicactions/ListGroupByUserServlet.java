/*
 * Copyright 2016 Google Inc.
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
import com.example.getstarted.objects.Group;
import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Result;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// [START example]
@SuppressWarnings("serial")
public class ListGroupByUserServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            ServletException {
        GroupDao dao = (GroupDao) this.getServletContext().getAttribute("daoGroup");
        String startCursor = req.getParameter("cursor");
        List<Group> groups = null;
        String endCursor = null;
        try {
            Result<Group> result =
                    dao.listGroupsByUser((String) req.getSession().getAttribute("userId"), startCursor);
            groups = result.result;
            endCursor = result.cursor;
        } catch (Exception e) {
            throw new ServletException("Error listing groups", e);
        }
        req.getSession().getServletContext().setAttribute("groups", groups);
//        StringBuilder groupNames = new StringBuilder();
//        for (Group group : groups) {
//            groupNames.append(group.getName() + " ");
//        }
        req.getSession().setAttribute("cursor", endCursor);
        req.getSession().setAttribute("page", "listGroup");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
}
// [END example]
