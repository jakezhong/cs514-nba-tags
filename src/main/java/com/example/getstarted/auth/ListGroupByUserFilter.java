/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
 * List Group by created By (user)
 */
public class ListGroupByUserFilter implements Filter {

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
            req.getSession().setAttribute("loginDestination", "/groups/user");
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
