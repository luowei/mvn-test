package com.rootls.base.util;


import javax.persistence.Entity;

/**
 * @className:QueryUtils
 * @classDescription:
 * @author:luowei
 * @createTime:12-4-12
 */
public abstract class QueryUtils {


    /**
     * 对象的别名统一定义为o
     * 例如: select count(o) from XXX o  where o.name=:name
     *
     * @param entityClass
     * @param where
     * @param <T>
     * @return
     */
    public static <T> String getCountQuery(Class<T> entityClass, String where) {

        String countQuery = "select count(o) from " + getEntityName(entityClass) + " o ";

        if (hasWhereClause(where)) {
            countQuery = countQuery + " where " + where;
        }

        return countQuery;
    }


    /**
     * 对象的别名统一定义为o
     * 例如: select count(o) from XXX o
     *
     * @param entityClass
     * @param <T>
     * @return
     */
    public static <T> String getCountAllQuery(Class<T> entityClass) {
        return getCountQuery(entityClass, null);
    }


    /**
     *
     * @param entityClass
     * @param <T>
     * @return
     */
    public static <T> String getBatchDeleteQuery(Class<T> entityClass){
        return getBatchDeleteQuery(entityClass, "id");
    }


    /**
     *
     * @param entityClass
     * @param propertyPath
     * @param <T>
     * @return
     */
    public static <T> String getBatchDeleteQuery(Class<T> entityClass, String propertyPath){
        String format = "delete from %s o where o.%s in (:ids)";
        return String.format(format, getEntityName(entityClass), propertyPath);
    }


    /**
     * 对象的别名统一定义为o
     * 例如: select o from XXX o where o.name=:name order by o.name
     *
     * @param entityClass
     * @param where
     * @param orders
     * @param <T>
     * @return
     */
    public static <T> String getSelectQuery(Class<T> entityClass, String where, String orders) {

        StringBuilder sb = new StringBuilder();
        sb.append("select o from ").append(getEntityName(entityClass))
                .append(" o");

        if (hasWhereClause(where)) {
            sb.append(" where ").append(where);
        }

        if (hasOrderByClause(orders)) {
            sb.append(buildOrderByClause(orders));
        }

        return sb.toString();
    }

    private static boolean hasWhereClause(String where) {

        return where != null && !"".equals(where.trim());
    }


    private static boolean hasOrderByClause(String orders) {

        return orders != null && !orders.isEmpty() && orders.contains(":");
    }

//    private static String buildOrderByClause(LinkedHashMap<String, String> orders) {
//
//        StringBuilder sb = new StringBuilder();
//
//        sb.append(" order by ");
//
//        for (Map.Entry<String, String> entry : orders.entrySet()) {
//            sb.append("o.").append(entry.getKey()).append(" ").append(entry.getValue()).append(",");
//        }
//
//        sb.deleteCharAt(sb.length() - 1);
//
//        return sb.toString();
//    }


    /**
     * 构建order by子句,例如: order by o.name asc,o.age desc,o.date desc
     *
     * @param orders 形式必须是 orderName:direction
     * @return
     */
    private static String buildOrderByClause(String orders) {

        StringBuilder sb = new StringBuilder();

        sb.append(" order by ");

        String str = orders.replace(":", " ");

        for (String s : str.split(",")) {
            sb.append("o.").append(s).append(",");
        }

        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    /**
     * 获取实体名称
     *
     * @param entityClass
     * @param <T>
     * @return
     */
    private static <T> String getEntityName(Class<T> entityClass) {

        String entityName = entityClass.getName();

        Entity entity = entityClass.getAnnotation(Entity.class);

        final String name = entity.name();

        if (null != name && !"".equals(name.trim())) {
            entityName = name;
        }

        return entityName;
    }

}

