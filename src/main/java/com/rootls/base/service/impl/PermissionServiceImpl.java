package com.rootls.base.service.impl;

import com.rootls.base.model.Permission;
import com.rootls.base.model.Role;
import com.rootls.base.repository.PermissionRepository;
import com.rootls.base.repository.RoleRepository;
import com.rootls.base.service.PermissionService;
import com.rootls.base.util.DynamicSpecifications;
import com.rootls.base.util.UrlBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

    Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Resource
    PermissionRepository permissionRepository;

    @Resource
    RoleRepository roleRepository;

    @Override
     public Page<Permission> getDataTableByCriteriaQuery(PageRequest pageRequest, List<UrlBuilder.PropertyFilter> pfList) {
        Specification<Permission> spec = DynamicSpecifications.<Permission>byPropertyFilter(pfList, Permission.class);
        return permissionRepository.findAll(spec, pageRequest);
    }

    @Override
    public Permission findById(Integer id) {
        return permissionRepository.findById(id);
    }

    @Override
    public long count() {
        return permissionRepository.count();
    }

    @Override
    public void update(Permission permissionsToUpdate) {
//        permissionRepository.update(permissionsToUpdate);
        permissionRepository.save(permissionsToUpdate);
    }

    @Override
    public void delete(Integer id) {
        permissionRepository.delete(id);
    }

    @Override
    public void batchDelete(Integer[] ids) {
        permissionRepository.batchDelete(ids,Permission.class);
    }

    @Override
    public String getZTreeNodes(Role role) {
        List<Permission> list = new ArrayList<Permission>();

        List<Permission> allPermissions = findAll();
        List<Permission> partialPermissions = new ArrayList<Permission>(role.getPermissions());

        Collection<Permission> subtractList = CollectionUtils.subtract(allPermissions, partialPermissions);

        for (Permission p : partialPermissions) {
            try {
                Permission permissionModel = Permission.class.cast(p.clone());
                permissionModel.setChecked(true);
                setOpen(p, permissionModel);
                list.add(permissionModel);
            } catch (CloneNotSupportedException e) {
                logger.error(e.getMessage(),e);
            }
        }

        for (Permission p : subtractList) {
            try {
                Permission permissionModel = Permission.class.cast(p.clone());
                setOpen(p, permissionModel);
                list.add(permissionModel);
            } catch (CloneNotSupportedException e) {
                logger.error(e.getMessage(), e);
            }
        }

        ObjectMapper om = new ObjectMapper();
        String result = null;
        try {
            result = om.writeValueAsString(list);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }

        return result;
    }

    private void setOpen(Permission p, Permission permissionModel) {
        if (p.getPermissionGroupId() == null) {
            permissionModel.setOpen(true);
        }
    }

    public List<Permission> findAll() {
        return IteratorUtils.toList(permissionRepository.findAll().iterator());
    }

    @Override
    public void assignOrRemovePermissionsForRole(Integer roleId, Integer[] originCheckedIds, Integer[] checkedIds, boolean unchecked) {

        if (roleId == null) {
            throw new RuntimeException("roleId不能为null");
        }

        boolean changed = false;

        Role role = roleRepository.findById(roleId);

        List<Integer> permissionsToAdd = new ArrayList<Integer>();
        List<Integer> permissionsToRemove = new ArrayList<Integer>();


        if(originCheckedIds == null && checkedIds != null){
            permissionsToAdd.addAll(Arrays.asList(checkedIds));
            changed = true;
        }

        if(checkedIds == null && unchecked){
            permissionsToRemove.addAll(Arrays.asList(originCheckedIds));
            changed = true;
        }

        if (checkedIds != null && originCheckedIds != null) {
            List<Integer> originCheckedIdList = Arrays.asList(originCheckedIds);
            List<Integer> checkedIdList = Arrays.asList(checkedIds);

            Collection<Integer> toRemove = CollectionUtils.subtract(originCheckedIdList, checkedIdList);
            Collection<Integer> toAdd = CollectionUtils.subtract(checkedIdList, originCheckedIdList);

            permissionsToAdd.addAll(toAdd);
            permissionsToRemove.addAll(toRemove);

            changed = true;
        }


        //为role添加权限
        for (Integer permissionId : permissionsToAdd) {
            Permission permissions = permissionRepository.findById(permissionId);
            role.getPermissions().add(permissions);
        }


        //为role移除权限
        for (Integer permissionId : permissionsToRemove) {
            Permission permissions = permissionRepository.findById(permissionId);
            role.getPermissions().remove(permissions);
        }


        if (changed) {
            roleRepository.save(role);
        }

    }
}
