package com.example.getstarted.basicactions.post;

import com.example.getstarted.daos.interfaces.GroupDao;
import com.example.getstarted.daos.interfaces.PersonDao;
import com.example.getstarted.daos.interfaces.PostDao;
import com.example.getstarted.daos.interfaces.PostTagDao;
import com.example.getstarted.objects.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@SuppressWarnings("serial")
/**
 * To list all posts
 */
public class ListPostServlet extends HttpServlet {

    /**
     *List all posts / display by using cursor (fetch 10 by time)
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PostDao daoPost = (PostDao) this.getServletContext().getAttribute("dao-post");
        PostTagDao daoPostTag = (PostTagDao) this.getServletContext().getAttribute("dao-postTag");
        PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
        GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");
        Hashtable<String, String> search = new Hashtable<String, String>();

        /* Initial post list */
        List<Post> posts;
        List<Post> visiblePosts = new ArrayList<Post>();
        // Cursor variables
        String startCursor = req.getParameter("cursor");
        String endCursor;
        // Search variables
        String searchTitle = req.getParameter("title");
        String searchCategory = req.getParameter("category");

        /* Check if there's search params, if so, list post by search, otherwise, list post by default */
        try {
            /* Filter for search */
            Result<Post> result;
            if (!(searchTitle == null && searchCategory == null)) {
                if (searchTitle != null && !searchTitle.isEmpty()) {
                    search.put("title", searchTitle);
                }
                if (searchCategory != null && !searchCategory.isEmpty()) {
                    search.put("category", searchCategory);
                }
                result = daoPost.listPostsBySearch(search, startCursor);
            } else {
                result = daoPost.listPosts(startCursor);
            }
            posts = result.result;

            /* Check if the post is public */
            for (Post post: posts) {
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
            endCursor = result.cursor;
        } catch (Exception e) {
            throw new ServletException("Error listing posts", e);
        }
        req.getSession().getServletContext().setAttribute("posts", visiblePosts);
        req.setAttribute("title", searchTitle);
        req.setAttribute("category", searchCategory);
        req.setAttribute("cursor", endCursor);
        req.setAttribute("page", "list-post");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
}