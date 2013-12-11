package com.rootls.base.controller;

import com.rootls.base.view.command.LoginCommand;
import com.rootls.base.view.controller.LoginContrlloer;
import com.rootls.base.view.controller.manage.RoleController;
import com.rootls.base.view.controller.manage.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-12-11
 * Time: 下午2:44
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:mvc-servlet.xml"})
public class UserCtrTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    LoginContrlloer loginContrlloer;

    @Autowired
    UserController userController;

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();


    @Before
    public void setUp() throws Exception {

        request.setRequestURI("/loginProcess");
        request.setMethod(HttpMethod.POST.name());
        request.setAttribute("username","luowei");
        request.setAttribute("password","luowei");

        handlerAdapter.handle(request, response,
                new HandlerMethod(loginContrlloer, "loginProcess", LoginCommand.class, BindingResult.class));

    }

    @Test
    public void testMain4User() throws Exception {
        request.setServletPath("/manage/user/assignRolesPre/1");
        request.setContextPath("/mvn-test");
        request.setRequestURI("/mvn-test/manage/user/assignRolesPre/1");
//        request.setMethod(HttpMethod.POST.name());

        ModelAndView mav = handlerAdapter.handle(request, response,
                new HandlerMethod(userController, "assignRolesForUserPre", Integer.class, Model.class));

        Map<String,Object> model = mav.getModel();

        assertNotNull(model.get("roleList1"));
        assertNotNull(model.get("roleList1"));
        assertNotNull(model.get("roleList1"));
        assertEquals("/manage/user/assignRoles", mav.getViewName());
    }

}
