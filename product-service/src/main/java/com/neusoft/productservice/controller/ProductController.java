package com.neusoft.productservice.controller;

import com.neusoft.productservice.domain.Product;
import com.neusoft.productservice.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Value("${server.port}")
    private Integer port;

    @Autowired
    private ProductService productService;

    /**
     * 获取产品列表
     * @return 产品列表
     */
    @GetMapping("/list")
    public List<Product> getProducts() {
        return productService.productList();
    }

    /**
     * 获取产品信息
     * @param id 主键
     * @return 产品信息
     */
    @GetMapping("/product")
    public Product findById(Integer id) {

        Product product = productService.findById(id);
        //记录来自哪个服务的
        Product result = new Product();
        BeanUtils.copyProperties(product, result);
        result.setName(result.getName() + ": data from port:" + port);

        return result;
    }
}
