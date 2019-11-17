package com.example.getstarted.basicactions;

import com.example.getstarted.daos.PersonDao;
import com.example.getstarted.objects.Person;
import com.example.getstarted.util.CloudStorageHelper;
import com.google.common.base.Strings;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

// [START example]
@SuppressWarnings("serial")
/**
 * To update person info
 */
public class UpdatePersonServlet extends HttpServlet {
  /**
   * To redirect to page form jsp
   * @param req HttpServletRequest
   * @param resp HttpServletResponse
   * @throws ServletException
   * @throws IOException
   */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    PersonDao dao = (PersonDao) this.getServletContext().getAttribute("dao");
    try {
      Person person = dao.readPerson(Long.decode(req.getParameter("id")));
      req.setAttribute("person", person);
      req.setAttribute("action", "Edit");
      req.setAttribute("destination", "person/update");
      req.setAttribute("page", "form-person");
      req.getRequestDispatcher("/base.jsp").forward(req, resp);
    } catch (Exception e) {
      throw new ServletException("Error loading person for editing", e);
    }
  }

  /**
   * According to updated info to create person object and store it datastore
   * @param req HttpServletRequest
   * @param resp HttpServletResponse
   * @throws ServletException
   * @throws IOException
   */

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");

    assert ServletFileUpload.isMultipartContent(req);
    CloudStorageHelper storageHelper =
        (CloudStorageHelper) getServletContext().getAttribute("storageHelper");

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
      Person oldPerson = daoPerson.readPerson(Long.decode(params.get("id")));

      // [START personBuilder]
      Person person = new Person.Builder()
          .id(Long.decode(params.get("id")))
          .first(params.get("first"))
          .last(params.get("last"))
          .title(params.get("title"))
          .introduction(params.get("introduction"))
          .email(params.get("email"))
          .phone(params.get("phone"))
          .address(params.get("address"))
          .category(params.get("category"))
          .type(params.get("type"))
          .linkedin(params.get("linkedin"))
          .facebook(params.get("facebook"))
          .twitter(params.get("twitter"))
          .instagram(params.get("instagram"))
          .youtube(params.get("youtube"))
          .website(params.get("website"))
          .description(params.get("description"))
          .imageUrl(null == newImageUrl ? params.get("imageUrl") : newImageUrl)
          .createdBy(oldPerson.getCreatedBy())
          .createdById(oldPerson.getCreatedById())
          .publishedDate(oldPerson.getPublishedDate())
          .build();
      // [END personBuilder]

      daoPerson.updatePerson(person);
      resp.sendRedirect("person/read?id=" + params.get("id"));
    } catch (Exception e) {
      throw new ServletException("Error updating person", e);
    }
  }
}
// [END example]
