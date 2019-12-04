package com.example.getstarted.basicactions.person;

import com.example.getstarted.daos.*;
import com.example.getstarted.daos.interfaces.AssociationDao;
import com.example.getstarted.daos.interfaces.GroupDao;
import com.example.getstarted.objects.Association;
import com.example.getstarted.objects.Group;
import com.example.getstarted.objects.Result;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
/**
 * Create Association between person and group
 */
public class JoinGroupsServlet extends HttpServlet {
    /**
     * When user request Join group, redirect to form-association jsp
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // pass personId
        String personId = req.getParameter("personId");// to create association
        String userId = (String) req.getSession().getAttribute("userId");

        GroupDao daoGroup = (DatastoreGroupDao) this.getServletContext().getAttribute("dao-group");
        AssociationDao associationDao = (AssociationDao) this.getServletContext().getAttribute("dao-association");

        /* Initialize group list */
        List<Long> groupsAlreadyEnjoyIds;
        List<Group> Allgroups ;
        List<Group>groups = new ArrayList<>();

        /* List all groups */
        try {
            Result<Group> result = daoGroup.listAllGroupsByUser(userId);
            groupsAlreadyEnjoyIds= associationDao.listAllGroupByPerson((Long.valueOf(personId))).result;
            Allgroups = result.result;
            for(Group group: Allgroups){
                if(groupsAlreadyEnjoyIds.indexOf(group.getId()) < 0){ // not added
                    groups.add(group);
                }
            }
        } catch (Exception e) {
            throw new ServletException("Error listing groups", e);
        }
        req.setAttribute("personId", personId);
        req.getSession().getServletContext().setAttribute("groups", groups);
        req.setAttribute("action", "Join");          // Part of the Header in form-association.jsp
        req.setAttribute("destination", "/person/join");  // The urlPattern to invoke (this Servlet)
        req.setAttribute("page", "enjoy-group");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }

    // [START formpost]
    /**
     * To create association entity and store in corresponding kind
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] groupsId = req.getParameterValues("groups");
//        System.out.println(Arrays.toString(groupsId));

        Long personId = Long.valueOf(req.getParameter("personId"));
//        System.out.println(personId);

        for (String group: groupsId) {
            Long groupId = Long.parseLong(group);
            Association association = new Association.Builder()
                .personId(personId)
                .groupId(groupId)
                .build();
            DatastoreAssociationDao daoAssociation = new DatastoreAssociationDao();
            try {
                daoAssociation.createAssociation(association);
            } catch (Exception e) {
                throw new ServletException("Error creating association", e);
            }
        }
        resp.sendRedirect("/person/read?id=" + personId);   // read what we just wrote
    }
}