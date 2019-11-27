package com.example.getstarted.basicactions;

import com.example.getstarted.daos.AssociationDao;
import com.example.getstarted.daos.GroupDao;
import com.example.getstarted.daos.PersonDao;
import com.example.getstarted.daos.PostTagDao;
import com.example.getstarted.objects.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
/**
 * Create Association between person and group
 */
public class CreatePostTagServlet extends HttpServlet {

    // [START setup]

    /**
     * When user request Join group, redirect to form-association jsp
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long postId = Long.decode(req.getParameter("id"));
            String userId = (String) req.getSession().getAttribute("userId");

            PostTagDao daoPostTag = (PostTagDao) this.getServletContext().getAttribute("dao-postTag");
            PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
            GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");

            List<Person> allPersons;
            List<Long> oldPersonIds;
            List<Person> persons = new ArrayList<Person>();
            try {
                /* List all persons */
                Result<Person> result = daoPerson.listAllPersonsByUser(userId);
                allPersons = result.result;

                /* List all persons from this group */
                Result<Long> newResult = daoPostTag.listAllPersonByPost(postId);
                oldPersonIds = newResult.result;

                /* If the current person is already in this group, skip it */
                for (Person person: allPersons) {
                    if (oldPersonIds.indexOf(person.getId()) < 0) {
                        persons.add(person);
                    }
                }
            } catch (Exception e) {
                throw new ServletException("Error listing persons", e);
            }

            List<Group> allGroups;
            List<Long> oldGroupIds;
            List<Group> groups = new ArrayList<Group>();
            try {
                /* List all persons */
                Result<Group> result = daoGroup.listAllGroupsByUser(userId);
                allGroups = result.result;

                /* List all persons from this group */
                Result<Long> newResult = daoPostTag.listAllGroupByPost(postId);
                oldGroupIds = newResult.result;

                /* If the current person is already in this group, skip it */
                for (Group group: allGroups) {
                    if (oldGroupIds.indexOf(group.getId()) < 0) {
                        groups.add(group);
                    }
                }
            } catch (Exception e) {
                throw new ServletException("Error listing groups", e);
            }

            req.setAttribute("persons", persons);
            req.setAttribute("groups", groups);
            req.setAttribute("postId", postId);
            req.setAttribute("action", "Add");          // Part of the Header in form-association.jsp
            req.setAttribute("destination", "/post-tag/create");  // The urlPattern to invoke (this Servlet)
            req.setAttribute("page", "form-post-tag");           // Tells base.jsp to include form-association.jsp
            req.getRequestDispatcher("/base.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error reading tag", e);
        }

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
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostTagDao daoPostTag = (PostTagDao) this.getServletContext().getAttribute("dao-postTag");
        Long postId = (req.getParameter("postId") != null) ? Long.decode(req.getParameter("postId")) : null;
        Long personId = (req.getParameter("personId") != null) ? Long.decode(req.getParameter("personId")) : null;
        Long groupId = (req.getParameter("groupId") != null) ? Long.decode(req.getParameter("groupId")) : null;

        //   [START PostPersonBuilder]
        PostTag postTag = new PostTag.Builder()
            .postId(postId)
            .personId(personId)
            .groupId(groupId)
            .build();
        try {
            daoPostTag.createPostTag(postTag);
            if (postId != null) {
                resp.sendRedirect("/post/read?id=" + postId.toString());   // read what we just wrote
            } else {
                resp.sendRedirect("/posts/user");   // read what we just wrote
            }
        } catch (Exception e) {
            throw new ServletException("Error creating post tag", e);
        }
    }
}