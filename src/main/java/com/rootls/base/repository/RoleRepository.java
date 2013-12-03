package com.rootls.base.repository;

import com.rootls.base.model.Role;
import com.rootls.base.repository.custom.RoleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 下午2:29
 * To change this template use File | Settings | File Templates.
 */
public interface RoleRepository  extends PagingAndSortingRepository<Role,Integer>,
        JpaSpecificationExecutor<Role>,RoleRepositoryCustom {

    Role findById(Integer id);
}
