package com.example.getstarted.basicactions.post;

import com.example.getstarted.daos.interfaces.GroupDao;
import com.example.getstarted.daos.interfaces.PersonDao;
import com.example.getstarted.daos.interfaces.PostDao;
import com.example.getstarted.daos.interfaces.PostTagDao;
import com.example.getstarted.objects.Post;
import com.example.getstarted.objects.PostTag;
import com.example.getstarted.objects.Result;

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
        PostTagDao daoPostTag = (PostTagDao) this.getServletContext().getAttribute("dao-post-tag");
        PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
        GroupDao daoGroup = (GroupDao) this.getServletContext().getAttribute("dao-group");
        Hashtable<String, String> search = new Hashtable<String, String>();

        String startCursor = req.getParameter("cursor");
        String searchKey = req.getParameter("search");
        String searchCategory = req.getParameter("category");
        String searchPerson = req.getParameter("person");
        String searchGroup = req.getParameter("group");

        List<Post> posts;
        List<PostTag> allTags;
        List<Object> tags = new ArrayList<Object>();

        String endCursor;
        try {
            Result<Post> result = null;
            if (searchKey != null || searchCategory != null || searchPerson != null || searchGroup != null) {
                if (searchKey != null) {
                    search.put("title", searchKey);
                }
                if (searchCategory != null) {
                    search.put("category", searchCategory);
                }
                result = daoPost.listPostsBySearch(search, startCursor);
            } else {
                result = daoPost.listPosts(startCursor);
            }
            posts = result.result;
//            for (Post post: posts) {
//                Result<PostTag> resultTags = daoPostTag.listAllTagsByPost(post.getId());
//                allTags = resultTags.result;
//
//                for (PostTag tag: allTags) {
//                    if (daoPerson.readPerson(tag.getPersonId()) != null) {
//                        tags.add(daoPerson.readPerson(tag.getPersonId()));
//                    } else if (daoGroup.readGroup(tag.getGroupId()) != null) {
//                        tags.add(daoGroup.readGroup(tag.getGroupId()));
//                    }
//                }
//
//                post.setPostTags(tags);
//            }
            endCursor = result.cursor;
        } catch (Exception e) {
            throw new ServletException("Error listing posts", e);
        }
        req.getSession().getServletContext().setAttribute("posts", posts);
        req.setAttribute("cursor", endCursor);
        req.setAttribute("page", "list-post");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
}