package com.rootls.base.repository.custom;

import com.rootls.base.model.Menu;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-11-29
 * Time: 上午9:53
 * To change this template use File | Settings | File Templates.
 */
@Resource
//@Transactional
public class MenuRepositoryCustomImpl extends BaseRepositoryImpl<Menu> implements MenuRepositoryCustom {

    public MenuRepositoryCustomImpl() {
        this.clazzz = Menu.class;
    }


    /**
     * 获得头一个菜单的子菜单
     *
     * @return
     */
    @Override
    public Menu getFirstSubMenu() {

        String ql = "select m from Menu m where m.parentId=? order by m.sequence asc";
        Menu menu = em.createQuery(ql, Menu.class).setParameter(0, 0).setFirstResult(0).setMaxResults(1).getSingleResult();
        if (menu != null) {
            List<Menu> subMenus = em.createQuery(ql, Menu.class).setParameter(0, 0).getResultList();
            menu.setSubMenus(subMenus);
        }
        return menu;
    }

    @Override
    public List<Menu> getSubMenu(int id) {
        String ql = "select m from Menu m where m.parentId=? order by m.sequence asc";
        List<Menu> subMenus = em.createQuery(ql, Menu.class).setParameter(0, id).getResultList();
        return subMenus;
    }

    @Override
    public void update(Menu menu) {

        em.getTransaction().begin();
//        Menu mnu=em.find(Menu.class, 1);
        em.clear(); //把实体管理器中的所有的实体都变成游离状态
//        em.remove(mnu); //操作的也必须是持久化状态，或者托管状态的对象
//        mnu.setName("tazi22");
        em.merge(menu);
        em.getTransaction().commit();
        em.close();

    }


}
