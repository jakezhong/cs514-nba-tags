package com.example.getstarted.basicactions.post;

import com.example.getstarted.daos.interfaces.GroupDao;
import com.example.getstarted.daos.interfaces.PersonDao;
import com.example.getstarted.daos.interfaces.PostDao;
import com.example.getstarted.daos.interfaces.PostTagDao;
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

            /* If the current post if private and the user is not the author, redirect user */
            if (post.getStatus() != null) {
                if (post.getStatus().equals("private") && !post.getCreatedById().equals(req.getSession().getAttribute("userId"))) {
                    resp.sendRedirect("/posts/user?id="+post.getCreatedById());
                    return;
                }
            }

            /* Initial person list */
            List<Long> personIds;
            List<Person> persons = new ArrayList<>();
            try {
                /* List the tagged persons */
                Result<Long> result = daoPostTag.listAllPersonByPost(postId);
                personIds = result.result;
                for(Long personId: personIds){
                    if (personId != null) {
                        Person person = daoPerson.readPerson(personId);
                        persons.add(person);
                    }
                }
            } catch (Exception e) {
                throw new ServletException("Error listing persons", e);
            }

            /* Initial group list */
            List<Long> groupIds;
            List<Group> groups = new ArrayList<>();
            try {
                /* List the tagged groups */
                Result<Long> result = daoPostTag.listAllGroupByPost(postId);
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
            req.setAttribute("page", "view-post");
            req.getRequestDispatcher("/base.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error reading post", e);
        }
    }
}