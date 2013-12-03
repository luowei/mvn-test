package com.rootls.base.view.controller.manage;

import com.rootls.base.bean.Constants;
import com.rootls.base.model.Menu;
import com.rootls.base.service.MenuService;
import com.rootls.base.util.UrlBuilder;
import com.rootls.base.view.command.MenuCommand;
import com.rootls.base.view.controller.BaseController;
import com.rootls.base.view.groups.MenuBatchDeleteGroup;
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
 * User: luowei
 * Date: 12-5-26
 * Time: 上午1:49
 */
@Controller
@RequestMapping("/manage/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;


    /**
     * 分页列出所有菜单
     *
     * @param menuCommand
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(MenuCommand menuCommand,
                       Model model,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        String pageStr = menuCommand.getPage();
        String menuName = menuCommand.getSearchKey1();
        Date startTime = menuCommand.getStartTime();
        String orders = menuCommand.getOrders();

        long totalElements = menuService.count();
        int pageSize = Constants.DEFAULT_PAGE_SIZE;
        int totalPages = (int) (totalElements + pageSize - 1) / pageSize;
        int page = getPageNoFromString(pageStr);
        if (model.containsAttribute(Constants.ADD_FLAG)) {
            page = totalPages;
        } else {
            page = Math.min(totalPages, page);
        }

        //构建pagerequest对象
        PageRequest pageRequest = new PageRequest(page - 1, pageSize, getSort(orders) );


        //构建查询条件
        List<UrlBuilder.PropertyFilter> pfList = new ArrayList<UrlBuilder.PropertyFilter>();
        pfList.add(new PropertyFilter("name", menuName, UrlBuilder.Type.LIKE));
        pfList.add(new PropertyFilter("createTime", startTime, UrlBuilder.Type.GE));

        Page<Menu> resultPage = menuService.getPageByCriteriaQuery(pageRequest, pfList);

        //添加返回的查询条件
        List<UrlBuilder.PropertyFilter> searchConditionList = new ArrayList<UrlBuilder.PropertyFilter>();
        searchConditionList.add(new PropertyFilter("searchKey1", menuName));
        searchConditionList.add(new PropertyFilter("startTime", startTime == null ? null : new DateTime(startTime).toString("yyyy-MM-dd")));

        addPageInfo(model, request, response, orders, page, pageRequest, resultPage, searchConditionList,"/manage/menu/list");

        return "/manage/menu/list";
    }

    @ModelAttribute
    public MenuCommand createMenuCommand() {
        return new MenuCommand();
    }


    /**
     * 返回json格式的顶级菜单列表
     *
     * @return
     */
    @RequestMapping(value = "/topLevelMenus", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Menu> getTopLevelMenuList() {

        return menuService.getTopLevelMenus();
    }


    /**
     * 返回json格式的第一个菜单
     *
     * @return
     */
    @RequestMapping(value = "/firstSubMenu", method = RequestMethod.GET)
    public
    @ResponseBody
    Menu getFirstSubMenu() {

        return menuService.getFirstSubMenu();
    }


    /**
     * 根据父级菜单id查找其子菜单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/subMenu/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Menu getSubMenu(@PathVariable int id) {

        return menuService.getSubMenu(id);
    }


    /**
     * 编辑菜单
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model) {


        Menu menu = menuService.findById(id);

        model.addAttribute("menu", menu);


        return "/manage/menu/edit";

    }


    /**
     * 更新菜单
     *
     * @param menu
     * @param result
     * @param redirectAttrs
     * @param redirectUrl
     * @return
     */
    @RequestMapping(value = "/update")
    public String update(@Valid Menu menu,
                         BindingResult result,
                         RedirectAttributes redirectAttrs,
                         @CookieValue(Constants.REDIRECT_URL) String redirectUrl) {

        //若表单对象验证有错,则返回当前页面,供用户重写填写
        if (result.hasErrors()) {

            return null;
        }

        Menu mnu = menuService.findById(menu.getId());

        //设置更新参数
        mnu.setName(menu.getName());
        mnu.setSequence(menu.getSequence());
        mnu.setUrl(menu.getUrl());

        //更新
        menuService.update(mnu);

        //添加一条成功消息
        addUpdateSuccessMessage(redirectAttrs);


        return getRedirectUrl(redirectUrl);
    }


    /**
     * 删除菜单
     *
     * @param id
     * @param redirectAttrs
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable int id,
                         RedirectAttributes redirectAttrs,
                         @CookieValue(Constants.REDIRECT_URL) String redirectUrl) {

        menuService.delete(id);

        //添加一条成功消息
        addDeleteSuccessMessage(redirectAttrs);

        return getRedirectUrl(redirectUrl);

    }


    /**
     * 批量删除菜单
     *
     * @param menuCommand
     * @param result
     * @param redirectAttrs
     * @param redirectUrl
     * @return
     */
    @RequestMapping(value = "/batchDelete")
    public String batchDelete(@Validated(MenuBatchDeleteGroup.class) MenuCommand menuCommand,
                              BindingResult result,
                              RedirectAttributes redirectAttrs,
                              @CookieValue(Constants.REDIRECT_URL) String redirectUrl) {
        if (result.hasErrors()) {
            addRedirectError(redirectAttrs, "至少选择一个删除项");
        } else {
            menuService.batchDelete(menuCommand.getMenuIds());
            addDeleteSuccessMessage(redirectAttrs);
        }

        return getRedirectUrl(redirectUrl);
    }


    /**
     * 显示菜单添加页面
     *
     * @param model
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public void showMenuAddPage(Model model) {
        model.addAttribute("menu", new Menu());
        model.addAttribute("topLevelMenuList", menuService.getTopLevelMenus());
    }


    /**
     * 添加菜单
     *
     * @param menu
     * @param result
     * @param model
     * @param redirectAttrs
     * @param redirectUrl
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@Valid Menu menu, BindingResult result, Model model,
                      RedirectAttributes redirectAttrs,
                      @CookieValue(Constants.REDIRECT_URL) String redirectUrl) {

        if (result.hasErrors()) {
            model.addAttribute("topLevelMenuList", menuService.getTopLevelMenus());
            return null;
        }

        menu.setCreateTime(new Date());
        menuService.save(menu);

        redirectAttrs.addFlashAttribute(Constants.ADD_FLAG, true);

        //添加一条成功消息
        addSaveSuccessMessage(redirectAttrs);

        return getRedirectUrl(redirectUrl);
    }


}
