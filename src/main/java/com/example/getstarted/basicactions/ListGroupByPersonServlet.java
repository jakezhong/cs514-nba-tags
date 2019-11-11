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

import com.example.getstarted.daos.DatastorePersonGroupDao;
import com.example.getstarted.daos.PersonDao;
import com.example.getstarted.daos.CloudSqlDao;
import com.example.getstarted.daos.DatastoreDao;
import com.example.getstarted.objects.Group;
import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Result;
import com.example.getstarted.util.CloudStorageHelper;

import com.google.common.base.Strings;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// [START example]
@SuppressWarnings("serial")
/**
 * List all groups which a specific person enjoyed
 */
public class ListGroupByPersonServlet extends HttpServlet {

    /**
     * List all groups which a specific person enjoyed r according to personId
     * display all persons by cursor( fetch 10 per time)
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            ServletException {
        DatastorePersonGroupDao dao = (DatastorePersonGroupDao) this.getServletContext().getAttribute("dao-association");
        String startCursor = req.getParameter("cursor");
        long peopleId ;
        if(startCursor!=null){
            peopleId = (Long)req.getSession().getServletContext().getAttribute("id");
        }else{
            peopleId = Long.decode(req.getParameter("id"));
        }

        System.out.println(peopleId);
        List<Group> groups = null;
        String endCursor = null;
        try {
            Result<Group> result = dao.listGroupByPerson(peopleId,startCursor);
            groups=result.result;
            System.out.println(groups);
            endCursor = result.cursor;
        } catch (Exception e) {
            throw new ServletException("Error listing persons", e);
        }
        req.getSession().getServletContext().setAttribute("groups", groups);
        req.getSession().getServletContext().setAttribute("id", peopleId);

        req.setAttribute("cursor", endCursor);
        req.setAttribute("page", "listGroupByPerson");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
}
// [END example]
