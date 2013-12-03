package com.rootls.base.cache;

import com.rootls.base.util.MD5Utils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @className:CachedAspect
 * @classDescription:
 * @author:luowei
 * @createTime:12-6-12
 */
@Aspect
public class CachedAspect {
    private static final Logger logger = LoggerFactory.getLogger(CachedAspect.class);
    @Resource
    private ServiceCache serviceCache;

    @Around("execution(* com.xxxx.demo.service..*(..)) && @annotation(needCached)")
    public Object serviceCached(ProceedingJoinPoint pjp, NeedCached needCached) throws Throwable {
        String key = createCacheKey(pjp);
        Object obj = serviceCache.asyncGet(key);
        if (obj != null) {
            logger.debug("get obj: {} from the cache server", obj);
            return obj;
        }
        obj = pjp.proceed();
        if (obj != null) {
            serviceCache.set(key, obj, needCached.timeToLive());
            logger.debug("set object: {} to the cache server after query from db", obj);
        }
        return obj;
    }

    @Around("execution(* com.xxxx.demo.dao..*(..)) && @annotation(needCached)")
    public Object daoCached(ProceedingJoinPoint pjp, NeedCached needCached) throws Throwable {
        String key = createCacheKey(pjp);
        Object obj = serviceCache.asyncGet(key);
        if (obj != null) {
            logger.debug("get obj: {} from the cache server", obj);
            return obj;
        }
        obj = pjp.proceed();
        if (obj != null) {
            serviceCache.set(key, obj, needCached.timeToLive());
            logger.debug("set object: {} to the cache server after query from db", obj);
        }
        return obj;
    }

    private String createCacheKey(ProceedingJoinPoint pjp) {
        Signature s = pjp.getSignature();
        Class[] interfaces = pjp.getTarget().getClass().getInterfaces();
        String key = null;
        if(interfaces == null || interfaces.length < 1){
            key = pjp.getTarget().getClass().getName();
        }else {
            key = interfaces[0].getName();
        }
        key += "." + s.getName() + "(";
        Object[] args = pjp.getArgs();
        try {
            key += new ObjectMapper().writeValueAsString(args);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        key += ")";
        logger.debug("search key: {} from cache server", key);
        logger.debug("search md5 key: {} from cache server", MD5Utils.md5Hex(key));
        return MD5Utils.md5Hex(key);
    }
}

