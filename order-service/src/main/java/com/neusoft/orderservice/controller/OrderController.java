package com.neusoft.orderservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.neusoft.orderservice.domain.ProductOrder;
import com.neusoft.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Resource
    private OrderService orderServiceImplFeign;

    @Resource
    private OrderService orderServiceImpl;

    @GetMapping("/save")
    public ProductOrder save(Integer userId, Integer productId) {
        return orderServiceImpl.save(userId, productId);
    }

    @HystrixCommand(fallbackMethod = "saveOrderFail")
    @GetMapping("/save1")
    public Object save1(Integer userId, Integer productId) {
        Map<String, Object> data = new HashMap<>();
        data.put("code", 1);
        data.put("data", orderServiceImplFeign.save(userId, productId));
        return data;
    }

    /**
     * 当服务出现异常时，回调函数
     */
    private Object saveOrderFail(Integer userId, Integer productId) {
        Map<String, Object> data = new HashMap<>();
        data.put("code", -1);
        data.put("msg", "抢购人数太多了，您被挤掉了，稍后再试！");
        return data;
    }

}
