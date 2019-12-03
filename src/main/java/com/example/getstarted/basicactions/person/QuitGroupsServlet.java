package com.example.getstarted.basicactions.person;

import com.example.getstarted.daos.DatastoreAssociationDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "QuitGroupsServlet")
public class QuitGroupsServlet extends HttpServlet {

        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            String[] groupsId = req.getParameterValues("groups");
//            System.out.println(Arrays.toString(groupsId));

            Long personId = Long.valueOf(req.getParameter("personId"));
//            System.out.println(personId);

            //   [START GroupBuilder]
            for (String group: groupsId) {
                Long groupId = Long.parseLong(group);
                //[END AssociationBuilder]
                DatastoreAssociationDao daoAssociation = new DatastoreAssociationDao();
                try {
                    daoAssociation.deleteAssociationByGroupIdPersonId(groupId,personId);

                } catch (Exception e) {
                    throw new ServletException("Error deleting association", e);
                }
            }
            resp.sendRedirect("/person/read?id="+personId);   // read what we just wrote
        }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
