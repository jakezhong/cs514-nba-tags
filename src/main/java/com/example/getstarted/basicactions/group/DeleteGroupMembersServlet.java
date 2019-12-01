package com.example.getstarted.basicactions.group;

import com.example.getstarted.daos.DatastoreAssociationDao;
import com.example.getstarted.objects.Association;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "DeleteGroupMembersServlet")
public class DeleteGroupMembersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] personsId = req.getParameterValues("members");
        System.out.println(Arrays.toString(personsId));

        Long groupId = Long.valueOf(req.getParameter("groupId"));
        System.out.println(groupId);

        //   [START GroupBuilder]
        for (String person: personsId) {
            Long personId = Long.parseLong(person);
            //[END AssociationBuilder]
            DatastoreAssociationDao daoAssociation = new DatastoreAssociationDao();
            try {
                daoAssociation.deleteAssociationByGroupPersonID(groupId,personId);

            } catch (Exception e) {
                throw new ServletException("Error creating association", e);
            }
        }
        resp.sendRedirect("/group/read?id=" +groupId);   // read what we just wrote

    }


}
