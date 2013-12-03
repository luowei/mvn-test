package com.rootls.base.view.inteceptor;

import com.rootls.base.model.User;
import com.rootls.base.service.UserService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @className:CurrentUserInterceptor
 * @classDescription: A Spring MVC interceptor that adds the currentUser into the request as a request attribute
 * before the JSP is rendered.  This operation is assumed to be fast because the User should be
 * cached in the Hibernate second-level cache.
 * @author:luowei
 * @createTime:12-5-16
 */
public class CurrentUserInterceptor extends HandlerInterceptorAdapter {
    private UserService userService;


    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User currentUser = userService.getCurrentUser();

        if(currentUser != null){
            request.setAttribute("currentUser", currentUser);
        }
    }
}

