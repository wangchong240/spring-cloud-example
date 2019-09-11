package com.neusoft.orderservice.fallback;

import com.neusoft.orderservice.service.ProductClient;
import org.springframework.stereotype.Component;

/**
 * 产品服务feign整合hystrix熔断回调类
 */
@Component
public class ProductFallback implements ProductClient {

    @Override
    public String findById(Integer id) {
       System.out.println("product-service 调用报错.....");
        return null;
    }
}
