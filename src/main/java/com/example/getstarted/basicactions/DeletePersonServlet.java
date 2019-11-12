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

 import com.example.getstarted.daos.PersonDao;
 import com.example.getstarted.daos.PersonGroupDao;

 import java.io.IOException;

        import javax.servlet.ServletException;
        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;

// [START example]
@SuppressWarnings("serial")
/**
 * Delete person object according to personId
 */
public class DeletePersonServlet extends HttpServlet {

    /**
     * To delete person object according by personId
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
          IOException {
    Long id = Long.decode(req.getParameter("id"));
    PersonDao dao = (PersonDao) this.getServletContext().getAttribute("dao");
      PersonGroupDao personGroupDao =(PersonGroupDao) this.getServletContext().getAttribute("dao-association");
    try {
      dao.deletePerson(id);
      personGroupDao.deleteAssociationByPersonId(id);
      resp.sendRedirect("/person");
    } catch (Exception e) {
      throw new ServletException("Error deleting person", e);
    }
  }
}
// [END example]
