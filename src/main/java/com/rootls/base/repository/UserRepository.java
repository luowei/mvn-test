package com.rootls.base.repository;

import com.rootls.base.model.Role;
import com.rootls.base.model.User;
import com.rootls.base.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 下午2:30
 * To change this template use File | Settings | File Templates.
 */
public interface UserRepository  extends PagingAndSortingRepository<User,Integer>,
        JpaSpecificationExecutor<User>,UserRepositoryCustom {

    User findById(Integer id);

    List<Role> findRolesById(Integer id);
}
