package com.example.getstarted.basicactions.group;

import com.example.getstarted.daos.interfaces.GroupDao;
import com.example.getstarted.objects.Group;
import com.example.getstarted.objects.Result;
import java.io.IOException;
import java.util.Hashtable;
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
        Hashtable<String, String> search = new Hashtable<String, String>();

        List<Group> groups;
        // Cursor variables
        String startCursor = req.getParameter("cursor");
        String endCursor;
        // Search variables
        String searchName = req.getParameter("name");
        String searchCategory = req.getParameter("category");

        try {
            Result<Group> result;

            /* Check if there's search params, if so, list group by search, otherwise, list group by default */
            if (!(searchName == null && searchCategory == null)) {
                if (searchName != null && !searchName.isEmpty()) {
                    search.put("name", searchName);
                }
                if (searchCategory != null && !searchCategory.isEmpty()) {
                    search.put("category", searchCategory);
                }
                result = daoGroup.listGroupsBySearch(search, startCursor);
            } else {
                result = daoGroup.listGroups(startCursor);
            }

            groups = result.result;
            endCursor = result.cursor;
        } catch (Exception e) {
            throw new ServletException("Error listing groups", e);
        }
        req.getSession().getServletContext().setAttribute("groups", groups);
        req.setAttribute("name", searchName);
        req.setAttribute("category", searchCategory);
        req.setAttribute("cursor", endCursor);
        req.setAttribute("page", "list-group");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
}
// [END example]
