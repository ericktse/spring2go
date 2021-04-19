package com.spring2go.common.core.util;

import java.util.Map;

/**
 * @description: TODO
 * @author: xiaobin
 * @date: 2021-04-19 14:59
 */
public class HeaderContextHolder {

    private HeaderContextHolder() {
    }

    private static final ThreadLocal<Map<String, String>> CTX = new ThreadLocal<>();

    public static void setContext(Map<String, String> map) {
        CTX.set(map);
    }

    public static void removeContext() {
        CTX.remove();
    }

    public static String getHeader(String key) {
        if (isEmpty()) {
            return null;
        }
        return CTX.get().get(key);
    }

    public static boolean isEmpty() {
        return CTX.get().isEmpty();
    }
}
