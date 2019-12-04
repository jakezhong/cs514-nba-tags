package com.example.getstarted.basicactions.person;

import com.example.getstarted.daos.interfaces.*;
import com.example.getstarted.objects.*;

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
        PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");
        SocialLinkDao daoSocial = (SocialLinkDao) this.getServletContext().getAttribute("dao-social");
        AssociationDao associationDao = (AssociationDao) this.getServletContext().getAttribute("dao-association");
        PostTagDao daoPostTag = (PostTagDao) this.getServletContext().getAttribute("dao-postTag");

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

            /* Initial group list */
            List<Group> groups = new ArrayList<>();
            List<Long> groupsId;
            List<SocialLink> socialLinks;
            try {
                Result<Long> result = associationDao.listAllGroupByPerson(personId);
                groupsId = result.result;
                for(Long groupId: groupsId){
                    Group group = daoGroup.readGroup(groupId);
                    groups.add(group);
                }
            } catch (Exception e) {
                throw new ServletException("Error listing groups", e);
            }

            /* Initial post list */
            List<Post> posts = new ArrayList<>();
            List<Long> postIds;
            List<Post> visiblePosts = new ArrayList<Post>();

            try {
                Result<Long> result = daoPostTag.listAllPostByPerson(personId);
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
                    } catch (Exception e) {
                        throw new ServletException("Error listing tags", e);
                    }
                }
            } catch (Exception e) {
                throw new ServletException("Error listing posts", e);
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
            req.getSession().getServletContext().setAttribute("posts", visiblePosts);
            req.setAttribute("page", "view-person");
            req.getRequestDispatcher("/base.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error reading person", e);
        }
    }
}