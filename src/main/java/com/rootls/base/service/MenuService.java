package com.rootls.base.service;

import com.rootls.base.model.Menu;
import com.rootls.base.util.UrlBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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

    Page<Menu> getPageByCriteriaQuery(PageRequest pageRequest, List<UrlBuilder.PropertyFilter> pfList);

    List<Menu> getTopLevelMenus();

    Menu getFirstSubMenu();

    Menu getSubMenu(int id);

    Menu findById(Integer id);

    void update(Menu menu);

    void delete(int id);

    void batchDelete(Integer[] menuIds);

    void save(Menu menu);
}
