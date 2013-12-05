package com.rootls.base.view.controller;

import com.rootls.base.view.command.LoginCommand;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * @className:LoginContrlloer
 * @classDescription:
 * @author:luowei
 * @createTime:12-5-15
 */
@Controller
public class LoginContrlloer {
    private static transient final Logger logger = LoggerFactory.getLogger(LoginContrlloer.class);


    @ModelAttribute
    public LoginCommand createLoginCommand(){
        return new LoginCommand();
    }


    /**
     * 显示登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void showLoginPage(){
        System.out.println("login controller");
    }


    /**
     * 处理登录
     * @param loginCommand
     * @param result
     * @return
     */
    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
    public String loginProcess(@Valid LoginCommand loginCommand, BindingResult result){
        if(result.hasErrors()){
            return "/login";
        }

        try {
            UsernamePasswordToken token = new UsernamePasswordToken(loginCommand.getUsername(), loginCommand.getPassword());
            token.setRememberMe(loginCommand.isRememberMe());
            System.out.println(loginCommand.isRememberMe());
            SecurityUtils.getSubject().login(token);

        } catch (AuthenticationException e) {
            logger.error("登录失败", e);
            return "redirect:/login?error=1";
        }


        return "redirect:/";
    }
}

