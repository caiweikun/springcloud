package com.yikun.springcloud.actuator.dao;

import com.yikun.springcloud.actuator.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data 的强大之处，就在于你不用写任何DAO处理，自动根据方法名或类的信息进行CRUD操作。只要你定义一个接口，然后继承Repository提供的一些子接口，就能具备各种基本的CRUD功能。
 */
@Repository
public interface ProductDao extends ElasticsearchRepository<Product, Long> {

    /**
     * 自定义方法
     * Spring Data 的另一个强大功能，是根据方法名称自动实现功能。
     *
     * 比如：你的方法名叫做：findByTitle，那么它就知道你是根据title查询，然后自动帮你完成，无需写实现类。
     *
     * 当然，方法名称要符合一定的约定：
     * Keyword	Sample
     * And	findByNameAndPrice
     * Or	findByNameOrPrice
     * Is	findByName
     * Not	findByNameNot
     * Between	findByPriceBetween
     * LessThanEqual	findByPriceLessThan
     * GreaterThanEqual	findByPriceGreaterThan
     * Before	findByPriceBefore
     * After	findByPriceAfter
     * Like	findByNameLike
     * StartingWith	findByNameStartingWith
     * EndingWith	findByNameEndingWith
     * Contains/Containing	findByNameContaining
     * In	findByNameIn(Collection<String>names)
     * NotIn	findByNameNotIn(Collection<String>names)
     * Near	findByStoreNear
     * True	findByAvailableTrue
     * False	findByAvailableFalse
     * OrderBy	findByAvailableTrueOrderByNameDesc
     */

    /**
     * @Description:根据价格区间查询
     * @Param price1
     * @Param price2
     */
    List<Product> findByPriceBetween(double price1, double price2);

}
