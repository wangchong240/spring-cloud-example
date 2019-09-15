package com.neusoft.orderservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.neusoft.orderservice.domain.ProductOrder;
import com.neusoft.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Resource
    private OrderService orderServiceImplFeign;

    @Resource
    private OrderService orderServiceImpl;

    @Autowired
    private StringRedisTemplate redisTemplate;

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

        //发送短信
        new Thread(() -> sendMes()).start();
        //提示信息
        Map<String, Object> data = new HashMap<>();
        data.put("code", -1);
        data.put("msg", "抢购人数太多了，您被挤掉了，稍后再试！");
        return data;
    }

    /**
     * 发送短信
     */
    private void sendMes() {
        //订单失败，发送短信
        String saveOrderKey = "save-order";
        String saveOrderValue = redisTemplate.opsForValue().get(saveOrderKey);
        if(StringUtils.isEmpty(saveOrderValue)) {
            System.out.println("紧急短信，用户下单失败，请核查！");
            //调用短信发送接口
            redisTemplate.opsForValue().set(saveOrderKey, "save-order-fail", 20, TimeUnit.SECONDS);
        }else {
            System.out.println("已发送短信，20s内不重复发送！");
        }
    }

}
