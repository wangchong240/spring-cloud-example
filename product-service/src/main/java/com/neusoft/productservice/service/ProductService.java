package com.neusoft.productservice.service;

import com.neusoft.productservice.domain.Product;

import java.util.List;

/**
 * 商品列表
 */
public interface ProductService {

    List<Product> productList();

    Product findById(int id);
}
