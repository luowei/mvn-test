package com.rootls.base.cache;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionObserver;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.Transcoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @className:SpyMemcachedManager
 * @classDescription:
 * @author:Administrator
 * @createTime:12-6-12
 */
public abstract class SpyMemcachedManager implements CacheManager {
    private static Logger logger = LoggerFactory.getLogger(SpyMemcachedManager.class);

    private List<SpyMemcachedServer> serverList;

    private MemcachedClient mc;
    private volatile boolean isConnected = false;

    // 有效期默认一天
    private int timeToLive = 60 * 60 * 24 * 1;


    public SpyMemcachedManager(){
        super();
    }


    public SpyMemcachedManager(List<SpyMemcachedServer> serverList) {
        this.serverList = serverList;
    }


    public void init(){
        if (mc != null) {
            return;
        }

        new Thread() {
            public void run() {
                logger.info("initial Memcached...");

                if (mc == null) {
                    try {
                        StringBuffer buf = new StringBuffer();
                        for (int i = 0; i < serverList.size(); i++) {
                            SpyMemcachedServer server = serverList.get(i);
                            buf.append(server.toString()).append(" ");
                        }
                        mc = new MemcachedClient(
                                AddrUtil.getAddresses(buf.toString()));
                        isConnected = true;
                        logger.info(">>>> Initialize MemCached [" + getCacheRegion() + "] completed.");
                    } catch (IOException e) {
                        logger.error(">>>> Initialize MemCached [" + getCacheRegion() + "] failure:", e);
                    }

                }
            }
        }.start();

    }


    public void destroy(){
        if(mc != null){
            mc.shutdown(30, TimeUnit.SECONDS);
        }
    }



    @Override
    public void addObserver(ConnectionObserver obs) {
        checkConnection();
        mc.addObserver(obs);
    }

    @Override
    public void removeObserver(ConnectionObserver obs) {
        checkConnection();
        mc.removeObserver(obs);
    }



    //---- Basic Operation Start ----//
    @Override
    public boolean set(String key, Object value, int expire) {
        checkConnection();
        Future<Boolean> f = mc.set(key, expire, value);
        return getBooleanValue(f);
    }


    @Override
    public boolean set(String key, Object value) {
        checkConnection();
        Future<Boolean> f = mc.set(key, timeToLive, value);
        return getBooleanValue(f);
    }

    @Override
    public Object get(String key) {
        checkConnection();
        return mc.get(key);
    }

    @Override
    public Object asyncGet(String key) {
        checkConnection();

        Object obj = null;
        Future<Object> f = mc.asyncGet(key);
        try {
            obj = f.get(SpyMemcachedConstants.DEFAULT_TIMEOUT,
                    SpyMemcachedConstants.DEFAULT_TIMEUNIT);
        }catch (TimeoutException timeoutException){
            logger.debug("asyncGet operation timeout");
            f.cancel(false);
        }catch (InterruptedException interruptedException){
            Thread.currentThread().interrupt();
        } catch (ExecutionException e){
            logger.error("asyncGet operation occur error ", e);
        }
        return obj;
    }

    @Override
    public boolean add(String key, Object value, int expire) {
        checkConnection();
        Future<Boolean> f = mc.add(key, expire, value);
        return getBooleanValue(f);
    }


    @Override
    public boolean add(String key, Object value) {
        checkConnection();
        Future<Boolean> f = mc.add(key, timeToLive, value);
        return getBooleanValue(f);
    }

    @Override
    public boolean replace(String key, Object value, int expire) {
        checkConnection();
        Future<Boolean> f = mc.replace(key, expire, value);
        return getBooleanValue(f);
    }

    @Override
    public boolean replace(String key, Object value) {
        checkConnection();
        Future<Boolean> f = mc.replace(key, timeToLive, value);
        return getBooleanValue(f);
    }

    @Override
    public boolean delete(String key) {
        checkConnection();
        Future<Boolean> f = mc.delete(key);
        return getBooleanValue(f);
    }

    @Override
    public boolean flush() {
        checkConnection();
        Future<Boolean> f = mc.flush();
        return getBooleanValue(f);
    }

    @Override
    public Map<String, Object> getMulti(Collection<String> keys) {
        checkConnection();
        return mc.getBulk(keys);
    }

    @Override
    public Map<String, Object> getMulti(String[] keys) {
        checkConnection();
        return mc.getBulk(keys);
    }

