package com.example.getstarted.auth;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// [START init]

/**
 * Logout
 */
public class LogoutFilter implements Filter {
  // [END init]

  /**
   * implement Filter interface abstract method
   * @param config FilterConfig
   * @throws ServletException
   */
  @Override
  public void init(FilterConfig config) throws ServletException {
  }

  /**
   *to check if user login before logout
   * @param servletReq servletRequest
   * @param servletResp servletResponse
   * @param chain FilterChain
   * @throws IOException
   * @throws ServletException
   */
  @Override
  public void doFilter(ServletRequest servletReq, ServletResponse servletResp, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) servletReq;
    HttpServletResponse resp = (HttpServletResponse) servletResp;
    String path = req.getRequestURI();

    chain.doFilter(servletReq, servletResp);

    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
      resp.sendRedirect(userService.createLogoutURL("/logout"));
    } else if (path.startsWith("/logout")) {
      resp.sendRedirect("/persons");
    }
  }

  /**
   * implement Filter interface abstract method
   */
  @Override
  public void destroy() {
  }
}
