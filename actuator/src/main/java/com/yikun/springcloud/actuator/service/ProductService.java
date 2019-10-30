package com.yikun.springcloud.actuator.service;

import com.yikun.springcloud.actuator.dao.ProductDao;
import com.yikun.springcloud.actuator.entity.Product;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    /**
     * 新增一个对象
     * @param product
     * @return
     */
    public Product save(Product product){
        return productDao.save(product);
    }

    /**
     * 批量新增
     * @param products
     */
    public Iterable<Product> batchSave(List<Product> products){
        return productDao.saveAll(products);
    }

    /**
     * 修改
     * elasticsearch中本没有修改，它的修改原理是该是先删除在新增
     * 修改和新增是同一个接口，区分的依据就是id。
     */
    public Product update(Product product){
        return productDao.save(product);
    }

    /**
     * 查询所有
     * @return
     */
    public Iterable<Product> queryAll(){
        // 查找所有
        // 对某字段排序查找所有 Sort.by("price").descending() 降序
        // Sort.by("price").ascending():升序
        Iterable<Product> list = productDao.findAll(Sort.by("price").ascending());
        /*for (Product item:list){
            System.out.println(item);
        }*/
        return list;
    }

    /**
     * 查询价格区间
     */
    public List<Product> queryByPriceBetween(){
        List<Product> list = productDao.findByPriceBetween(2000.00, 3500.00);
        /*for (Product item : list) {
            System.out.println("item = " + item);
        }*/
        return list;
    }

    /**
     * 按 title 模糊查询
     * @param title
     * @return
     */
    public Page<Product> queryByTitle(String title){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.fuzzyQuery("title",title));
        Page<Product> page = this.productDao.search(builder.build());
        /*for(Product item:page){
            System.out.println(item);
        }*/
        return page;
    }



}
