package com.example.getstarted.basicactions.person;

import com.example.getstarted.daos.interfaces.PersonDao;
import com.example.getstarted.objects.Person;
import com.example.getstarted.util.CloudStorageHelper;
import com.google.common.base.Strings;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

@SuppressWarnings("serial")
/**
 * Create Person Object and store, display it
 */
public class CreatePersonServlet extends HttpServlet {

  // [START setup]

  /**
   * When adding Person, redirect page to form JSP
   * @param req HttpServletRequest
   * @param resp HttpServletResponse
   * @throws ServletException
   * @throws IOException
   */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    req.setAttribute("action", "Add");          // Part of the Header in form-person.jsp
    req.setAttribute("destination", "create");  // The urlPattern to invoke (this Servlet)
    req.setAttribute("page", "form-person");           // Tells base.jsp to include form-person.jsp
    req.getRequestDispatcher("/base.jsp").forward(req, resp);
  }
  // [END setup]

  // [START formpost]
  /**
   * Create person object and store in Person4 kind
   * Check whether if created by specific user
   * @param req HttpServletRequest
   * @param resp HttpServletResponse
   * @throws ServletException
   * @throws IOException
   */
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
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

    // [START createdBy]
    String createdByString = "";
    String createdByIdString = "";
    HttpSession session = req.getSession();
    if (session.getAttribute("userEmail") != null) { // Does the user have a logged in session?
        createdByString = (String) session.getAttribute("userEmail");
        createdByIdString = (String) session.getAttribute("userId");
    }
    Date date = new Date();
    // [END createdBy]

    // [START personBuilder]
    Person person = new Person.Builder()
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
        // [START auth]
        .createdBy(createdByString)
        .createdById(createdByIdString)
        .publishedDate(date)
        // [END auth]
        .build();
        System.out.println(person);
    // [END personBuilder]

    PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
    try {
        Long id = daoPerson.createPerson(person);
        resp.sendRedirect("/person/read?id=" + id.toString());   // read what we just wrote
    } catch (Exception e) {
        throw new ServletException("Error creating person", e);
    }
  }
  // [END formpost]
}
