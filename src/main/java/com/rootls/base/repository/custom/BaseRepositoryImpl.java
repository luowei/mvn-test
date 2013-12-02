package com.rootls.base.repository.custom;

import com.rootls.base.model.IdEntity;
import com.rootls.base.util.GenericsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-11-29
 * Time: 上午9:47
 * To change this template use File | Settings | File Templates.
 */
@Resource
@Transactional
public class BaseRepositoryImpl<T extends IdEntity> extends JdbcDaoSupport implements BaseRepository<T> {

    private final Class<T> entityClass = GenericsUtils.getSuperClassGenricType(getClass());

    protected Class<T> clazzz;
    public Class<T> getClazzz() {
        return clazzz;
    }

    //注入JPA EntityManager
    @PersistenceContext(unitName = "jpa.trade")
    EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    //注入JDBC数据源
    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    @PostConstruct
    void init(){
        setDataSource(dataSource);
    }



}
