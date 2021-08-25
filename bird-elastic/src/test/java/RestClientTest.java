import com.bird.ElasticStackApp;
import com.bird.builder.ElasticBuilder;
import com.bird.builder.ElasticRequestBuilder;
import com.bird.common.ElasticDocument;
import com.bird.common.ElasticIndex;
import com.bird.common.ElasticRequest;
import com.bird.component.impl.DocumentComponent;
import com.bird.component.impl.IndexComponent;
import com.bird.entity.Student;
import com.bird.factory.ElasticClientFactory;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Author lipu
 * @Date 2021/5/8 14:48
 * @Description
 */
@SpringBootTest(classes = ElasticStackApp.class)
public class RestClientTest {


    @Resource
    private IndexComponent indexComponent;
    @Resource
    private DocumentComponent documentComponent;
    @Resource
    private ElasticClientFactory factory;

    /**
     * @Author lipu
     * @Date 2021/8/17 19:14
     * @Description 创建索引
     */
    @Test
    void createIndex() {
        ElasticRequestBuilder builder=new ElasticRequestBuilder();
        ElasticRequest elasticRequest = builder.buildWithIndex(new ElasticIndex().indexName("bird_2021.32_w").alias("bird-log"))
                .build();
        indexComponent.createIndex(elasticRequest);
    }

    /**
     * @Author lipu
     * @Date 2021/8/17 19:19
     * @Description 批量添加文档
     */
    @Test
    void insertList() {
        //模拟数据
        List<Student> studentList=new ArrayList<>();
        Student studentOne=new Student();
        studentOne.setId("1");
        studentOne.setName("张三");
        studentOne.setSex("男");
        studentOne.setAge(20);
        studentOne.setCreateDate(new Date());
        studentOne.setChineseGrade(120);
        studentOne.setMathGrade(140);
        studentOne.setEnglishGrade(95);
        studentOne.setDescription("张三是一个好学生");
        Student studentTwo=new Student();
        studentTwo.setId("2");
        studentTwo.setName("李四");
        studentTwo.setSex("男");
        studentTwo.setAge(22);
        studentTwo.setCreateDate(new Date());
        studentTwo.setChineseGrade(130);
        studentTwo.setMathGrade(130);
        studentTwo.setEnglishGrade(100);
        studentTwo.setDescription("李四是一个好学生,喜欢打篮球");
        studentList.add(studentOne);
        studentList.add(studentTwo);
        ElasticRequestBuilder builder=new ElasticRequestBuilder();
        ElasticRequest elasticRequest = builder.buildWithIndex(new ElasticIndex().indexName("elastic-student"))
                .buildWithDocument(new ElasticDocument<Student>().dataList(studentList))
                .build();
        documentComponent.insertList(elasticRequest);
    }

    /**
     * @Author lipu
     * @Date 2021/8/17 19:38
     * @Description 聚合操作
     */
    @Test
    void aggregate() throws Exception {
        RestHighLevelClient client = factory.getClient();
        SearchRequest searchRequest = new SearchRequest("student");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //分组 terms 分组名 field 分组字段 分组必须要有指标否则没有意义 分组相当于分桶bucket
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_sex")
                .field("sex.keyword");
        //添加指标 求语文成绩的平均分
        aggregation.subAggregation(AggregationBuilders.avg("average_chinese")
                .field("chineseGrade"));
        searchSourceBuilder.aggregation(aggregation);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations aggregations = searchResponse.getAggregations();
        Terms sexAggregation = aggregations.get("by_sex");
        List<? extends Terms.Bucket> buckets = sexAggregation.getBuckets();
        buckets.forEach(item->{
            //获取每个桶的标识
            String key = item.getKeyAsString();
            System.out.println(key);
            //拿每个桶的不同指标结果 注意使用具体的聚合类型接收 不然多态下获取不了value
            ParsedAvg averageChinese = item.getAggregations().get("average_chinese");
            //指标名称
            String name = averageChinese.getName();
            System.out.println(name);
            //指标的值
            double value = averageChinese.getValue();
            System.out.println(value);
        });
    }

    /**
     * @Author lipu
     * @Date 2021/8/20 12:06
     * @Description 查询所有索引库
     */
    @Test
    void getIndexList() {
        ElasticBuilder builder=new ElasticRequestBuilder();
        ElasticRequest request = builder.buildWithIndex(new ElasticIndex()
                .alias("bird-log")).build();
        List<String> indexList = indexComponent.getIndexList(request);
        System.out.println(indexList);
    }
}
