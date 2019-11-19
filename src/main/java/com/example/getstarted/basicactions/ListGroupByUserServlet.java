package com.example.getstarted.basicactions;

import com.example.getstarted.daos.GroupDao;
import com.example.getstarted.daos.UserDao;
import com.example.getstarted.objects.Group;
import com.example.getstarted.objects.OurUser;
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
            Load user first
         */
        String id = req.getParameter("id");
        OurUser currentUser = null;
        UserDao daoUser = (UserDao) this.getServletContext().getAttribute("dao-user");
        if (id == null || id.isEmpty()) {
            if (req.getSession().getAttribute("userEmail") == null || ((String) req.getSession().getAttribute("userEmail")).isEmpty()) {
                resp.sendRedirect("/");
            } else {
                currentUser= (OurUser) this.getServletContext().getAttribute("login-user");
            }
        } else {
            try {
                Long userId = Long.decode(id);
                currentUser = daoUser.readUser(userId);
            } catch (Exception e) {
                resp.sendRedirect("/");
            }
        }
        /*
            Load groups after user
         */
        GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");
        String startCursor = req.getParameter("cursor");
        List<Group> groups = null;
        String endCursor = null;
        try {
            Result<Group> result = daoGroup.listGroupsByUser((String) req.getSession().getAttribute("userId"), startCursor);
            groups = result.result;
            endCursor = result.cursor;
        } catch (Exception e) {
            throw new ServletException("Error listing groups", e);
        }
        req.setAttribute("user", currentUser);
        req.getSession().getServletContext().setAttribute("groups", groups);
        req.getSession().setAttribute("cursor", endCursor);
        req.getSession().setAttribute("page", "view-user-groups");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
}
// [END example]
