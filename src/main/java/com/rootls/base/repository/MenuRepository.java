package com.rootls.base.repository;

import com.rootls.base.model.Menu;
import com.rootls.base.repository.custom.MenuRepositoryCustom;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 下午2:28
 * To change this template use File | Settings | File Templates.
 */
public interface MenuRepository  extends PagingAndSortingRepository<Menu,Integer>,
        JpaSpecificationExecutor<Menu>,MenuRepositoryCustom {

    Menu findById(Integer id);

    List<Menu> findByName(String name);

    List<Menu> findByCreateTimeBetween(Timestamp beginTime,Timestamp endTime);

    @Modifying  @Transactional
    @Query(value = "insert into menu (id,name,create_time,sequence, parent_id) " +
            " values(?1,?2,'2013-04-20 18:58:58','100',0)",nativeQuery = true)
    int add(Integer id,String name);


    @Modifying @Transactional
    @Query("update Menu set name = ?1 where id = ?2")
    int updateNameById(String name, Integer id);

    @Modifying  @Transactional
    @Query("delete from Menu where id = ?1")
    int deleteById(Integer id);

    List<Menu> findByParentIdOrderBySequenceAsc(Integer parentId);
}
