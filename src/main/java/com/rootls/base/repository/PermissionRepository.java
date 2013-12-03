package com.rootls.base.repository;

import com.rootls.base.model.Permission;
import com.rootls.base.repository.custom.PermissionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 下午2:28
 * To change this template use File | Settings | File Templates.
 */
public interface PermissionRepository extends PagingAndSortingRepository<Permission,Integer>,
        JpaSpecificationExecutor<Permission>,PermissionRepositoryCustom {

    Permission findById(Integer id);
}
