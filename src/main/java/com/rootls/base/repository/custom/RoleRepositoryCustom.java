package com.rootls.base.repository.custom;

import com.rootls.base.model.Role;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-12-3
 * Time: 上午11:00
 * To change this template use File | Settings | File Templates.
 */
public interface RoleRepositoryCustom extends BaseRepository<Role>{

    List<Integer> findRoleIdsByUserId(Integer userId);
}
