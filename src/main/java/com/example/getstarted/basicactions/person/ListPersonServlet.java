package com.example.getstarted.basicactions.person;

import com.example.getstarted.daos.*;
import com.example.getstarted.daos.interfaces.*;
import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Result;
import com.example.getstarted.util.CloudStorageHelper;
import com.google.common.base.Strings;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
/**
 * List all persons in Person4 kind
 */
public class ListPersonServlet extends HttpServlet {

  /**
   * init datastore to choose storage type
   * @throws ServletException
   */
  @Override
  public void init() throws ServletException {
    ProfileDao daoProfile = null;
    PersonDao daoPerson = null;
    GroupDao daoGroup = null;
    AssociationDao daoAssociation = null;
    PostDao daoPost = null;
    PostTagDao daoPostTag = null;
    SocialLinkDao daoSocial =null;
    CloudStorageHelper storageHelper = new CloudStorageHelper();

    // Creates the DAO based on the Context Parameters
    String storageType = this.getServletContext().getInitParameter("personshelf.storageType");
    switch (storageType) {
      case "datastore":
        daoProfile = new DatastoreProfileDao();
        daoPerson = new DatastorePersonDao();
        daoGroup = new DatastoreGroupDao();
        daoAssociation = new DatastoreAssociationDao();
        daoPost = new DatastorePostDao();
        daoPostTag = new DatastorePostTagDao();
        daoSocial = new DatastoreSocialDao();

        break;
      case "cloudsql":
        try {
          // Use this url when using dev appserver, but connecting to Cloud SQL
          String connect = this.getServletContext().getInitParameter("sql.urlRemote");
          if (connect.contains("localhost")) {
            // Use this url when using a local mysql server
            connect = this.getServletContext().getInitParameter("sql.urlLocal");
          } else if (System.getProperty("com.google.appengine.runtime.version")
              .startsWith("Google App Engine/")) {
            // Use this url when on App Engine, connecting to Cloud SQL.
            // Uses a special adapter because of the App Engine sandbox.
            connect = this.getServletContext().getInitParameter("sql.urlRemoteGAE");
          }
          daoPerson = new CloudSqlDao(connect);
          
        } catch (SQLException e) {
          throw new ServletException("SQL error", e);
        }
        break;
      default:
        throw new IllegalStateException(
            "Invalid storage type. Check if personshelf.storageType property is set.");
    }
    this.getServletContext().setAttribute("dao-profile", daoProfile);
    this.getServletContext().setAttribute("dao-person", daoPerson);
    this.getServletContext().setAttribute("dao-group", daoGroup);
    this.getServletContext().setAttribute("dao-association", daoAssociation);
    this.getServletContext().setAttribute("dao-post", daoPost);
    this.getServletContext().setAttribute("dao-postTag", daoPostTag);
    this.getServletContext().setAttribute("dao-social",daoSocial);
    this.getServletContext().setAttribute("storageHelper", storageHelper);
    this.getServletContext().setAttribute(
        "isCloudStorageConfigured",  // Hide upload when Cloud Storage is not configured.
        !Strings.isNullOrEmpty(getServletContext().getInitParameter("personshelf.bucket")));
  }

  /**
   *List all persons / display by using cursor (fetch 10 by time)
   * @param req HttpServletRequest
   * @param resp HttpServletResponse
   * @throws IOException
   * @throws ServletException
   */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
      ServletException {
    PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
    Hashtable<String, String> search = new Hashtable<String, String>();

    List<Person> persons;
    // Cursor variables
    String startCursor = req.getParameter("cursor");
    String endCursor;
    // Search variables
    String searchFirst = req.getParameter("first");
    String searchLast = req.getParameter("last");
    String searchCategory = req.getParameter("category");

    try {
        Result<Person> result;

        /* Check if there's search params, if so, list person by search, otherwise, list person by default */
        if (!(searchFirst == null && searchLast == null && searchCategory == null)) {
          if (searchFirst != null && !searchFirst.isEmpty()) {
              search.put("first", searchFirst);
          }
          if (searchLast != null && !searchLast.isEmpty()) {
              search.put("last", searchLast);
          }
          if (searchCategory != null && !searchCategory.isEmpty()) {
              search.put("category", searchCategory);
          }
          result = daoPerson.listPersonsBySearch(search, startCursor);
        } else {
          result = daoPerson.listPersons(startCursor);
        }
        persons = result.result;
        endCursor = result.cursor;
    } catch (Exception e) {
        throw new ServletException("Error listing persons", e);
    }

    req.getSession().getServletContext().setAttribute("persons", persons);
    req.setAttribute("first", searchFirst);
    req.setAttribute("last", searchLast);
    req.setAttribute("category", searchCategory);
    req.setAttribute("cursor", endCursor);
    req.setAttribute("page", "list-person");
    req.getRequestDispatcher("/base.jsp").forward(req, resp);
  }
}