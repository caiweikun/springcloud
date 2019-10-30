package com.yikun.springcloud.actuator.elasticsearch;

import com.yikun.springcloud.actuator.ActuatorApplication;
import com.yikun.springcloud.actuator.dao.ProductDao;
import com.yikun.springcloud.actuator.entity.Product;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
// ActuatorApplication 是启动类
@SpringBootTest(classes = ActuatorApplication.class)
public class EsDemoApplicationTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ProductDao productDao;

    /**
     * 创建索引，会根据Product类的@Document注解信息来创建
     */
    @Test
    public void createIndex() {
        elasticsearchTemplate.createIndex(Product.class);
    }

    /**
     * 删除索引
     */
    @Test
    public void deleteIndex(){
        elasticsearchTemplate.deleteIndex(Product.class);
    }


    /**
     * 自定义查询
     */
    @Test
    public void testMatchQuery(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "小米手机"));
        // 搜索，获取结果
        Page<Product> items = productDao.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("total = " + total);
        for (Product item : items) {
            System.out.println(item);
        }
    }

    /**
     * @Description:
     * termQuery:功能更强大，除了匹配字符串以外，还可以匹配
     * int/long/double/float/....
     */
    @Test
    public void testTermQuery(){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.termQuery("price",998.0));
        // 查找
        Page<Product> page = this.productDao.search(builder.build());
        for(Product item:page){
            System.out.println(item);
        }
    }

    /**
     * @Description:布尔查询
     */
    @Test
    public void testBooleanQuery(){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(
                QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("title","华为"))
                        .must(QueryBuilders.matchQuery("brand","华为"))
        );
        // 查找
        Page<Product> page = this.productDao.search(builder.build());
        for(Product item:page){
            System.out.println(item);
        }
    }

    /**
     * @Description:模糊查询
     */
    @Test
    public void testFuzzyQuery(){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.fuzzyQuery("title","faceoooo"));
        Page<Product> page = this.productDao.search(builder.build());
        for(Product item:page){
            System.out.println(item);
        }
    }

    /**
     * @Description:分页查询
     */
    @Test
    public void searchByPage(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));
        // 分页：
        int page = 0;
        int size = 2;
        queryBuilder.withPageable(PageRequest.of(page,size));

        // 搜索，获取结果
        Page<Product> items = this.productDao.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);
        // 总页数
        System.out.println("总页数 = " + items.getTotalPages());
        // 当前页
        System.out.println("当前页：" + items.getNumber());
        // 每页大小
        System.out.println("每页大小：" + items.getSize());

        for (Product item : items) {
            System.out.println(item);
        }
    }



}
