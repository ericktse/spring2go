package com.spring2go.common.interceptor.definition;

import com.spring2go.common.interceptor.chain.SimpleInterceptor;
import lombok.Data;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.tokenizeToStringArray;

/**
 * 路由定义
 *
 * @author xiaobin
 */
@Data
public class RouteDefinition {
    private String id;
    private String path;
    private List<InterceptorDefinition> interceptors = new ArrayList<>();
    private List<SimpleInterceptor> simpleInterceptors = new ArrayList<>();

    public RouteDefinition() {
    }

    public RouteDefinition(String text) throws ValidationException {
        int eqIdx = text.indexOf('=');
        if (eqIdx <= 0) {
            throw new ValidationException(
                    "Unable to parse RouteDefinition text '" + text + "'" + ", must be of the form name=value");
        }

        setId(text.substring(0, eqIdx));

        String[] args = tokenizeToStringArray(text.substring(eqIdx + 1), ",");

        setPath(args[0]);

        for (int i = 1; i < args.length; i++) {
            this.interceptors.add(new InterceptorDefinition(args[i]));
        }
    }
}
