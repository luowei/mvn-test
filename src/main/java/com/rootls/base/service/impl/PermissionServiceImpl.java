package com.rootls.base.service.impl;

import com.rootls.base.bean.DataTable;
import com.rootls.base.bean.PageRequest;
import com.rootls.base.model.Permission;
import com.rootls.base.model.Role;
import com.rootls.base.service.PermissionService;
import com.rootls.base.util.UrlBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 下午2:32
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Override
    public DataTable<Permission> getDataTableByCriteriaQuery(PageRequest pageRequest, List<UrlBuilder.PropertyFilter> pfList) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Permission findById(Integer id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long count() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(Permission permissionsToUpdate) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Integer id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void batchDelete(Integer[] ids) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getZTreeNodes(Role role) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void assignOrRemovePermissionsForRole(Integer roleId, Integer[] ids1, Integer[] ids2, boolean unchecked) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
