package com.rootls.base.repository.custom.impl;

import com.rootls.base.model.IdEntity;
import com.rootls.base.repository.custom.BaseRepository;
import com.rootls.base.util.GenericsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.Entity;
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
    void init() {
        setDataSource(dataSource);
    }


    @Override
    public void batchDelete(Integer[] ids, Class<T> menuClass) {
        if (ids != null && ids.length > 0) {
            String ql = "delete from %s o where o.id in (:ids)";
            ql = String.format(ql, getEntityName(menuClass));
            em.createQuery(ql).setParameter("ids", ids).executeUpdate();
        }

    }


    /**
     * 获取实体名称
     *
     * @param entityClass
     * @param <T>
     * @return
     */
    public <T> String getEntityName(Class<T> entityClass) {
        String entityName = entityClass.getName();
        Entity entity = entityClass.getAnnotation(Entity.class);
        final String name = entity.name();

        if (null != name && !"".equals(name.trim())) {
            entityName = name;
        }
        return entityName;
    }
}
