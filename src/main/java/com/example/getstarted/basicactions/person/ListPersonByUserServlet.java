package com.example.getstarted.basicactions.person;

import com.example.getstarted.daos.interfaces.PersonDao;
import com.example.getstarted.daos.interfaces.ProfileDao;
import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Profile;
import com.example.getstarted.objects.Result;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// [START example]
@SuppressWarnings("serial")
/**
 * To List persons by user according to userId
 */
public class ListPersonByUserServlet extends HttpServlet {
    /**
    * list all persons created by specific user according to userID
    * display all persons by cursor( fetch 10 per time)
    * @param req HttpServletRequest
    * @param resp HttpServletResponse
    * @throws IOException
    * @throws ServletException
    */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        /*
        If there's user id parameter, list persons by this id
        Otherwise, list persons by logged in user id
        */

        String userId;
        try {
            userId = req.getParameter("id") == null || req.getParameter("id").isEmpty() ? (String) req.getSession().getAttribute("userId") : req.getParameter("id");
        } catch (Exception e) {
            userId = (String) req.getSession().getAttribute("userId");
        }

        /* First fetch the user profile information */
        ProfileDao daoProfile = (ProfileDao) this.getServletContext().getAttribute("dao-profile");
        List<Profile> profiles;
        Profile profile;

        try {
            Result<Profile> result = daoProfile.findProfile(userId);
            profiles = result.result;
            profile = profiles.get(0);
        } catch (Exception e) {
            profile = null;
        }

        /* Then fetch the user's persons */
        PersonDao daoPerson = (PersonDao) this.getServletContext().getAttribute("dao-person");
        String startCursor = req.getParameter("cursor");
        List<Person> persons;
        String endCursor;

        try {
            Result<Person> result = daoPerson.listPersonsByUser(userId, startCursor);
            persons = result.result;
            endCursor = result.cursor;
        } catch (Exception e) {
            throw new ServletException("Error listing persons", e);
        }

        req.setAttribute("profile", profile);
        req.getSession().getServletContext().setAttribute("persons", persons);
        req.getSession().setAttribute("cursor", endCursor);
        req.getSession().setAttribute("page", "list-user-persons");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);
    }
}
// [END example]
