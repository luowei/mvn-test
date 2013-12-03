package com.rootls.base.cache;

/**
 * @className:SpyMemcachedServer
 * @classDescription:
 * @author:luowei
 * @createTime:12-6-12
 */
public class SpyMemcachedServer {
    private String ip;
    private int port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("Port number must be between 0 to 65535");
        }

        this.port = port;
    }


    @Override
    public String toString() {
        return getIp() + ":" + getPort();
    }
}

