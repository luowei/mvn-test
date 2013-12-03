package com.rootls.base.service.impl;

import com.rootls.base.model.User;
import com.rootls.base.repository.UserRepository;
import com.rootls.base.service.UserService;
import com.rootls.base.util.DynamicSpecifications;
import com.rootls.base.util.UrlBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 下午2:34
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public Page<User> getDataTableByCriteriaQuery(PageRequest pageRequest, List<UrlBuilder.PropertyFilter> pfList) {
        Specification<User> spec = DynamicSpecifications.<User>byPropertyFilter(pfList, User.class);
        return userRepository.findAll(spec, pageRequest);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public void batchDelete(Integer[] ids) {
        userRepository.batchDelete(ids,User.class);
    }
}
