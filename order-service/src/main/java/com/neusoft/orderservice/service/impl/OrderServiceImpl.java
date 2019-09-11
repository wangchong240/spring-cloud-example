package com.neusoft.orderservice.service.impl;

import com.neusoft.orderservice.domain.ProductOrder;
import com.neusoft.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 订单业务层
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate template;

    @Override
    public ProductOrder save(Integer userId, Integer productId) {

        //1.获取产品信息
        Map<String, Object> product = template.getForObject("http://product-service/api/v1/product/product?id=1", HashMap.class);
        System.out.println(product);

        //2.构建订单信息
        ProductOrder order = new ProductOrder();
        order.setCreateTime(new Date());
        order.setUserId(userId);
        order.setOrderNum(UUID.randomUUID().toString());
        order.setProductName((String) product.get("name"));
        order.setPrice(Integer.valueOf(product.get("price").toString()));

        return order;
    }
}
