package com.example.getstarted.basicactions;

import com.example.getstarted.daos.GroupDao;
import com.example.getstarted.objects.Group;
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
 * To List groups by user according to userId
 */
public class ListGroupByUserServlet extends HttpServlet {
    /**
     * list all groups created by specific user according to userID
     * display all persons by cursor( fetch 10 per time)
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        /*
            If there's user id parameter, list group by this id
            Otherwise, list group by logged in user id
         */
        String userId;
        try {
            userId = req.getParameter("id") == null || req.getParameter("id").isEmpty() ? (String) req.getSession().getAttribute("userId") : req.getParameter("id");
        } catch (Exception e) {
            userId = (String) req.getSession().getAttribute("userId");
        }

        GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");
        String startCursor = req.getParameter("cursor");
        List<Group> groups;
        String endCursor;
        try {
            Result<Group> result = daoGroup.listGroupsByUser(userId, startCursor);
            groups = result.result;
            endCursor = result.cursor;
            if (result == null) {
                resp.sendRedirect("/");
            }
        } catch (Exception e) {
            throw new ServletException("Error listing groups", e);
        }
        req.getSession().getServletContext().setAttribute("groups", groups);
        req.getSession().setAttribute("cursor", endCursor);
        req.getSession().setAttribute("page", "list-user-groups");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
}
// [END example]
