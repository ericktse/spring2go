package com.spring2go.common.log.config;

import com.spring2go.common.core.util.HeaderContextHolder;
import com.spring2go.common.core.util.ServletUtils;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description: 线程上下文装饰器
 * @author: xiaobin
 * @date: 2021-04-19 14:29
 */
public class ContextTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        //将当前线程的 context 装饰到指定的 Runnable，最后重置当前线程上下文
        RequestAttributes context = RequestContextHolder.currentRequestAttributes();

        //当http请求连接关闭的时候就会清除掉contentLength、headers 之类的信息，调用tomcat的Http11InputBuffer有个recycle方法
        //所以采用浅拷贝RequestAttributes的方式，有可能会获取不到header
        //所以这里将header拷贝一份单独传输
        Map<String, String> map = wrapHeaders(context);
        return () -> {
            try {
                HeaderContextHolder.setContext(map);
                //RequestContextHolder.setRequestAttributes(context);
                runnable.run();
            } finally {
                HeaderContextHolder.removeContext();
                //RequestContextHolder.resetRequestAttributes();
            }
        };
    }

    private Map<String, String> wrapHeaders(RequestAttributes attributes) {
        Map<String, String> map = new LinkedHashMap<>();
        if (attributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
            Enumeration<String> enumeration = request.getHeaderNames();
            if (enumeration != null) {
                while (enumeration.hasMoreElements()) {
                    String key = enumeration.nextElement();
                    String value = request.getHeader(key);
                    map.put(key, value);
                }
            }
        }
        return map;
    }
}
