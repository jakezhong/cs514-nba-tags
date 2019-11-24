package com.example.getstarted.basicactions;

import com.example.getstarted.daos.GroupDao;
import com.example.getstarted.daos.PersonDao;
import com.example.getstarted.daos.PostDao;
import com.example.getstarted.daos.PostTagDao;
import com.example.getstarted.objects.Group;
import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Post;
import com.example.getstarted.objects.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// [START example]
@SuppressWarnings("serial")

/**
* To view all information of a specific post
*/
public class ReadPostServlet extends HttpServlet {

    /**
    * To view all information of a specific post
    * @param req HttpServletRequest
    * @param resp HttpServletResponse
    * @throws IOException
    * @throws ServletException
    */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");
        PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
        GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");
        PostTagDao daoPostTag = (PostTagDao) this.getServletContext().getAttribute("dao-postTag");

        Long postId = Long.decode(req.getParameter("id"));

        try {
            Post post = daoPost.readPost(postId);

            String startCursor = req.getParameter("cursor");
            List<Long> personIds;
            List<Person> persons = new ArrayList<>();
            String endCursor;

            try {
                Result<Long> result = daoPostTag.listPersonByPost(postId, startCursor);
                personIds = result.result;
                for(Long personId: personIds){
                    if (personId != null) {
                        Person person = daoPerson.readPerson(personId);
                        persons.add(person);
                    }
                }
                endCursor = result.cursor;
            } catch (Exception e) {
                throw new ServletException("Error listing persons", e);
            }

            List<Long> groupIds;
            List<Group> groups = new ArrayList<>();

            try {
                Result<Long> result = daoPostTag.listGroupByPost(postId, startCursor);
                groupIds = result.result;
                for(Long groupId: groupIds) {
                    if (groupId != null) {
                        Group group = daoGroup.readGroup(groupId);
                        groups.add(group);
                    }
                }
            } catch (Exception e) {
                throw new ServletException("Error listing groups", e);
            }

            req.setAttribute("post", post);
            req.getSession().getServletContext().setAttribute("persons", persons);
            req.getSession().getServletContext().setAttribute("groups", groups);
            req.setAttribute("cursor", endCursor);
            req.setAttribute("page", "view-post");
            req.getRequestDispatcher("/base.jsp").forward(req, resp);
        } catch (Exception e) {
                throw new ServletException("Error reading post", e);
        }
    }
}
// [END example]
