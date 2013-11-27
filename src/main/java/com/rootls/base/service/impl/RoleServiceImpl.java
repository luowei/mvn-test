package com.rootls.base.service.impl;

import com.rootls.base.bean.DataTable;
import com.rootls.base.bean.PageRequest;
import com.rootls.base.model.Role;
import com.rootls.base.service.RoleService;
import com.rootls.base.util.UrlBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 下午2:34
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public long count() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public DataTable<Role> getDataTableByCriteriaQuery(PageRequest pageRequest, List<UrlBuilder.PropertyFilter> pfList) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Role findById(Integer id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(Role roles) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Long id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void batchDelete(Integer[] ids) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void save(Role role) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Role> getRolesForSelect1(Integer id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Role> getRolesForSelect2(Integer id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void assignOrRemoveRolesForUser(Integer userId, Integer[] ids1, Integer[] ids2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
