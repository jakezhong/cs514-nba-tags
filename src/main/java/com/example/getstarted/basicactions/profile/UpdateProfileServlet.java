package com.example.getstarted.basicactions.profile;

import com.example.getstarted.daos.interfaces.ProfileDao;
import com.example.getstarted.objects.Profile;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
/**
 * To update profile info
 */
public class UpdateProfileServlet extends HttpServlet {
  /**
   * To redirect to page form jsp
   * @param req HttpServletRequest
   * @param resp HttpServletResponse
   * @throws ServletException
   * @throws IOException
   */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ProfileDao daoProfile = (ProfileDao) this.getServletContext().getAttribute("dao-profile");
    try {
        Profile profile = daoProfile.readProfile(Long.decode(req.getParameter("id")));
        req.setAttribute("profile", profile);
        req.setAttribute("action", "Edit");
        req.setAttribute("destination", "update");
        req.setAttribute("page", "form-profile");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    } catch (Exception e) {
        throw new ServletException("Error loading profile for editing", e);
    }
  }

  /**
   * According to updated info to create profile object and store it datastore
   * @param req HttpServletRequest
   * @param resp HttpServletResponse
   * @throws ServletException
   * @throws IOException
   */

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      ProfileDao daoProfile = (ProfileDao) this.getServletContext().getAttribute("dao-profile");

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

      try {
          Profile oldProfile = daoProfile.readProfile(Long.decode(params.get("id")));

          // [START profileBuilder]
          Profile profile = new Profile.Builder()
              .id(Long.decode(params.get("id")))
              .first(params.get("first"))
              .last(params.get("last"))
              .title(params.get("title"))
              .introduction(params.get("introduction"))
              .email(params.get("email"))
              .phone(params.get("phone"))
              .address(params.get("address"))
              .linkedin(params.get("linkedin"))
              .facebook(params.get("facebook"))
              .twitter(params.get("twitter"))
              .instagram(params.get("instagram"))
              .youtube(params.get("youtube"))
              .website(params.get("website"))
              .description(params.get("description"))
              .imageUrl(null == newImageUrl ? params.get("imageUrl") : newImageUrl)
              .createdBy(oldProfile.getCreatedBy())
              .createdById(oldProfile.getCreatedById())
              .publishedDate(oldProfile.getPublishedDate())
              .build();
          // [END profileBuilder]

          daoProfile.updateProfile(profile);
          resp.sendRedirect("/profile/user?id=" + oldProfile.getCreatedById());
      } catch (Exception e) {
          throw new ServletException("Error updating profile", e);
      }
  }
}