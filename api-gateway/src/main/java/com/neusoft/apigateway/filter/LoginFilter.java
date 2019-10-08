package com.neusoft.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 登录过滤器
 */
@Component
public class LoginFilter extends ZuulFilter {

    /**
     * 过滤器类型，前置过滤器：
     * 前置过滤器（pre）、路由过滤器（route）、后置过滤器（post）、异常过滤器（error）
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤器顺序，越小越先执行，可以查看FilterConstants来确定自己的值
     */
    @Override
    public int filterOrder() {
        return 4;
    }

    /**
     * 过滤器是否生效
     */
    @Override
    public boolean shouldFilter() {

        //获取访问路径
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String uri = request.getRequestURI();

        //ACL 权限访问列表，存放在redis中

        //指定路径过滤
        if("/api-gateway/order/api/v1/order/save1".equalsIgnoreCase(uri)) {
            return true;
        }

        return false;
    }

    /**
     * 具体处理逻辑
     */
    @Override
    public Object run() throws ZuulException {

        //JWT生成token
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String token = request.getHeader("token");

        if(StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }

        if(StringUtils.isBlank(token)) {
            //不返回响应
            requestContext.setSendZuulResponse(false);
            //设置响应错误码:401 没有权限
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        return null;
    }
}
