package com.rootls.base.view;

import com.rootls.base.util.CookieUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: luowei
 * Date: 12-4-30
 * Time: 下午3:45
 */
public class TestServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CookieUtils.addCookie(resp, "forwardUrl", "?page=3&sno=210708055&name=张三");
    }
}
