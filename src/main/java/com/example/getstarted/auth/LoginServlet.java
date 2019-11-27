package com.example.getstarted.auth;

import com.example.getstarted.daos.ProfileDao;
import com.example.getstarted.objects.Profile;
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
 * To to finish login and also get Profile info
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
        resp.sendRedirect(destination);
    } else {
        resp.sendRedirect(userService.createLoginURL("/login"));
    }
  }
}