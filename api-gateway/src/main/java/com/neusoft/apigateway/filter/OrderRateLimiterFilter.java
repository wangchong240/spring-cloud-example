package com.neusoft.apigateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 订单接口限流 gauva
 */
public class OrderRateLimiterFilter extends ZuulFilter {

    //每秒限定2000访问量
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(2000);

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
        return -4;
    }

    /**
     * 过滤器是否生效,指定生效的过滤器
     */
    @Override
    public boolean shouldFilter() {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        //指定订单接口限流
        if("/api-gateway/order/api/v1/order/save1".equalsIgnoreCase(request.getRequestURI())) {
            return true;
        }

        return false;
    }

    /**
     * 具体处理逻辑
     */
    @Override
    public Object run() throws ZuulException {

        //校验是否超出流量，即令牌桶，是否还有令牌
        boolean isPass = RATE_LIMITER.tryAcquire();

        RequestContext requestContext = RequestContext.getCurrentContext();
        if(!isPass) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        }

        return null;
    }
}
