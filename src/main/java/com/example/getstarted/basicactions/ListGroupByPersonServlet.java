package com.example.getstarted.basicactions;

import com.example.getstarted.daos.DatastoreAssociationDao;
import com.example.getstarted.daos.DatastoreGroupDao;
import com.example.getstarted.objects.Group;
import com.example.getstarted.objects.Result;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// [START example]
@SuppressWarnings("serial")
/**
 * List all groups which a specific person enjoyed
 */
public class ListGroupByPersonServlet extends HttpServlet {

    /**
     * List all groups which a specific person enjoyed r according to personId
     * display all persons by cursor( fetch 10 per time)
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            ServletException {
        DatastoreAssociationDao daoAssociation = (DatastoreAssociationDao) this.getServletContext().getAttribute("dao-association");
        DatastoreGroupDao groupDao = (DatastoreGroupDao) this.getServletContext().getAttribute("dao-group");
        String startCursor = req.getParameter("cursor");
        long peopleId ;
        if(startCursor!=null){
            peopleId = (Long)req.getSession().getServletContext().getAttribute("id");
        }else{
            peopleId = Long.decode(req.getParameter("id"));
        }

        System.out.println(peopleId);
        List<Group> groups = new ArrayList<>();
        List<Long> groupsId ;
        String endCursor ;
        try {
            Result<Long> result = daoAssociation.listGroupByPerson(peopleId,startCursor);
            groupsId=result.result;
            for(Long groupId : groupsId){
                Group group = groupDao.readGroup(groupId);
                groups.add(group);
            }
            endCursor = result.cursor;
        } catch (Exception e) {
            throw new ServletException("Error listing groups", e);
        }
        req.getSession().getServletContext().setAttribute("groups", groups);
        req.getSession().getServletContext().setAttribute("id", peopleId);

        req.setAttribute("cursor", endCursor);
        req.setAttribute("page", "list-association");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
}
// [END example]
