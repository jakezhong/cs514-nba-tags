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

/**
 * List person by created By (user)
 * Filter to check user whether login
 */
public class ListPersonByUserFilter implements Filter {

  /**
   * implements Filter, implement interface
   * @param config FilterConfig
   * @throws ServletException
   */
  @Override
  public void init(FilterConfig config) throws ServletException {
  }

  /**
   *
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

    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
      chain.doFilter(servletReq, servletResp);
    } else {
      req.getSession().setAttribute("loginDestination", "/persons/user");
      resp.sendRedirect(userService.createLoginURL("/login"));
    }
  }

  /**
   * implement Filter interface abstract method
   */
  @Override
  public void destroy() {
  }
}
