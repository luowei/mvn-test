package com.rootls.base.view.controller.manage;

import com.rootls.base.bean.Constants;
import com.rootls.base.model.Permission;
import com.rootls.base.service.PermissionService;
import com.rootls.base.util.UrlBuilder;
import com.rootls.base.view.command.BaseCommand;
import com.rootls.base.view.controller.BaseController;
import com.rootls.base.view.groups.BatchDeleteGroup;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @className:PermissionsController
 * @classDescription:
 * @author:luowei
 * @createTime:12-6-7
 */
@Controller
@RequestMapping("/manage/permissions")
public class PermissionsController extends BaseController {

    @Autowired
    private PermissionService permissionService;


    /**
     * 分页列出所有权限
     * @param baseCommand
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(BaseCommand baseCommand,
                       Model model,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        String pageStr = baseCommand.getPage();
        String permission = baseCommand.getSearchKey1();
        String description = baseCommand.getSearchKey2();
        Date startTime = baseCommand.getStartTime();
        String orders = baseCommand.getOrders();

        long totalElements = permissionService.count();
        int pageSize = Constants.DEFAULT_PAGE_SIZE;
        int totalPages = (int) (totalElements + pageSize - 1) / pageSize;
        int page = getPageNoFromString(pageStr);
        if(model.containsAttribute(Constants.ADD_FLAG)){
            page = totalPages;
        }else {
            page = Math.min(totalPages, page);
        }

        //构建pagerequest对象
        PageRequest pageRequest = new PageRequest(page - 1, pageSize, getSort(orders) );

        //添加搜索条件
        List<UrlBuilder.PropertyFilter> pfList = new ArrayList<UrlBuilder.PropertyFilter>() ;
        pfList.add(new UrlBuilder.PropertyFilter("permission", permission, UrlBuilder.Type.LIKE));
        pfList.add(new UrlBuilder.PropertyFilter("description", description, UrlBuilder.Type.LIKE));

        Page<Permission> resultPage= permissionService.getDataTableByCriteriaQuery(pageRequest, pfList);

        //添加分页条件
        List<UrlBuilder.PropertyFilter> searchConditionList = new ArrayList<UrlBuilder.PropertyFilter>();
        searchConditionList.add(new UrlBuilder.PropertyFilter("searchKey1", permission));
        searchConditionList.add(new UrlBuilder.PropertyFilter("searchKey2", description));
        searchConditionList.add(new UrlBuilder.PropertyFilter("startTime", startTime == null ? null : new DateTime(startTime).toString("yyyy-MM-dd")));

        addPageInfo(model, request, response, orders, page, pageRequest, resultPage, searchConditionList,"/manage/permissions/list");

        return "/manage/permissions/list";
    }



    /**
     * 编辑权限
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model) {

        Permission permissions = permissionService.findById(id);

        model.addAttribute("permissions", permissions);


        return "/manage/permissions/edit";

    }


    /**
     * 更新权限
     * @param permission
     * @param result
     * @param redirectAttrs
     * @param redirectUrl
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid Permission permission,
                         BindingResult result,
                         RedirectAttributes redirectAttrs,
                         @CookieValue(Constants.REDIRECT_URL) String redirectUrl) {

        //若表单对象验证有错,则返回当前页面,供用户重写填写
        if (result.hasErrors()) {

            return null;
        }

        Permission permissionsToUpdate = permissionService.findById(permission.getId());

        //设置更新参数
        permissionsToUpdate.setDescription(permission.getDescription());
        permissionsToUpdate.setPermission(permission.getPermission());

        //更新
        permissionService.update(permissionsToUpdate);

        //添加一条成功消息
        addUpdateSuccessMessage(redirectAttrs);


        return getRedirectUrl(redirectUrl);
    }


    /**
     * 删除角色
     *
     * @param id
     * @param redirectAttrs
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable Integer id,
                         RedirectAttributes redirectAttrs,
                         @CookieValue(Constants.REDIRECT_URL) String redirectUrl) {

        permissionService.delete(id);

        //添加一条成功消息
        addDeleteSuccessMessage(redirectAttrs);

        return getRedirectUrl(redirectUrl);

    }


    /**
     * 批量删除角色
     * @param baseCommand
     * @param result
     * @param redirectAttrs
     * @param redirectUrl
     * @return
     */
    @RequestMapping(value = "/batchDelete")
    public String batchDelete(@Validated(BatchDeleteGroup.class) BaseCommand baseCommand,
                              BindingResult result,
                              RedirectAttributes redirectAttrs,
                              @CookieValue(Constants.REDIRECT_URL) String redirectUrl) {
        if (result.hasErrors()) {
            addRedirectError(redirectAttrs, "至少选择一个删除项");
        } else {
            permissionService.batchDelete(baseCommand.getIds());
            addDeleteSuccessMessage(redirectAttrs);
        }

        return getRedirectUrl(redirectUrl);
    }

}

