package com.rootls.base.service.impl;

import com.rootls.base.bean.DataTable;
import com.rootls.base.bean.PageRequest;
import com.rootls.base.model.Menu;
import com.rootls.base.service.MenuService;
import com.rootls.base.util.UrlBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 上午1:24
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MenuServiceImpl implements MenuService{
    @Override
    public long count() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public DataTable<Menu> getDataTableByCriteriaQuery(PageRequest pageRequest, List<UrlBuilder.PropertyFilter> pfList) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Menu> getTopLevelMenus() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Menu getFirstSubMenu() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Menu getSubMenu(int id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Menu findById(Integer id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(Menu menus) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(int id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void batchDelete(Integer[] menuIds) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(Menu menu) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
