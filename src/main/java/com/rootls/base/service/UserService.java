package com.rootls.base.service;

import com.rootls.base.bean.DataTable;
import com.rootls.base.bean.PageRequest;
import com.rootls.base.model.User;
import com.rootls.base.util.UrlBuilder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 下午2:32
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {
    User getCurrentUser();

    long count();

    DataTable<User> getDataTableByCriteriaQuery(PageRequest pageRequest, List<UrlBuilder.PropertyFilter> pfList);

    User findById(Integer id);

    void update(User user);

    void delete(Integer id);

    void batchDelete(Integer[] ids);
}
