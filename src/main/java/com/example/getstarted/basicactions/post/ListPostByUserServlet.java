package com.example.getstarted.basicactions.post;

import com.example.getstarted.daos.interfaces.*;
import com.example.getstarted.objects.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// [START example]
@SuppressWarnings("serial")
/**
 * To List posts by user according to userId
 */
public class ListPostByUserServlet extends HttpServlet {

    /**
    * list all posts created by specific user according to userID
    * display all posts by cursor( fetch 10 per time)
    * @param req HttpServletRequest
    * @param resp HttpServletResponse
    * @throws IOException
    * @throws ServletException
    */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        /*
            If there's user id parameter, list group by this id
            Otherwise, list group by logged in user id
        */
        String userId;
        try {
            userId = req.getParameter("id") == null || req.getParameter("id").isEmpty() ? (String) req.getSession().getAttribute("userId") : req.getParameter("id");
        } catch (Exception e) {
            userId = (String) req.getSession().getAttribute("userId");
        }

        /* First fetch the user profile information */
        ProfileDao daoProfile = (ProfileDao) this.getServletContext().getAttribute("dao-profile");
        PostTagDao daoPostTag = (PostTagDao) this.getServletContext().getAttribute("dao-postTag");
        PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
        GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");

        /* Initial post list */
        List<Profile> profiles;
        Profile profile;

        try {
            Result<Profile> result = daoProfile.findProfile(userId);
            profiles = result.result;
            profile = profiles.get(0);
        } catch (Exception e) {
            profile = null;
        }

        /* Ask user to login if there's no current profile */
        if (profile == null && req.getSession().getAttribute("userId") == null) {
            resp.sendRedirect("/login");
            return;
        }
        /* Don't show profile to others if it's private */
        if (profile != null && profile.getStatus() != null) {
            if (profile.getStatus().equals("private") && !profile.getCreatedById().equals(req.getSession().getAttribute("userId"))) {
                req.setAttribute("privacy", true);
            }
        }

        PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");
        String startCursor = req.getParameter("cursor");
        List<Post> posts;
        String endCursor;
        try {
            Result<Post> result = daoPost.listPostsByUser(userId, startCursor);
            posts = result.result;
            for (Post post: posts) {
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
                                Person person = daoPerson.readPerson(tag.getPersonId());
                                tags.add(person);
                            } catch (Exception e) {
                                throw new ServletException("Error read person", e);
                            }
                        } else if (tag.getGroupId() != null) {
                            try {
                                Group group = daoGroup.readGroup(tag.getGroupId());
                                tags.add(group);
                            } catch (Exception e) {
                                throw new ServletException("Error read group", e);
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
            endCursor = result.cursor;
        } catch (Exception e) {
            throw new ServletException("Error listing posts", e);
        }

        req.setAttribute("profile", profile);
        req.getSession().getServletContext().setAttribute("posts", posts);
        req.getSession().setAttribute("cursor", endCursor);
        req.getSession().setAttribute("page", "list-user-posts");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
}
