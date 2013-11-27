package com.rootls.base.service.impl;

import com.rootls.base.bean.DataTable;
import com.rootls.base.bean.PageRequest;
import com.rootls.base.model.User;
import com.rootls.base.service.UserService;
import com.rootls.base.util.UrlBuilder;
import org.springframework.stereotype.Service;

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
    @Override
    public User getCurrentUser() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long count() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public DataTable<User> getDataTableByCriteriaQuery(PageRequest pageRequest, List<UrlBuilder.PropertyFilter> pfList) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User findById(Integer id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(User user) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Integer id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void batchDelete(Integer[] ids) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
