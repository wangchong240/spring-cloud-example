package com.neusoft.orderservice.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.neusoft.orderservice.domain.ProductOrder;
import com.neusoft.orderservice.service.OrderService;
import com.neusoft.orderservice.service.ProductClient;
import com.neusoft.orderservice.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * 订单业务层
 */
@Service
public class OrderServiceImplFeign implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImplFeign.class);

    @Resource
    private ProductClient productClient;

    @Override
    public ProductOrder save(Integer userId, Integer productId) {

        logger.info("order-service:{}", "save()方法");

        String str = productClient.findById(productId);
        JsonNode product = JsonUtil.str2JsonNode(str);

        //2.构建订单信息
        ProductOrder order = new ProductOrder();
        order.setCreateTime(new Date());
        order.setUserId(userId);
        order.setOrderNum(UUID.randomUUID().toString());
        order.setProductName(product.get("name").toString());
        order.setPrice(Integer.valueOf(product.get("price").toString()));

        return order;
    }
}
