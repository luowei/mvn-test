package com.rootls.base.cache;

import java.io.Serializable;

/**
 * @className:ServiceCache
 * @classDescription:
 * @author:Administrator
 * @createTime:12-6-12
 */
public class ServiceCache extends SpyMemcachedManager implements CacheManager, Serializable {
    private static final String CACHE_REGION = "Service";

    @Override
    protected String getCacheRegion() {
        return CACHE_REGION;
    }
}

