package com.example.getstarted.basicactions.group;

import com.example.getstarted.daos.DatastoreAssociationDao;
import com.example.getstarted.daos.interfaces.*;
import com.example.getstarted.objects.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
/**
 * To view all information of a specific group
 */
public class ReadGroupServlet extends HttpServlet {

    /**
     * To view all information of a specific group according by groupId
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");
        PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
        PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");
        AssociationDao daoAssociation = (AssociationDao) this.getServletContext().getAttribute("dao-association");
        PostTagDao daoPostTag = (PostTagDao) this.getServletContext().getAttribute("dao-postTag");

        Long groupId = Long.decode(req.getParameter("id"));
        /* Initial cursor */
        String cursor_person = req.getParameter("cursor_person");
        String cursor_post = req.getParameter("cursor_post");
        String endCursor_person;
        String endCursor_post;
        boolean login = false;

        try {
            Group group = daoGroup.readGroup(groupId);
            /* If the current person if private and the user is not the author, redirect user */
            if (group.getStatus() != null) {
                if (group.getStatus().equals("private") && !group.getCreatedById().equals(req.getSession().getAttribute("userId"))) {
                    resp.sendRedirect("/groups/user?id="+group.getCreatedById());
                    return;
                }
            }
            if (group.getCreatedById().equals(req.getSession().getAttribute("userId"))) {
                login = true;
            }
            /* Initial person list */
            List<Person> persons = new ArrayList<>();
            List<Long> personIds;
            try {
                /* List all person by the current group */
                Result<Long> result = daoAssociation.listPersonsByGroup(groupId, cursor_person);
                personIds = result.result;
                /* Read all persons by the person ids */
                for(Long personId: personIds){
                    Person person = daoPerson.readPerson(personId);
                    persons.add(person);
                }
                endCursor_person = result.cursor;
            } catch (Exception e) {
                throw new ServletException("Error listing persons", e);
            }

            /* Initial post list */
            List<Post> posts = new ArrayList<>();
            List<Long> postIds;
            List<Post> visiblePosts = new ArrayList<Post>();

            try {
                Result<Long> result = daoPostTag.listPostByGroup(groupId, cursor_post);
                postIds = result.result;
                /* Loop posts tagged this person */
                for(Long postId: postIds){
                    try {
                        Post post = daoPost.readPost(postId);
                        posts.add(post);
                    } catch (Exception e) {
                        throw new ServletException("Error reading post", e);
                    }
                }
                /* Check if the post is public */
                for (Post post: posts) {
                    if (post != null) {
                        /* Check if post is public, only show own post if it's not */
                        if (post.getStatus() != null) {
                            if (post.getStatus().equals("public")) {
                                visiblePosts.add(post);
                            } else if (post.getCreatedById().equals(req.getSession().getAttribute("userId"))) {
                                visiblePosts.add(post);
                            }
                        } else {
                            visiblePosts.add(post);
                        }
                    }
                }
                /* Loop tags from posts */
                for (Post post: visiblePosts) {
                    // Post tag variables
                    List<PostTag> allTags;
                    List<Object> tags = new ArrayList<Object>();
                    /* Save all tags from the current post to a list */
                    try {
                        Result<PostTag> resultTags = daoPostTag.listAllTagsByPost(post.getId());
                        allTags = resultTags.result;
                        /* Loop through tags and store persons/groups */
                        for (PostTag tag: allTags) {
                            if (tag.getPersonId() != null) {
                                try {
                                    Person personTag = daoPerson.readPerson(tag.getPersonId());
                                    tags.add(personTag);
                                } catch (Exception e) {
                                    throw new ServletException("Error read person tag", e);
                                }
                            } else if (tag.getGroupId() != null) {
                                try {
                                    Group groupTag = daoGroup.readGroup(tag.getGroupId());
                                    tags.add(groupTag);
                                } catch (Exception e) {
                                    throw new ServletException("Error read group tag", e);
                                }
                            }
                        }
                        post.setPostTags(tags);
                        try {
                            Map<String, String> comments = daoPost.listComment(post.getId());
                            if (comments != null) {
                                post.setCommentNum(comments.size());
                            } else {
                                post.setCommentNum(0);
                            }
                        } catch (Exception e) {
                            throw new ServletException("Error listing comments", e);
                        }
                    } catch (Exception e) {
                        throw new ServletException("Error listing tags", e);
                    }
                }
                endCursor_post = result.cursor;
            } catch (Exception e) {
                throw new ServletException("Error listing posts", e);
            }
            req.setAttribute("group", group);
            req.setAttribute("login", login);
            req.getSession().getServletContext().setAttribute("persons", persons);
            req.getSession().getServletContext().setAttribute("posts", visiblePosts);
            req.setAttribute("cursor_group", endCursor_person);
            req.setAttribute("cursor_post", endCursor_post);
            req.setAttribute("page", "view-group");
            req.getRequestDispatcher("/base.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error reading group", e);
        }
    }
}