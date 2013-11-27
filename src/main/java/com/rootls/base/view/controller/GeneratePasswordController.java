package com.rootls.base.view.controller;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @className:GeneratePasswordController
 * @classDescription:
 * @author:Administrator
 * @createTime:12-5-17
 */
@Controller
public class GeneratePasswordController {

    @Autowired
    private PasswordService passwordService;

    @RequestMapping(value = "/generatePassword", produces = "text/plain;charset=utf-8")
    public
    @ResponseBody
    String getSaltedPassword(@RequestParam String rawPassword) {

        String encodedPassword = passwordService.encryptPassword(rawPassword);

        return encodedPassword;
    }
}

