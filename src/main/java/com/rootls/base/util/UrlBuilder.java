package com.rootls.base.util;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: luowei
 * Date: 12-5-1
 * Time: 下午1:23
 */
public abstract class UrlBuilder {

    /**
     * 从给定的路径构建多条件参数的url
     * @param path
     * @param pfList
     * @return
     */
    public static String getUrl(String path, List<PropertyFilter> pfList){
        UriComponentsBuilder ucb = UriComponentsBuilder.fromPath(path);

        for(PropertyFilter pf : pfList){
            String queryName = pf.getName();
            if(queryName != null && pf.isNotBlankOfThisPropertyValue()){
                ucb.queryParam(queryName, pf.getValue());
            }
        }

        UriComponents uriComponents = ucb.build();

        return uriComponents.toUriString();

    }


    /**
     * 从给定的路径和条件参数构建排序url
     * @param path
     * @param pfList
     * @return
     */
    public static String getOrdersUrl(String path, List<PropertyFilter> pfList){

        return getUrl(getUrl(path, pfList), "orders", null);

    }


    /**
     * 从给定的url构建排序url
     * @param uriString
     * @return
     */
    public static String getOrdersUrl(String uriString, String orders){
        String url = getUrl(uriString, "orders", orders);
        if(orders == null || orders.trim().isEmpty()){
            url += "=";
        }

        return url;

    }


    public static String getOrdersUrl(String uriString){

        return getUrl(uriString, "orders", null) + "=";

    }

    public static String getUrl(HttpServletRequest request, List<PropertyFilter> pfList){
        UriComponentsBuilder ucb = UriComponentsBuilder.fromUriString(request.getRequestURI());

        for(PropertyFilter pf : pfList){
            String queryName = pf.getName();
            if(queryName != null && pf.isNotBlankOfThisPropertyValue()){
                ucb.queryParam(queryName, pf.getValue());
            }
        }


        UriComponents uriComponents = ucb.build();

        return uriComponents.toUriString();

    }

    /**
     * 附近新的条件参数到已有的url
     * @param uriString
     * @param paramName
     * @param paramValue
     * @return
     */
    public static String getUrl(String uriString, String paramName, Object paramValue){
        UriComponentsBuilder ucb = UriComponentsBuilder.fromUriString(uriString);
        ucb.queryParam(paramName, paramValue);

        UriComponents uriComponents = ucb.build();

        return uriComponents.toUriString();
    }


    public enum Type {
        EQ, GT, GE, LT, LG, LE, LIKE
    }

    /**
     * @className:PropertyFilter
     * @classDescription:
     * @author:luowei
     * @createTime:12-10-12
     */
    public static class PropertyFilter {
        private String name;    // 条件名称
        private Object value;   // 条件值
        private Type type;      // 查询类型

        public PropertyFilter() {
        }

        public PropertyFilter(String name, Object value) {
            this.name = name;
            this.value = value;
            this.type = Type.EQ;
        }

        public PropertyFilter(String name, Object value, Type type) {
            this.name = name;
            this.value = value;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        /**
         * 返回当前条件参数是否有效,字符串不为空,非字符串的不为null
         *
         * @return
         */
        public boolean isNotBlankOfThisPropertyValue() {
            if (value instanceof String) {
                String str = (String) value;

                return (null != str && !str.trim().isEmpty());
            } else {
                return (null != value);
            }

        }

        /**
         * 添加属性
         *
         * @param name
         * @param value
         * @return
         */
        public PropertyFilter add(String name, String value, Type type) {

            if (name != null && name != "") {
                if (type == null) {
                    type = Type.EQ;
                }
                return new PropertyFilter(name, value, type);
            } else return this;
        }
    }

    
}
