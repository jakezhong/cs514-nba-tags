package com.example.getstarted.basicactions.group;

import com.example.getstarted.daos.*;
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
 * To list all groups
 */
public class ListGroupServlet extends HttpServlet {

    /**
     *List all groups / display by using cursor (fetch 10 by time)
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");
        String startCursor = req.getParameter("cursor");
        List<Group> groups;
        String endCursor;
        try {
            Result<Group> result = daoGroup.listGroups(startCursor);
            groups = result.result;
            endCursor = result.cursor;
        } catch (Exception e) {
            throw new ServletException("Error listing groups", e);
        }
        req.getSession().getServletContext().setAttribute("groups", groups);
        req.setAttribute("cursor", endCursor);
        req.setAttribute("page", "list-group");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
}
// [END example]
