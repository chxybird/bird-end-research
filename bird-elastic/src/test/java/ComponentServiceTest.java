import com.bird.ElasticStackApp;
import com.bird.builder.ElasticBuilder;
import com.bird.builder.ElasticRequestBuilder;
import com.bird.common.ElasticIndex;
import com.bird.common.ElasticRequest;
import com.bird.component.impl.DocumentComponent;
import com.bird.component.impl.IndexComponent;
import com.bird.config.ElasticConfig;
import com.bird.domain.DataVo;
import com.bird.entity.EsLog;
import com.bird.factory.ElasticClientFactory;
import com.bird.utils.DateUtils;
import com.bird.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.*;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;
import org.elasticsearch.search.aggregations.metrics.ParsedValueCount;
import org.elasticsearch.search.aggregations.metrics.ValueCountAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author lipu
 * @Date 2021/8/25 14:57
 * @Description 浙江浙大中控信息业务模拟测试
 */
@SpringBootTest(classes = ElasticStackApp.class)
@Slf4j
public class ComponentServiceTest {

    @Resource
    private ElasticConfig config;

    @Resource
    private IndexComponent indexComponent;

    @Resource
    private DocumentComponent documentComponent;

    @Resource
    private ElasticClientFactory factory;

    /**
     * @Author lipu
     * @Date 2021/8/25 14:58
     * @Description 索引库初始化
     */
    @Test
    void init() {
        try {
            //加载默认日志数据库mappings配置文件
            ClassPathResource resource = new ClassPathResource("mapping");
            String path = resource.getFile().getPath();
            Map mappingsMap = JsonUtils.fileToEntity(path, config.getDefaultMappingsName(), Map.class);
            ElasticRequestBuilder builder = new ElasticRequestBuilder();
            ElasticRequest request = builder
                    .buildWithIndex(new ElasticIndex()
                            .indexName("bird-log_2021.33_w")
                            .mappings(mappingsMap)
                            .alias(config.getDefaultIndexName()))
                    .build();
            //判断索引库是否存在 存在跳过索引库初始化 不存在初始化索引库
            boolean isExist = indexComponent.existIndex(request);
            if (isExist) {
                log.info("------索引库已初始化,跳过初始化------");
            } else {
                indexComponent.createIndex(request);
            }
        } catch (Exception e) {
            log.error("发生异常!!!");
        }
    }

    /**
     * @Author lipu
     * @Date 2021/8/25 14:57
     * @Description 获取用户访问次数top10
     */
    @Test
    void getTop() {
        //获取今日凌晨12:00:00时间 也就是明日的00:00:00
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime tomorrow = localDateTime.withHour(0).withMinute(0).withSecond(0).plusDays(1);
        LocalDateTime yesterday = tomorrow.plusDays(-1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String tomorrowStr = formatter.format(tomorrow);
        String yesterdayStr = formatter.format(yesterday);


        TermsAggregationBuilder aggregation = AggregationBuilders
                .terms("username").field("username").size(2);
        aggregation.subAggregation(AggregationBuilders.count("count").field("username"));
        ElasticBuilder builder = new ElasticRequestBuilder();
        ElasticRequest request = builder.buildWithIndex(new ElasticIndex().alias("bird-log"))
                .buildWithAggregation(aggregation)
//                .buildWithQuery(QueryBuilders.rangeQuery("logDate").from(yesterdayStr).to(tomorrowStr))
                .build();
        SearchResponse searchResponse = documentComponent.aggregation(request);
        Aggregations aggregations = searchResponse.getAggregations();
        Terms termsByUsername = aggregations.get("username");
        List<? extends Terms.Bucket> buckets = termsByUsername.getBuckets();
        List<DataVo> dataVoList=new ArrayList<>();
        buckets.forEach((item) -> {
            DataVo dataVo=new DataVo();
            //获取每个桶的标识
            String key = item.getKeyAsString();
            //拿每个桶的不同指标结果 注意使用具体的聚合类型接收 不然多态下获取不了value
            ParsedValueCount valueCount = item.getAggregations().get("count");
            long value = valueCount.getValue();
            dataVo.setKey(key);
            dataVo.setValue((int) value);
            dataVoList.add(dataVo);
        });

        dataVoList.forEach(System.out::println);
    }

    /**
     * @Author lipu
     * @Date 2021/8/26 9:19
     * @Description 获取一个用户时间范围内不同时间点的访问次数
     */
    @Test
    void getUserInterViewByTime() {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //获取今日凌晨12:00:00时间 也就是明日的00:00:00
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime tomorrow = localDateTime.withHour(0).withMinute(0).withSecond(0).plusDays(1);
        String tomorrowStr = formatter.format(tomorrow);
        LocalDateTime yesterday = tomorrow.plusDays(-1);
        String yesterdayStr = formatter.format(yesterday);
        List<DateUtils.Point> pointList = DateUtils.getDayPoint();
        ElasticBuilder builder = new ElasticRequestBuilder();
        List<DataVo> dataVoList =new ArrayList<>();
        //查询每个时间段的数据
        pointList.forEach(item->{
            DataVo dataVo=new DataVo();
            ElasticRequest request = builder.buildWithIndex(new ElasticIndex().alias("bird-log"))
                    .buildWithQuery(QueryBuilders.boolQuery()
                            .must(QueryBuilders.termQuery("username","lp"))
                            .must(QueryBuilders.rangeQuery("logDate").from(item.getStartStr()).to(item.getEndStr()))
                    )
                    .build();
            List<EsLog> logList = documentComponent.search(request, EsLog.class);
            dataVo.setKey(item.getName());
            dataVo.setValue(logList.size());
            dataVoList.add(dataVo);
        });

        dataVoList.forEach(System.out::println);


        System.out.println("---执行结束---");
    }

    /**
     * @Author lipu
     * @Date 2021/8/25 17:43
     * @Description 原生API查询top10
     */
    @Test
    void topApi() throws Exception {
        RestHighLevelClient client = factory.getClient();
        SearchRequest searchRequest = new SearchRequest("bird-log");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("byUsername")
                .field("username");
        aggregation.subAggregation(AggregationBuilders.count("count")
                .field("username"));
        searchSourceBuilder.aggregation(aggregation);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations aggregations = searchResponse.getAggregations();
        Terms sexAggregation = aggregations.get("byUsername");
        List<? extends Terms.Bucket> buckets = sexAggregation.getBuckets();
        buckets.forEach(item -> {
            //获取每个桶的标识
            String key = item.getKeyAsString();
            System.out.println(key);
            //拿每个桶的不同指标结果 注意使用具体的聚合类型接收 不然多态下获取不了value
            ParsedValueCount count = item.getAggregations().get("count");
            //指标的值
            long value = count.getValue();
            System.out.println(value);
        });
    }
}
