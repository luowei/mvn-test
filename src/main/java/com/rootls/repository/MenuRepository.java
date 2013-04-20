package com.rootls.repository;

import com.rootls.model.Menu;
import org.springframework.data.repository.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 下午2:28
 * To change this template use File | Settings | File Templates.
 */
public interface MenuRepository  extends Repository<Menu,Integer> {

    Menu findById(Integer id);

    List<Menu> findByName(String name);

    List<Menu> findByCreateTimeBetween(Timestamp beginTime,Timestamp endTime);

}
