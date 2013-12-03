package com.rootls.base.service.impl;

import com.rootls.base.model.Role;
import com.rootls.base.model.User;
import com.rootls.base.repository.RoleRepository;
import com.rootls.base.repository.UserRepository;
import com.rootls.base.service.RoleService;
import com.rootls.base.util.DynamicSpecifications;
import com.rootls.base.util.UrlBuilder;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 下午2:34
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleRepository roleRepository;

    @Resource
    UserRepository userRepository;

    @Override
    public long count() {
        return roleRepository.count();
    }

    @Override
    public Page<Role> getDataTableByCriteriaQuery(PageRequest pageRequest, List<UrlBuilder.PropertyFilter> pfList) {
        Specification<Role> spec = DynamicSpecifications.<Role>byPropertyFilter(pfList, Role.class);
        return roleRepository.findAll(spec, pageRequest);
    }

    @Override
    public Role findById(Integer id) {
        return roleRepository.findById(id);
    }

    @Override
    public void update(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void delete(Integer id) {
        roleRepository.delete(id);
    }

    @Override
    public void batchDelete(Integer[] ids) {
        roleRepository.batchDelete(ids,Role.class);
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> getRolesForSelect1(Integer id) {
        //获取所有角色列表
        List<Role> allList = IteratorUtils.toList(roleRepository.findAll().iterator());

        //把所有角色放入map中,key为角色id, value为角色对象
        Map<Integer, Role> map = new HashMap<Integer, Role>();
        for (Role role : allList) {
            map.put(role.getId(), role);
        }

        //遍历用户所关联的角色集合,移除map中key与用户关联的角色集合的角色id相同的记录
        List<Role> roles = userRepository.findRolesById(id);

        for (Role r : roles) {
            if (map.containsKey(r.getId())) {
                map.remove(r.getId());
            }
        }

        //用于存放结果结合
        List<Role> result = new ArrayList<Role>();

        //将map中的value存入List集合中
        for (Map.Entry<Integer, Role> entry : map.entrySet()) {
            result.add(entry.getValue());
        }

        return result;
    }

    @Override
    public List<Role> getRolesForSelect2(Integer id) {
        return userRepository.findRolesById(id);
    }

    @Override
    public void assignOrRemoveRolesForUser(Integer userId, Integer[] ids1, Integer[] ids2) {
        if (userId != null) {
            // 原始的已经是该用户的角色id集合
            List<Integer> originalRoleIdList = roleRepository.findRoleIdsByUserId(userId);

            // 原始的还不是该用户的角色id集合
            List<Integer> list = new ArrayList<Integer>();
            for (Role r : getRolesForSelect1(userId)) {
                list.add(r.getId());
            }

            // 将要为用户新添加的角色id集合
            List<Integer> roleIdListToAdd = new ArrayList<Integer>();
            // 将要为该班级删除的老师id集合
            List<Integer> roleIdListToRemove = new ArrayList<Integer>();

            // 如果ids1数组不为null且长度大于0,
            // 并且ids1数组中有原始的还不是该用户的角色id集合中的元素，
            // 则该元素是该用户要删除的角色id
            if (ids1 != null && ids1.length > 0) {
                Map<Integer, Integer> map = new HashMap<Integer, Integer>();

                for (Integer id : list) {
                    map.put(id, id);
                }

                for (Integer id : ids1) {
                    if (!map.containsKey(id)) {
                        roleIdListToRemove.add(id);
                    }
                }

            } else {
                roleIdListToAdd.addAll(list);
            }


            // 如果ids2数组不为null且长度大于0,
            // 且原来的没有与用户关联的角色id出现在ids2数组中,则为该用户添加该角色,
            if (ids2 != null && ids2.length > 0) {
                Map<Integer, Integer> map1 = new HashMap<Integer, Integer>();
                for (Integer id : originalRoleIdList) {
                    map1.put(id, id);
                }

                for (Integer id : ids2) {
                    if (!map1.containsKey(id)) {
                        roleIdListToAdd.add(id);
                    }
                }
            }
            //反之,则没有要添加的角色,而是将原来是该用户的角色也删除
            else {
                roleIdListToRemove.addAll(originalRoleIdList);
            }

            User user = userRepository.findById(userId);

//            System.out.println("roleIdListToAdd="+roleIdListToAdd);
//            System.out.println("roleIdListToRemove="+roleIdListToRemove);

            // 为用户删除角色
            for (Integer roleId : roleIdListToRemove) {
                userRepository.deleteUserRole(userId, roleId);
            }

            // 为用户添加角色
            for (Integer roleId : roleIdListToAdd) {
                Role role = roleRepository.findById(roleId);
                userRepository.saveUserRole(user,role);
            }

        }
    }
}
