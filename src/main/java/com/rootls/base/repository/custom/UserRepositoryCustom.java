package com.rootls.base.repository.custom;

import com.rootls.base.model.Role;
import com.rootls.base.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-12-3
 * Time: 上午11:17
 * To change this template use File | Settings | File Templates.
 */
public interface UserRepositoryCustom  extends BaseRepository<User>{

    void deleteUserRole(Integer userId, Integer roleId);

    void saveUserRole(User user, Role role);
}
