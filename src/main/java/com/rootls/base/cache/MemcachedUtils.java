package com.rootls.base.cache;

import com.rootls.base.util.MD5Utils;
import com.rootls.base.util.SpringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @className:MemcachedUtils
 * @classDescription:
 * @author:luowei
 * @createTime:12-6-13
 */
public class MemcachedUtils {
    private static final Logger logger = LoggerFactory.getLogger(MemcachedUtils.class);

    /**
     * 删除service数据的缓存
     * @param key 包名+类名+方法名
     * @param args 方法的参数
     */
    public static void removeCache(String key, Object[] args) {
        ServiceCache cache = (ServiceCache) SpringUtils.getBean("serviceCache");
        String cacheId2 = null;
        try {
            cacheId2 = key + "(" + new ObjectMapper().writeValueAsString(args) + ")";
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }

        logger.debug("remove key: {}", cacheId2);

        cache.delete(MD5Utils.md5Hex(cacheId2));
    }


    public static String getFullQulifiedClassName(Class clazz, String methodName){
        String result = null;
        Class<?>[] interfaces = clazz.getInterfaces();
        if(interfaces == null || interfaces.length < 1){
            result = clazz.getName();
        }else{
            result = interfaces[0].getName();
        }

        result += "." + methodName;

        return result;
    }
}

