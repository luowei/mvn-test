package com.rootls.base.service;

import com.rootls.base.bean.DataTable;
import com.rootls.base.bean.PageRequest;
import com.rootls.base.model.Menu;
import com.rootls.base.util.UrlBuilder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 上午1:23
 * To change this template use File | Settings | File Templates.
 */
public interface MenuService {
    long count();

    DataTable<Menu> getDataTableByCriteriaQuery(PageRequest pageRequest, List<UrlBuilder.PropertyFilter> pfList);

    List<Menu> getTopLevelMenus();

    Menu getFirstSubMenu();

    Menu getSubMenu(int id);

    Menu findById(Integer id);

    void update(Menu menus);

    void delete(int id);

    void batchDelete(Integer[] menuIds);

    void save(Menu menu);
}
