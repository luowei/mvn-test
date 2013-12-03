package com.rootls.base.cache;

import net.spy.memcached.ConnectionObserver;
import net.spy.memcached.transcoders.Transcoder;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

/**
 * @className:CacheManager
 * @classDescription:
 * @author:luowei
 * @createTime:12-6-12
 */
public interface CacheManager {
    void addObserver(ConnectionObserver obs);

    void removeObserver(ConnectionObserver obs);

    //---- Basic Operation Start ----//
    boolean set(String key, Object value, int expire);

    boolean set(String key, Object value);

    Object get(String key);

    Object asyncGet(String key);

    boolean add(String key, Object value, int expire);

    boolean add(String key, Object value);

    boolean replace(String key, Object value, int expire);

    boolean replace(String key, Object value);

    boolean delete(String key);

    boolean flush();

    Map<String, Object> getMulti(Collection<String> keys);

    Map<String, Object> getMulti(String[] keys);

    Map<String, Object> asyncGetMulti(Collection<String> keys);

    Map<String, Object> asyncGetMulti(String keys[]);

    //---- increment & decrement Start ----//
    long increment(String key, int by, long defaultValue, int expire);

    long increment(String key, int by);

    long decrement(String key, int by, long defaultValue, int expire);

    long decrement(String key, int by);

    long asyncIncrement(String key, int by);

    long asyncDecrement(String key, int by);

    void printStats() throws IOException;

    void printStats(OutputStream stream) throws IOException;

    Transcoder getTranscoder();

    boolean isConnected();
}
