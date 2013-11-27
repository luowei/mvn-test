package com.rootls.base.service;

import com.rootls.base.bean.DataTable;
import com.rootls.base.bean.PageRequest;
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
public interface RoleService {
    long count();

    DataTable<Role> getDataTableByCriteriaQuery(PageRequest pageRequest, List<UrlBuilder.PropertyFilter> pfList);

    Role findById(Integer id);

    void update(Role roles);

    void delete(Long id);

    void batchDelete(Integer[] ids);

    void save(Role role);

    List<Role> getRolesForSelect1(Integer id);

    List<Role> getRolesForSelect2(Integer id);

    void assignOrRemoveRolesForUser(Integer userId, Integer[] ids1, Integer[] ids2);
}
