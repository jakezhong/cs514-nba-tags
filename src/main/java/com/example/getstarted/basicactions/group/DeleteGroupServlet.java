package com.example.getstarted.basicactions.group;

import com.example.getstarted.daos.interfaces.GroupDao;
import com.example.getstarted.daos.interfaces.AssociationDao;
import com.example.getstarted.daos.interfaces.PostTagDao;
import com.example.getstarted.objects.Group;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
/**
    * Delete group object according to groupId
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
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long groupId = Long.decode(req.getParameter("id"));
        GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");
        AssociationDao daoAssociation =(AssociationDao) this.getServletContext().getAttribute("dao-association");
        PostTagDao dapPostTag =(PostTagDao) this.getServletContext().getAttribute("dao-postTag");

        /* If the current user is not the post author, redirect */
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

        try {
            daoGroup.deleteGroup(groupId);
            daoAssociation.deleteAssociationByGroupId(groupId);
            dapPostTag.deletePostTagByGroupId(groupId);
            resp.sendRedirect("/groups/user");
        } catch (Exception e) {
            throw new ServletException("Error deleting group", e);
        }
    }
}