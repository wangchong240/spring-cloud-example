package com.neusoft.productservice.service.impl;

import com.neusoft.productservice.domain.Product;
import com.neusoft.productservice.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Map<Integer, Product> map = new HashMap<>();

    static {
        Product p1 = new Product(1, "电话", 333, 100);
        Product p2 = new Product(2, "玩具", 32, 100);
        Product p3 = new Product(3, "汽车", 35, 100);
        Product p4 = new Product(4, "java编程思想", 367, 100);
        Product p5 = new Product(5, "洋娃娃", 89, 100);
        Product p6 = new Product(6, "电话卡", 43, 100);
        Product p7 = new Product(7, "英雄皮肤", 43, 100);

        map.put(p1.getId(), p1);
        map.put(p2.getId(), p2);
        map.put(p3.getId(), p3);
        map.put(p4.getId(), p4);
        map.put(p5.getId(), p5);
        map.put(p6.getId(), p6);
        map.put(p7.getId(), p7);
    }

    @Override
    public List<Product> productList() {
         return new ArrayList<>(map.values());
    }

    @Override
    public Product findById(int id) {
        return map.get(id);
    }
}
