package com.neusoft.productservice.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Product implements Serializable {

    public Product() {};

    public Product(int id, String name, int price, int store) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.store = store;
    }

    /**
     * 主键
     */
    private int id;

    /**
     * 商品名字
     */
    private String name;

    /**
     * 商品单价
     */
    private int price;

    /**
     * 商品库存
     */
    private int store;
}
