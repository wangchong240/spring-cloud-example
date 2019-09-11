package com.neusoft.orderservice.service;

import com.neusoft.orderservice.fallback.ProductFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 产品信息客户端，用feign直接调用接口
 */
@FeignClient(name = "product-service", fallback = ProductFallback.class)
public interface ProductClient {

    @GetMapping("/api/v1/product/product")
    String findById(@RequestParam("id") Integer id);

}
