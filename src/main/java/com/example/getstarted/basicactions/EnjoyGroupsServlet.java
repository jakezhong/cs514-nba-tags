package com.example.getstarted.basicactions;

import com.example.getstarted.daos.*;
import com.example.getstarted.objects.Association;
import com.example.getstarted.objects.Group;
import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Result;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// [START example]
@SuppressWarnings("serial")
/**
 * Create Association between person and group
 */
public class EnjoyGroupsServlet extends HttpServlet {

    // [START setup]

    /**
     * When user request Join group, redirect to form-association jsp
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        // pass personId

            String personId = req.getParameter("personId");// to create association

            GroupDao daoGroup = (DatastoreGroupDao) this.getServletContext().getAttribute("dao-group");
            AssociationDao associationDao = (AssociationDao) this.getServletContext().getAttribute("dao-association");
            String startCursor = req.getParameter("cursor");
            List<Long> groupsAlreadyEnjoyIds;
            List<Group> Allgroups ;
            List<Group>groups = new ArrayList<>();
            String endCursor;

            try {
                Result<Group> result = daoGroup.listGroups(startCursor);
                groupsAlreadyEnjoyIds= associationDao.listGroupByPerson(Long.valueOf(personId),startCursor).result;
                Allgroups = result.result;
                for(Group group: Allgroups){
                    if(groupsAlreadyEnjoyIds.indexOf(group.getId())==-1){ // not added
                        groups.add(group);
                    }
                }
                endCursor = result.cursor;

            } catch (Exception e) {
                throw new ServletException("Error listing groups", e);
            }
            req.getSession().getServletContext().setAttribute("groups", groups);
            req.setAttribute("personId",personId);

            req.setAttribute("cursor",endCursor);
            req.setAttribute("page","enjoy-Groups");

            req.getRequestDispatcher("/base.jsp").forward(req, resp);


    }
    // [END setup]

    // [START formpost]

    /**
     * To create association entity and store in corresponding kind
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {

        String[] groupsId = req.getParameterValues("groups");
        System.out.println(Arrays.toString(groupsId));

        Long personId = Long.valueOf(req.getParameter("personId"));
        System.out.println(personId);

        //   [START GroupBuilder]
        for (String group: groupsId) {
             Long groupId = Long.parseLong(group);
             Association association = new Association.Builder()
                    .personId(personId)
                    .groupId(groupId)
                    .build();
             //[END AssociationBuilder]
            DatastoreAssociationDao daoAssociation = new DatastoreAssociationDao();
            try {
                daoAssociation.createAssociation(association);

            } catch (Exception e) {
                throw new ServletException("Error creating association", e);
            }
        }
        resp.sendRedirect("/person/read?id=" +personId);   // read what we just wrote
    }
}
// [END example]

