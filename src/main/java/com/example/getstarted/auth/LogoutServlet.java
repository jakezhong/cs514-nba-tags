package com.example.getstarted.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
/**
 * To logout and invalidate the user info
 * and also rebuild session
 */
public class LogoutServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    // you can also make an authenticated request to logout, but here we choose to
    // simply delete the session variables for simplicity
    HttpSession session =  req.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    this.getServletContext().setAttribute("login-user", null);
    // rebuild session
    req.getSession();
  }
}
