package com.example.getstarted.basicactions.group;

import com.example.getstarted.daos.DatastoreAssociationDao;
import com.example.getstarted.daos.interfaces.AssociationDao;
import com.example.getstarted.daos.interfaces.GroupDao;
import com.example.getstarted.objects.Association;
import com.example.getstarted.objects.Group;

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
        GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");

        Long groupId = Long.valueOf(req.getParameter("groupId"));

        /* If the current user is not the post author, redirect */
        try {
            Group group = daoGroup.readGroup(groupId);
            if (!group.getCreatedById().equals(req.getSession().getAttribute("userId"))) {
                resp.sendRedirect("/login");
                return;
            }
        } catch (Exception e) {
            resp.sendRedirect("/group/read?id="+groupId.toString());
            return;
        }

        /* If the current user is not the group author, redirect */
        try {
            Group group = daoGroup.readGroup(groupId);
            if (!group.getCreatedById().equals(req.getSession().getAttribute("userId"))) {
                resp.sendRedirect("/login");
                return;
            }
        } catch (Exception e) {
            resp.sendRedirect("/groups");
            return;
        }

        String[] personIds = req.getParameterValues("members");

//        System.out.println(groupId);

        if (personIds != null) {
            for (String person: personIds) {
                Long personId = Long.parseLong(person);
                AssociationDao daoAssociation = new DatastoreAssociationDao();
                try {
                    daoAssociation.deleteAssociationByGroupIdPersonId(groupId, personId);
                } catch (Exception e) {
                    throw new ServletException("Error deleting association", e);
                }
            }
        }
        resp.sendRedirect("/group/read?id="+groupId);   // read what we just wrote
    }
}
