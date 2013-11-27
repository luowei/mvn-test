package com.rootls.base.view.controller.manage;

import com.rootls.base.bean.Constants;
import com.rootls.base.bean.DataTable;
import com.rootls.base.bean.PageRequest;
import com.rootls.base.model.Role;
import com.rootls.base.service.PermissionService;
import com.rootls.base.service.RoleService;
import com.rootls.base.util.UrlBuilder;
import com.rootls.base.view.command.BaseCommand;
import com.rootls.base.view.controller.BaseController;
import com.rootls.base.view.groups.BatchDeleteGroup;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.rootls.base.util.UrlBuilder.PropertyFilter;
import static com.rootls.base.util.UrlBuilder.Type;

/**
 * @className:RoleManageController
 * @classDescription:
 * @author:Administrator
 * @createTime:12-5-23
 */
@Controller
@RequestMapping("/manage/role")
public class RoleController extends BaseController {


    @Autowired
    private RoleService roleService;


    @Autowired
    private PermissionService permissionService;




    /**
     * 分页列出所有菜单
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
        String roleName = baseCommand.getSearchKey1();
        Date startTime = baseCommand.getStartTime();
        String orders = baseCommand.getOrders();

        long totalElements = roleService.count();
        int pageSize = Constants.DEFAULT_PAGE_SIZE;
        int totalPages = (int) (totalElements + pageSize - 1) / pageSize;
        int page = getPageNoFromString(pageStr);
        if(model.containsAttribute(Constants.ADD_FLAG)){
            page = totalPages;
        }else {
            page = Math.min(totalPages, page);
        }

        PageRequest pageRequest = new PageRequest(page, Constants.DEFAULT_PAGE_SIZE, orders);


        //添加搜索条件
        List<UrlBuilder.PropertyFilter> pfList = new ArrayList<UrlBuilder.PropertyFilter>() ;
        pfList.add(new PropertyFilter("name", roleName, Type.LIKE));
        pfList.add(new PropertyFilter("createTime", startTime, Type.GE));

        DataTable<Role> dataTable = roleService.getDataTableByCriteriaQuery(pageRequest, pfList);
        model.addAttribute("dataTable", dataTable);

        //添加索引号
        model.addAttribute("index", pageRequest.getOffset());


        //添加分页条件
        List<UrlBuilder.PropertyFilter> searchConditionList = new ArrayList<UrlBuilder.PropertyFilter>();
        searchConditionList.add(new PropertyFilter("searchKey1", roleName));
        searchConditionList.add(new PropertyFilter("startTime", startTime == null ? null : new DateTime(startTime).toString("yyyy-MM-dd")));
        String conditionUrl = UrlBuilder.getUrl("/manage/role/list", searchConditionList);
        String conditionAndOrdersUrl = UrlBuilder.getOrdersUrl(conditionUrl, orders);
        dataTable.setConditionUrl(request.getContextPath()+conditionAndOrdersUrl);

        //添加排序条件
        model.addAttribute("order", conditionAndOrdersUrl);


        //添加url到cookie
        addRediectUrlCookie(request, response, page, conditionUrl);

        return "/manage/role/list";
    }



    /**
     * 编辑角色
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model) {


        Role role = roleService.findById(id);

        model.addAttribute("role", role);


        return "/manage/role/edit";

    }


    /**
     * 更新角色
     * @param role
     * @param result
     * @param redirectAttrs
     * @param redirectUrl
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid Role role,
                         BindingResult result,
                         RedirectAttributes redirectAttrs,
                         @CookieValue(Constants.REDIRECT_URL) String redirectUrl) {

        //若表单对象验证有错,则返回当前页面,供用户重写填写
        if (result.hasErrors()) {

            return null;
        }

        Role roles = roleService.findById(role.getId());

        //设置更新参数
        roles.setName(role.getName());
        roles.setDescription(role.getDescription());

        //更新
        roleService.update(roles);

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
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttrs,
                         @CookieValue(Constants.REDIRECT_URL) String redirectUrl) {

        roleService.delete(id);

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
            roleService.batchDelete(baseCommand.getIds());
            addDeleteSuccessMessage(redirectAttrs);
        }

        return getRedirectUrl(redirectUrl);
    }


    /**
     * 显示角色添加页面
     * @param model
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public void showRoleAddPage(Model model){
        model.addAttribute("role", new Role());
    }


    /**
     * 添加角色
     * @param role
     * @param result
     * @param redirectAttrs
     * @param redirectUrl
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@Valid Role role, BindingResult result,
                      RedirectAttributes redirectAttrs,
                      @CookieValue(Constants.REDIRECT_URL) String redirectUrl){

        if(result.hasErrors()){
            return null;
        }

        role.setCreateTime(new Date());
        roleService.save(role);

        redirectAttrs.addFlashAttribute(Constants.ADD_FLAG, true);

        //添加一条成功消息
        addSaveSuccessMessage(redirectAttrs);

        return getRedirectUrl(redirectUrl);
    }


    /**
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/assignPermissionsPre/{id}", method = RequestMethod.GET)
    public String assignPermissionsForRolePre(@PathVariable Integer id, Model model){

        Role role = roleService.findById(id);

        model.addAttribute("role", role);

        model.addAttribute("permissionJson", permissionService.getZTreeNodes(role));

        return "/manage/role/assignPermissions";
    }


    /**
     * 为角色分配权限
     * @param roleId
     * @param ids1
     * @param ids2
     * @param redirectAttrs
     * @return
     */
    @RequestMapping(value = "/assignPermissions", method = RequestMethod.POST)
    public String assignPermissions(@RequestParam Integer roleId,
                                    @RequestParam(required = false) Integer[] ids1,
                                    @RequestParam(required = false) Integer[] ids2,
                                    @RequestParam boolean unchecked,
                                    RedirectAttributes redirectAttrs){

        if(roleId != null){
            permissionService.assignOrRemovePermissionsForRole(roleId, ids1, ids2, unchecked);
        }

        redirectAttrs.addAttribute("roleId", roleId);

        //添加一条成功消息
        addRedirectMessage(redirectAttrs, "权限分配成功");

        return "redirect:/manage/role/assignPermissionsPre/{roleId}";
    }


}

