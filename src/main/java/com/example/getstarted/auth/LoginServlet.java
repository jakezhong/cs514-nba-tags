package com.example.getstarted.auth;

import com.example.getstarted.daos.UserDao;
import com.example.getstarted.objects.OurUser;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
/**
 * To to finish login and also get OurUser info
 * store in the session
 */
public class LoginServlet extends HttpServlet {
  /**
   *
   * @param req HttpServletRequest
   * @param resp HttpServletResponse
   * @throws IOException
   * @throws ServletException
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {

    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
        // Save the relevant profile info and store it in the session.
        User user = userService.getCurrentUser();
        req.getSession().setAttribute("userEmail", user.getEmail());
        req.getSession().setAttribute("userId", user.getUserId());
        req.getSession().setAttribute("userName", user.getNickname());

        String destination = (String) req.getSession().getAttribute("loginDestination");
        if (destination == null) {
          destination = "/";
        }

        UserDao daoUser = (UserDao) this.getServletContext().getAttribute("dao-user");
        try {
            OurUser currentUser = daoUser.findUser(user.getUserId());
            if (currentUser == null) {
                Date date = new Date();
                // [START userBuilder]
                OurUser ourUser = new OurUser.Builder()
                        .userId(user.getUserId())
                        .userName(user.getNickname())
                        .first(null)
                        .last(null)
                        .title(null)
                        .introduction(null)
                        .email(user.getEmail())
                        .phone(null)
                        .address(null)
                        .linkedin(null)
                        .facebook(null)
                        .twitter(null)
                        .instagram(null)
                        .youtube(null)
                        .website(null)
                        .description(null)
                        .createdDate(date)
                        .imageUrl(null)
                        .build();
                // [END userBuilder]
                try {
                    Long id = daoUser.createUser(ourUser);
                    currentUser = daoUser.readUser(id);
                } catch (Exception createE) {
                    throw new ServletException("Error creating user", createE);
                }
            }
            System.out.println(currentUser);
            this.getServletContext().setAttribute("login-user", currentUser);
        } catch(Exception e) {
            throw new ServletException("Error finding user", e);
        }
        resp.sendRedirect(destination);
    } else {
        resp.sendRedirect(userService.createLoginURL("/login"));
    }
  }
}