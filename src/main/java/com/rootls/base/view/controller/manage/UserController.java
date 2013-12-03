package com.rootls.base.view.controller.manage;

import com.rootls.base.bean.Constants;
import com.rootls.base.model.User;
import com.rootls.base.service.RoleService;
import com.rootls.base.service.UserService;
import com.rootls.base.util.UrlBuilder;
import com.rootls.base.view.command.UserCommand;
import com.rootls.base.view.controller.BaseController;
import com.rootls.base.view.groups.BatchDeleteGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

/**
 * @className:UserManageController
 * @classDescription:
 * @author:luowei
 * @createTime:12-5-17
 */
@Controller
@RequestMapping("/manage/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;


    @Autowired
    private RoleService roleService;


    @ModelAttribute
    public UserCommand createUserCommand(){
        return new UserCommand();
    }


    /**
     * 分页列出所有用户
     * @param userCommand
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/list")
    public String list(UserCommand userCommand,
                       Model model,
                       HttpServletRequest request,
                       HttpServletResponse response){


        String pageStr = userCommand.getPage();
        String username = userCommand.getSearchKey1();
        Date startTime = userCommand.getStartTime();
        String orders = userCommand.getOrders();

        long totalElements = userService.count();
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
        List<PropertyFilter> pfList = new ArrayList<PropertyFilter>() ;
        pfList.add(new PropertyFilter("username", username, UrlBuilder.Type.LIKE));
//        pfList.add(new PropertyFilter("createTime", startTime, Type.GE));

        Page<User> resultPage = userService.getDataTableByCriteriaQuery(pageRequest, pfList);

        //添加分页条件
        List<UrlBuilder.PropertyFilter> searchConditionList = new ArrayList<UrlBuilder.PropertyFilter>();
        searchConditionList.add(new PropertyFilter("searchKey1", username));
        searchConditionList.add(new PropertyFilter("startTime", startTime == null ? null : new DateTime(startTime).toString("yyyy-MM-dd")));

        addPageInfo(model, request, response, orders, page, pageRequest, resultPage, searchConditionList,"/manage/user/list");

        return "/manage/user/list";
    }


    /**
     * 编辑用户
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    @RequiresPermissions("user:update")
    public String edit(@PathVariable Integer id,  Model model){

        model.addAttribute("userCommand",userService.findById(id));

        return "/manage/user/edit";
    }


    /**
     * 更新用户
     * @param userCommand
     * @param result
     * @return
     */
    @RequestMapping("/update")
    @RequiresPermissions("user:update")
    public String update(@Valid UserCommand userCommand,
                         BindingResult result,
                         RedirectAttributes redirectAttrs,
                         @CookieValue(Constants.REDIRECT_URL) String redirectUrl){
        if(result.hasErrors()){
            return null;
        }

        User user = userService.findById(userCommand.getId());
        user.setName(userCommand.getUsername());
        user.setEmail(userCommand.getEmail());

        userService.update(user);

        //添加一条成功消息
        addUpdateSuccessMessage(redirectAttrs);

        return getRedirectUrl(redirectUrl);
    }


    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    @RequiresPermissions("user:delete")
    public String delete(@PathVariable Integer id,
                         RedirectAttributes redirectAttrs,
                         @CookieValue(Constants.REDIRECT_URL) String redirectUrl){

        userService.delete(id);

        //添加一条成功消息
        addDeleteSuccessMessage(redirectAttrs);

        return getRedirectUrl(redirectUrl);
    }


    /**
     * 批量删除用户
     * @param userCommand
     * @param result
     * @param redirectAttrs
     * @param redirectUrl
     * @return
     */
    @RequestMapping(value = "/batchDelete")
    public String batchDelete(@Validated(BatchDeleteGroup.class) UserCommand userCommand,
                              BindingResult result,
                              RedirectAttributes redirectAttrs,
                              @CookieValue(Constants.REDIRECT_URL) String redirectUrl) {
        if (result.hasErrors()) {
            addRedirectError(redirectAttrs, "至少选择一个删除项");
        } else {
            userService.batchDelete(userCommand.getIds());
            addDeleteSuccessMessage(redirectAttrs);
        }

        return getRedirectUrl(redirectUrl);
    }



    /**
     * 显示角色分配列表
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/assignRolesPre/{id}")
    public String assignRolesForUserPre(@PathVariable Integer id, Model model){

        model.addAttribute("roleList1", roleService.getRolesForSelect1(id));
        model.addAttribute("roleList2", roleService.getRolesForSelect2(id));
        model.addAttribute("user", userService.findById(id));

        return "/manage/user/assignRoles";
    }


    /**
     * 为用户分配角色
     * @param userId
     * @param ids1
     * @param ids2
     * @param redirectAttrs
     * @return
     */
    @RequestMapping("/assignRoles")
    public String assignRolesForUser(@RequestParam Integer userId,
                                     @RequestParam(required = false) Integer[] ids1,
                                     @RequestParam(required = false) Integer[] ids2,
                                     RedirectAttributes redirectAttrs){

        if (userId != null) {
            roleService.assignOrRemoveRolesForUser(userId, ids1, ids2);
        }

        redirectAttrs.addAttribute("userId", userId);

        //添加一条成功消息
        addRedirectMessage(redirectAttrs, "分配成功");

        return "redirect:/manage/user/assignRolesPre/{userId}";
    }

}

