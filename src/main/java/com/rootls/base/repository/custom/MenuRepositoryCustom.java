package com.rootls.base.repository.custom;

import com.rootls.base.model.Menu;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-11-29
 * Time: 上午9:49
 * To change this template use File | Settings | File Templates.
 */
//@NoRepositoryBean
public interface MenuRepositoryCustom extends BaseRepository<Menu> {

    Menu getFirstSubMenu();

    List<Menu> getSubMenu(int id);

    void update(Menu menu);

}
