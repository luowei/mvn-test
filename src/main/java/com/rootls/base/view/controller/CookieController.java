package com.rootls.base.view.controller;

import com.rootls.base.util.CookieUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * User: luowei
 * Date: 12-4-30
 * Time: 下午4:07
 */
@Controller
public class CookieController {


    @RequestMapping(value = "/cookie", produces = "text/plain;charset=utf-8")
    public @ResponseBody
    String extractCookie(@CookieValue("forwardUrl") String cookieValue) {

        System.out.println("cookie=" + cookieValue);

        return cookieValue;
    }


    @RequestMapping(value = "/cookie2", produces = "text/plain;charset=utf-8")
    public @ResponseBody
    String extractCookie2(HttpServletRequest request) {

        String cookieValue = CookieUtils.findCookie(request, "forwardUrl");

        System.out.println("cookie2=" + cookieValue);


        return cookieValue;
    }
}
