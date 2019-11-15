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

import com.example.getstarted.daos.DatastoreAssociationDao;
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
/**
 * To view all information of a specific group
 */
public class ReadGroupServlet extends HttpServlet {

    /**
     * To view all information of a specific group according by groupId
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            ServletException {
        Long id = Long.decode(req.getParameter("id"));
        GroupDao dao = (GroupDao) this.getServletContext().getAttribute("daoGroup");
     //   DatastoreAssociationDao daoAssociation = (DatastoreAssociationDao) this.getServletContext().getAttribute("dao-association");
        try {
            Group group = dao.readGroup(id);
        //    List<Person> members = daoAssociation.listPersonsByGroup(id,startCursor).result;
        //    System.out.println(members);

            DatastoreAssociationDao associationDao = (DatastoreAssociationDao) this.getServletContext().getAttribute("dao-association");
            String startCursor = req.getParameter("cursor");
            Long groupId = Long.decode(req.getParameter("id"));
            List<Person> persons = null;
            String endCursor = null;

            try {
                Result<Person> result = associationDao.listPersonsByGroup(groupId,startCursor);
                persons=result.result;
                System.out.println(persons);
                endCursor = result.cursor;
            } catch (Exception e) {
                throw new ServletException("Error listing persons", e);
            }

            req.setAttribute("group", group);
            req.getSession().getServletContext().setAttribute("persons", persons);
            req.setAttribute("cursor", endCursor);
            req.setAttribute("page", "viewGroup");
         //   req.setAttribute("persons",members);
            req.getRequestDispatcher("/base.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error reading group", e);
        }
    }
}
// [END example]
