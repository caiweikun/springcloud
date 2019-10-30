package com.yikun.springcloud.actuator.controller;

import com.yikun.springcloud.actuator.entity.Product;
import com.yikun.springcloud.actuator.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/es/product")
public class ElasticController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ProductService productService;

    /**
     * 创建索引，会根据Product类的@Document注解信息来创建
     */
    @ApiOperation(value="创建索引", notes="创建索引")
    @GetMapping("/create")
    public String createIndex() {
        elasticsearchTemplate.createIndex(Product.class);
        return "success";
    }

    /**
     * 删除索引
     */
    @ApiOperation(value="删除索引", notes="删除索引")
    @GetMapping("/delete")
    public String deleteIndex() {
        elasticsearchTemplate.deleteIndex(Product.class);
        return "success";
    }

    /**
     * 保存对象
     * @param product
     * @return
     */
    @ApiOperation(value="保存doc", notes="保存doc")
    @PostMapping("/save")
    public String save(Product product) {
        productService.save(product);
        return "save success";
    }

    /**
     * 更改对象
     * @param product
     * @return
     */
    @ApiOperation(value="更改doc", notes="更改doc")
    @PostMapping("/update")
    public String update(Product product) {
        productService.update(product);
        return "save success";
    }

    /**
     * 查询所有
     * @return
     */
    @ApiOperation(value="查询所有", notes="查询所有")
    @PostMapping("/queryAll")
    public Iterable<Product> queryAll() {
        return productService.queryAll();
    }

    /**
     * 查询所有，按title模糊查询
     * @return
     */
    @ApiOperation(value="查询所有", notes="查询所有")
    @PostMapping("/query")
    public Page<Product> queryAll(String title) {
        return productService.queryByTitle(title);
    }

}
