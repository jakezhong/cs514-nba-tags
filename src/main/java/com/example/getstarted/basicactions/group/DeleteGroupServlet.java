package com.example.getstarted.basicactions.group;

import com.example.getstarted.daos.interfaces.GroupDao;
import com.example.getstarted.daos.interfaces.AssociationDao;
import com.example.getstarted.daos.interfaces.PostTagDao;

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
        Long id = Long.decode(req.getParameter("id"));
        GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");
        AssociationDao daoAssociation =(AssociationDao) this.getServletContext().getAttribute("dao-association");
        PostTagDao dapPostTag =(PostTagDao) this.getServletContext().getAttribute("dao-postTag");

        try {
            daoGroup.deleteGroup(id);
            daoAssociation.deleteAssociationByGroupId(id);
            dapPostTag.deletePostTagByGroupId(id);
            resp.sendRedirect("/groups/user");
        } catch (Exception e) {
            throw new ServletException("Error deleting group", e);
        }
    }
}