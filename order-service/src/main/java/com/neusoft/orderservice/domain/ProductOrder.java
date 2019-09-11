package com.neusoft.orderservice.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品订单
 */
@Data
public class ProductOrder implements Serializable {

     private Integer id;

     private String productName;

    /**
     * 订单流水号
     */
    private String orderNum;

     private Integer price;

     private Date createTime;

     private Integer userId;

     private String userName;
}
