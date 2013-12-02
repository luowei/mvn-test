package com.rootls.base.service.impl;

import com.rootls.base.model.Menu;
import com.rootls.base.repository.MenuRepository;
import com.rootls.base.service.MenuService;
import com.rootls.base.util.DynamicSpecifications;
import com.rootls.base.util.UrlBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 上午1:24
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    MenuRepository menuRepository;

    @Override
    public long count() {
        return menuRepository.count();
    }

    @Override
    public Page<Menu> getPageByCriteriaQuery(PageRequest pageRequest, List<UrlBuilder.PropertyFilter> pfList) {
        Specification<Menu> spec = DynamicSpecifications.<Menu>byPropertyFilter(pfList, Menu.class);
        return menuRepository.findAll(spec, pageRequest);
    }

    @Override
    public List<Menu> getTopLevelMenus() {
        return menuRepository.findByParentIdOrderBySequenceAsc(0);
    }

    @Override
    public Menu getFirstSubMenu() {
        return menuRepository.getFirstSubMenu();
    }

    @Override
    public Menu getSubMenu(int id) {
        Menu menu = menuRepository.findById(id);
        menu.setSubMenus(menuRepository.getSubMenu(id));
        return menu;
    }

    @Override
    public Menu findById(Integer id) {
        return menuRepository.findById(id);
    }

    @Override
    public void update(Menu menu) {
        menuRepository.update(menu);
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
