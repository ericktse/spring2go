package com.spring2go.common.openai.util;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * 代理
 *
 * @author xiaobin
 */
public class Proxys {
    /**
     * http 代理
     *
     * @param ip
     * @param port
     * @return
     */
    public static Proxy http(String ip, int port) {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
    }

    /**
     * socks5 代理
     *
     * @param ip
     * @param port
     * @return
     */
    public static Proxy socks(String ip, int port) {
        return new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(ip, port));
    }
}
