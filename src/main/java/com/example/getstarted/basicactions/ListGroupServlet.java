package com.example.getstarted.basicactions;

import com.example.getstarted.daos.*;
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
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            ServletException {
        GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("daoGroup");
        String startCursor = req.getParameter("cursor");
        List<Group> groups;
        groups = null;
        String endCursor = null;
        try {
            Result<Group> result = daoGroup.listGroups(startCursor);
            groups = result.result;
            endCursor = result.cursor;
        } catch (Exception e) {
            throw new ServletException("Error listing groups", e);
        }
        req.getSession().getServletContext().setAttribute("groups", groups);
        StringBuilder personNames = new StringBuilder();
        for (Group group : groups) {
            personNames.append(group.getName() + " ");
        }
        req.setAttribute("cursor", endCursor);
        req.setAttribute("page", "listGroup");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
}
// [END example]
