package com.example.getstarted.basicactions.profile;

import com.example.getstarted.daos.interfaces.ProfileDao;
import com.example.getstarted.objects.Profile;
import com.example.getstarted.objects.Result;
import com.example.getstarted.util.CloudStorageHelper;
import com.google.common.base.Strings;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
/**
 * Create profile object
 */
public class CreateProfileServlet extends HttpServlet {

    // [START setup]

    /**
     * When request add Profile, redirect the page to form-profile JSP
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* If the user has not logged in, don't allow to create group and redirect */
        if (req.getSession().getAttribute("userId") == null) {
            resp.sendRedirect("/login");
            return;
        }

        String userId = (String) req.getSession().getAttribute("userId");

        ProfileDao daoProfile = (ProfileDao) this.getServletContext().getAttribute("dao-profile");
        List<Profile> profile;
        try {
            Result<Profile> result = daoProfile.findProfile(userId);
            profile = result.result;
            if (profile.size() == 0) {
                req.setAttribute("action", "Add");          // Part of the Header in form-person.jsp
                req.setAttribute("destination", "create");  // The urlPattern to invoke (this Servlet)
                req.setAttribute("page", "form-profile");           // Tells base.jsp to include form-person.jsp
                req.getRequestDispatcher("/base.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("/profile/user");
            }
        } catch (Exception e) {
            throw new ServletException("Error displaying profile", e);
        }
    }
    // [END setup]

    // [START formpost]
    /**
     * Create  an Profile Object and store in Profile4 Kind, store image to bucket
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        assert ServletFileUpload.isMultipartContent(req);
        CloudStorageHelper storageHelper = (CloudStorageHelper) getServletContext().getAttribute("storageHelper");

        String newImageUrl = null;
        Map<String, String> params = new HashMap<String, String>();
        try {
            FileItemIterator iter = new ServletFileUpload().getItemIterator(req);
            while (iter.hasNext()) {
                FileItemStream item = iter.next();
                if (item.isFormField()) {
                    params.put(item.getFieldName(), Streams.asString(item.openStream()));
                } else if (!Strings.isNullOrEmpty(item.getName())) {
                    newImageUrl = storageHelper.uploadFile(
                            item, getServletContext().getInitParameter("personshelf.bucket"));
                }
            }
        } catch (FileUploadException e) {
            throw new IOException(e);
        }

        // [START createdBy]
        String createdByString = "";
        String createdByIdString = "";
        Date date = new Date();
        HttpSession session = req.getSession();

        if (session.getAttribute("userEmail") != null) { // Does the user have a logged in session?
            createdByString = (String) session.getAttribute("userEmail");
            createdByIdString = (String) session.getAttribute("userId");
        }
        // [END createdBy]

        // [START personBuilder]
        Profile profile = new Profile.Builder()
            .first(params.get("first"))
            .last(params.get("last"))
            .title(params.get("title"))
            .introduction(params.get("introduction"))
            .status(params.get("status"))
            .email(createdByString)
            .description(params.get("description"))
            .imageUrl(null == newImageUrl ? params.get("imageUrl") : newImageUrl)
            // [START auth]
            .createdBy(createdByString)
            .createdById(createdByIdString)
            .publishedDate(date)
            // [END auth]
            .build();
        // [END profileBuilder]

        ProfileDao daoProfile = (ProfileDao) this.getServletContext().getAttribute("dao-profile");
        try {
            Long id = daoProfile.createProfile(profile);
            profile = daoProfile.readProfile(id);
            resp.sendRedirect("/profile/user?id=" + profile.getCreatedById());   // read what we just wrote
        } catch (Exception e) {
            throw new ServletException("Error creating profile", e);
        }
    }
    // [END formpost]
}