package com.rootls.base.view.controller;

import com.rootls.base.bean.Constants;
import com.rootls.base.bean.DataTable;
import com.rootls.base.model.IdEntity;
import com.rootls.base.util.CookieUtils;
import com.rootls.base.util.UrlBuilder;
import com.rootls.base.view.command.BaseCommand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @className:BaseController
 * @classDescription:
 * @author:luowei
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

    /**
     * 添加页面信息
     * @param model
     * @param request
     * @param response
     * @param orders
     * @param page
     * @param pageRequest
     * @param resultPage
     * @param searchConditionList
     * @param thisUrl
     */
    protected <E extends IdEntity> void  addPageInfo(Model model, HttpServletRequest request, HttpServletResponse response, String orders,int page,
                            PageRequest pageRequest, Page<E> resultPage, List<UrlBuilder.PropertyFilter> searchConditionList,String thisUrl) {

        String conditionUrl = UrlBuilder.getUrl(thisUrl, searchConditionList);
        String conditionAndOrdersUrl = UrlBuilder.getOrdersUrl(conditionUrl, orders);

        DataTable<E> dataTable = new DataTable<E>();
        dataTable.setContent(resultPage.getContent());
        dataTable.setCurrentPage(resultPage.getNumber());
        dataTable.setStartIndex(pageRequest.getOffset());
        dataTable.setPageSize(pageRequest.getPageSize());
        dataTable.setConditionUrl(request.getContextPath() + conditionAndOrdersUrl);

        model.addAttribute("dataTable", dataTable);
        model.addAttribute("order", orders);
        model.addAttribute("index", pageRequest.getOffset());


        //添加url到cookie
        addRediectUrlCookie(request, response, page, conditionUrl);
    }

    /**
     * 获得排序
     * @param orders
     * @return
     */
    protected Sort getSort(String orders) {
        if (orders == null || orders == "") {
            return null;
        }
        Sort sort = null;
        if (orders.indexOf(":") > 0) {

            List<Sort.Order> orderList = new ArrayList<Sort.Order>();
            for (String order : orders.split(",")) {
                String[] subOrder = order.split(";");
                Sort.Direction direction = Sort.Direction.valueOf(subOrder[0].trim());
                String properties = subOrder[1].trim();

                orderList.add(new Sort.Order(direction, properties));
            }
            sort = new Sort(orderList);
        } else {
            sort = new Sort(orders.split(","));
        }
        return sort;
    }
}

