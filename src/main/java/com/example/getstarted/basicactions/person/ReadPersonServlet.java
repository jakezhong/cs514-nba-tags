package com.example.getstarted.basicactions.person;

import com.example.getstarted.daos.DatastoreAssociationDao;
import com.example.getstarted.daos.interfaces.AssociationDao;
import com.example.getstarted.daos.interfaces.GroupDao;
import com.example.getstarted.daos.interfaces.PersonDao;
import com.example.getstarted.daos.interfaces.SocialLinkDao;
import com.example.getstarted.objects.Group;
import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Result;
import com.example.getstarted.objects.SocialLink;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
/**
 * To view all information of a specific person
 */
public class ReadPersonServlet extends HttpServlet {

    /**
    * To view all information of a specific person
    * @param req HttpServletRequest
    * @param resp HttpServletResponse
    * @throws IOException
    * @throws ServletException
    */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
        GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");
        SocialLinkDao daoSocial = (SocialLinkDao) this.getServletContext().getAttribute("dao-social");
        AssociationDao associationDao = (AssociationDao) this.getServletContext().getAttribute("dao-association");

        Long personId = Long.decode(req.getParameter("id"));

        try {
            Person person = daoPerson.readPerson(personId);

            /* If the current person if private and the user is not the author, redirect user */
            if (person.getStatus() != null) {
                if (person.getStatus().equals("private") && !person.getCreatedById().equals(req.getSession().getAttribute("userId"))) {
                    resp.sendRedirect("/persons/user?id="+person.getCreatedById());
                    return;
                }
            }

            String startCursor = req.getParameter("cursor");
            String endCursor;

            List<Group> groups = new ArrayList<>();
            List<Long> groupsId;
            List<SocialLink> socialLinks;

            try {
                Result<Long> result = associationDao.listGroupByPerson(personId,startCursor);
                groupsId = result.result;
                for(Long groupId: groupsId){
                  Group group = daoGroup.readGroup(groupId);
                  groups.add(group);
                }
                endCursor = result.cursor;
            } catch (Exception e) {
                throw new ServletException("Error listing groups", e);
            }

            //get socials
            try {
                Result<SocialLink> result = daoSocial.listSocialLinksByPerson(personId);
                socialLinks = result.result;
            } catch (Exception e) {
                throw new ServletException("Error listing socialLinks", e);
            }

            req.setAttribute("person", person);
            req.setAttribute("socialLinks",socialLinks);
            req.getSession().getServletContext().setAttribute("groups", groups);
            req.setAttribute("cursor", endCursor);
            req.setAttribute("page", "view-person");
            req.getRequestDispatcher("/base.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error reading person", e);
        }
    }
}