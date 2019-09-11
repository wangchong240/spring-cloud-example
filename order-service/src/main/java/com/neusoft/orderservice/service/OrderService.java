package com.neusoft.orderservice.service;

import com.neusoft.orderservice.domain.ProductOrder;

/**
 * 订单业务层
 */
public interface OrderService {

    ProductOrder save(Integer userId, Integer productId);
}