    @Override
    public Map<String, Object> asyncGetMulti(Collection<String> keys) {
        checkConnection();
        Map map = null;
        Future<Map<String, Object>> f = mc.asyncGetBulk(keys);
        try {
            map = f.get(SpyMemcachedConstants.DEFAULT_TIMEOUT,
                    SpyMemcachedConstants.DEFAULT_TIMEUNIT);
        } catch (Exception e) {
            f.cancel(false);
        }
        return map;
    }

    @Override
    public Map<String, Object> asyncGetMulti(String keys[]) {
        checkConnection();
        Map map = null;
        Future<Map<String, Object>> f = mc.asyncGetBulk(keys);
        try {
            map = f.get(SpyMemcachedConstants.DEFAULT_TIMEOUT,
                    SpyMemcachedConstants.DEFAULT_TIMEUNIT);
        } catch (TimeoutException e){
            logger.debug("asyncGetMulti operation timeout");
            f.cancel(false);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        } catch (ExecutionException e){
            logger.error("asyncGetMulti operation occur error ", e);
        }
        return map;
    }
    //---- Basic Operation End ----//


    //---- increment & decrement Start ----//  
    @Override
    public long increment(String key, int by, long defaultValue, int expire) {
        checkConnection();
        return mc.incr(key, by, defaultValue, expire);
    }

    @Override
    public long increment(String key, int by) {
        checkConnection();
        return mc.incr(key, by);
    }

    @Override
    public long decrement(String key, int by, long defaultValue, int expire) {
        checkConnection();
        return mc.decr(key, by, defaultValue, expire);
    }

    @Override
    public long decrement(String key, int by) {
        checkConnection();
        return mc.decr(key, by);
    }

    @Override
    public long asyncIncrement(String key, int by) {
        checkConnection();
        Future<Long> f = mc.asyncIncr(key, by);
        return getLongValue(f);
    }

    @Override
    public long asyncDecrement(String key, int by) {
        checkConnection();
        Future<Long> f = mc.asyncDecr(key, by);
        return getLongValue(f);
    }
    //  ---- increment & decrement End ----// 



    @Override
    public void printStats() throws IOException {
        printStats(null);
    }

    @Override
    public void printStats(OutputStream stream) throws IOException {
        checkConnection();
        Map<SocketAddress, Map<String, String>> statMap =
                mc.getStats();
        if (stream == null) {
            stream = System.out;
        }
        StringBuffer buf = new StringBuffer();
        Set<SocketAddress> addrSet = statMap.keySet();
        Iterator<SocketAddress> iter = addrSet.iterator();
        while (iter.hasNext()) {
            SocketAddress addr = iter.next();
            buf.append(addr.toString() + "/n");
            Map<String, String> stat = statMap.get(addr);
            Set<String> keys = stat.keySet();
            Iterator<String> keyIter = keys.iterator();
            while (keyIter.hasNext()) {
                String key = keyIter.next();
                String value = stat.get(key);
                buf.append("  key=" + key + ";value=" + value + "/n");
            }
            buf.append("/n");
        }
        stream.write(buf.toString().getBytes());
        stream.flush();
    }

    @Override
    public Transcoder getTranscoder() {
        checkConnection();
        return mc.getTranscoder();
    }


    @Override
    public boolean isConnected(){
        return isConnected;
    }



    private long getLongValue(Future<Long> f) {
        try {
            Long result = f.get(SpyMemcachedConstants.DEFAULT_TIMEOUT,
                    SpyMemcachedConstants.DEFAULT_TIMEUNIT);
            return result.longValue();
        } catch (Exception e) {
            f.cancel(false);
        }
        return -1;
    }

    private boolean getBooleanValue(Future<Boolean> f) {
        try {
            Boolean bool = f.get(SpyMemcachedConstants.DEFAULT_TIMEOUT,
                    SpyMemcachedConstants.DEFAULT_TIMEUNIT);
            return bool.booleanValue();
        } catch (Exception e) {
            f.cancel(false);
            return false;
        }
    }

    private void checkConnection() {
        if (!isConnected()) {
            logger.warn("Memcached not initialized");
        }
    }


    protected abstract String getCacheRegion();


    public void setServerList(List<SpyMemcachedServer> serverList) {
        this.serverList = serverList;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

}

