package com.spring2go.common.interceptor.predicate;

import com.spring2go.common.interceptor.definition.RouteDefinition;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.server.PathContainer.parsePath;

/**
 * 拦截器断言
 *
 * @author xiaobin
 */
public class PathRoutePredicate implements InterceptorPredicate {

    @Override
    public boolean apply(HttpServletRequest httpServletRequest, RouteDefinition route) {
        PathContainer path = parsePath(httpServletRequest.getRequestURI());
        PathPatternParser pathPatternParser = new PathPatternParser();
        PathPattern pathPattern = pathPatternParser.parse(route.getPath());
        if (pathPattern.matches(path)) {
            return true;
        }
        return false;
    }
}
