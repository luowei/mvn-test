package com.rootls.base.service;

import com.rootls.base.model.User;
import com.rootls.base.util.UrlBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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

    Page<User> getDataTableByCriteriaQuery(PageRequest pageRequest, List<UrlBuilder.PropertyFilter> pfList);

    User findById(Integer id);

    void update(User user);

    void delete(Integer id);

    void batchDelete(Integer[] ids);
}
