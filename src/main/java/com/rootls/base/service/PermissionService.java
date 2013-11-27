package com.rootls.base.service;

import com.rootls.base.bean.DataTable;
import com.rootls.base.bean.PageRequest;
import com.rootls.base.model.Permission;
import com.rootls.base.model.Role;
import com.rootls.base.util.UrlBuilder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 下午2:32
 * To change this template use File | Settings | File Templates.
 */
public interface PermissionService {
    DataTable<Permission> getDataTableByCriteriaQuery(PageRequest pageRequest, List<UrlBuilder.PropertyFilter> pfList);

    Permission findById(Integer id);

    long count();

    void update(Permission permissionsToUpdate);

    void delete(Integer id);

    void batchDelete(Integer[] ids);

    String getZTreeNodes(Role role);

    void assignOrRemovePermissionsForRole(Integer roleId, Integer[] ids1, Integer[] ids2, boolean unchecked);
}
