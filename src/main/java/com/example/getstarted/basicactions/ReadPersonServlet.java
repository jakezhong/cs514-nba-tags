package com.example.getstarted.basicactions;

import com.example.getstarted.daos.DatastoreAssociationDao;
import com.example.getstarted.daos.GroupDao;
import com.example.getstarted.daos.PersonDao;
import com.example.getstarted.daos.SocialLinkDao;
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

// [START example]
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
    Long id = Long.decode(req.getParameter("id"));
    PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
    GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");
    SocialLinkDao daoSocial = (SocialLinkDao) this.getServletContext().getAttribute("dao-social");
    DatastoreAssociationDao associationDao = (DatastoreAssociationDao) this.getServletContext().getAttribute("dao-association");
    try {
      Person person = daoPerson.readPerson(id);

      String startCursor = req.getParameter("cursor");
      Long personId = Long.decode(req.getParameter("id"));
      List<Group> groups = new ArrayList<>();
      List<Long> groupsId;
      List<SocialLink> socialLinks;
      String endCursor;

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
// [END example]
