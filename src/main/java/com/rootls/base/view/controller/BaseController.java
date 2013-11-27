package com.rootls.base.view.controller;

import com.rootls.base.bean.Constants;
import com.rootls.base.util.CookieUtils;
import com.rootls.base.util.UrlBuilder;
import com.rootls.base.view.command.BaseCommand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @className:BaseController
 * @classDescription:
 * @author:Administrator
 * @createTime:12-4-19
 */
public class BaseController {



    @ModelAttribute
    public BaseCommand createBaseCommand(){
        return new BaseCommand();
    }

    /**
     * 获取page页码
     * @param pageStr
     * @return
     */
    protected int getPageNoFromString(String pageStr) {
        int page = 1;

        if (StringUtils.isNumeric(pageStr)) {
            page = Integer.parseInt(pageStr);
        }

        return page;
    }


    /**
     * 重定向时添加消息
     * @param redirectAttrs
     * @param messageText
     */
    protected void addRedirectMessage(RedirectAttributes redirectAttrs, String messageText) {
        redirectAttrs.addFlashAttribute("message", messageText);
    }


    /**
     * 重定向时添加错误消息
     * @param redirectAttrs
     * @param messageText
     */
    protected void addRedirectError(RedirectAttributes redirectAttrs, String messageText) {
        redirectAttrs.addFlashAttribute("error", messageText);
    }


    /**
     *  获得重定向url
     * @param redirectUrl
     * @return
     */
    protected String getRedirectUrl(String redirectUrl){
        String path = "/";
        if(redirectUrl != null){
            path = UriComponentsBuilder.fromUriString(redirectUrl).build().encode().toUriString();
        }
        return "redirect:"+path;
    }


    /**
     * 添加条件到cookie中
     * @param request
     * @param response
     * @param page
     * @param conditionUrl
     */
    protected void addRediectUrlCookie(HttpServletRequest request, HttpServletResponse response, int page, String conditionUrl) {
        String redirectUrl = UrlBuilder.getUrl(conditionUrl, Constants.PAGE, page);

        String key = Constants.REDIRECT_URL;
        if(CookieUtils.findCookie(request, key) != null){
            CookieUtils.deleteCookie(response, key);
        }
        CookieUtils.addCookie(response, key, redirectUrl);
    }


    /**
     * 添加成功消息
     * @param redirectAttrs
     */
    protected void addSaveSuccessMessage(RedirectAttributes redirectAttrs){
        addRedirectMessage(redirectAttrs, "添加成功");
    }

    /**
     * 更新成功消息
     * @param redirectAttrs
     */
    protected void addUpdateSuccessMessage(RedirectAttributes redirectAttrs){
        addRedirectMessage(redirectAttrs, "更新成功");
    }


    /**
     * 删除成功消息
     * @param redirectAttrs
     */
    protected void addDeleteSuccessMessage(RedirectAttributes redirectAttrs){
        addRedirectMessage(redirectAttrs, "删除成功");
    }
}

