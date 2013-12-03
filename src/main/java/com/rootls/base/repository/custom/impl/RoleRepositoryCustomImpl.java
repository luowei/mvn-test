package com.rootls.base.repository.custom.impl;

import com.rootls.base.model.Role;
import com.rootls.base.repository.custom.RoleRepositoryCustom;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-12-3
 * Time: 上午11:05
 * To change this template use File | Settings | File Templates.
 */
@Resource
public class RoleRepositoryCustomImpl extends BaseRepositoryImpl<Role> implements RoleRepositoryCustom {


    @Override
    public List<Integer> findRoleIdsByUserId(Integer userId) {

        return null;
    }
}
