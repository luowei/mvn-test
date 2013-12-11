package com.rootls.base.controller;

import com.rootls.base.view.command.LoginCommand;
import com.rootls.base.view.controller.LoginContrlloer;
import com.rootls.base.view.controller.manage.RoleController;
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
 * Time: 下午3:53
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:mvc-servlet.xml"})
public class RoleCtrTest  extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    LoginContrlloer loginContrlloer;

    @Autowired
    RoleController roleController;

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
        request.setRequestURI("/manage/role/assignPermissionsPre/2");
        request.setMethod(HttpMethod.POST.name());

        ModelAndView mav = handlerAdapter.handle(request, response,
                new HandlerMethod(roleController, "assignPermissionsForRolePre", Integer.class, Model.class));

        Map<String,Object> model = mav.getModel();

        assertNotNull(model.get("role"));
        assertNotNull(model.get("permissionJson"));
        assertEquals("/manage/role/assignPermission", mav.getViewName());
    }


}
