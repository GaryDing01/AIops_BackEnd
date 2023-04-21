package com.aiops_web.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.aiops_web.dao.elasticsearch.OriginalDataRepository;
import com.aiops_web.entity.elasticsearch.OriginalData;
import com.aiops_web.service.neo4j.OriginalDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OriginalDataServiceImpl implements OriginalDataService {
    @Resource
    private OriginalDataRepository originalDataRepository;

    // 创建低级客户端
    private final RestClient restClient = RestClient.builder(new HttpHost("82.157.145.14", 9200)).build();

    // 使用Jackson映射器创建传输层
    private final ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

    private final ElasticsearchClient client = new ElasticsearchClient(transport); // 创建API客户端

    private final String index = "origin_data";


//    private static final String BASIC = "123456789qwertyuiopasdfghjklzxcvbnm";

//    public static String random() {
//        char[] basicArray = BASIC.toCharArray();
//        Random random = new Random();
//        char[] result = new char[6];
//        for (int i = 0; i < 6; i++) {
//            int index = random.nextInt(100) % (basicArray.length);
//            result[i] = basicArray[index];
//        }
//        return new String(result);
//    }

    /**
     * 添加单条文档
     *
     * @param batchId 导入源数据的批次编号
     * @param content 源数据的具体内容
     * @param objId   源数据的具体类型
     */
    @Override
    public void createDocument(Integer batchId, String content, Integer objId) throws IOException {
        OriginalData originalData = new OriginalData();
        originalData.setBatchId(batchId);
        originalData.setContent(content);
        originalData.setObjId(objId);
        originalData.setCalcId(1L);
        originalDataRepository.save(originalData);
//        System.out.println(originalDataRepository.count());
//        //生成不重复的 id
//        Date date = new Date(); //获取当前的日期
//        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); //设置日期格式
//        String str = df.format(date) + random(); //获取 String 类型的时间
//        //向索引中添加数据
//        CreateResponse createResponse = client.create(e -> e.index(index).id(str).document(originalData));
//        System.out.println("createResponse.result() = " + createResponse.result());
//
//        SearchResponse<OriginalData> searchResponse = client.search(s -> s.index(index).query(q -> q.range(r -> r.field("batchId").gte(JsonData.of(1)))), OriginalData.class);
//        searchResponse.hits().hits().forEach(h -> System.out.println(h.source().toString()));
    }

    /**
     * 批量插入文档
     *
     * @param data 源数据的集合
     */
    @Override
    public void addBatchDocument(List<OriginalData> data) throws IOException {
        // 构建一个批量数据集合
        List<BulkOperation> list = new ArrayList<>();
        for (OriginalData origin : data) {
            list.add(new BulkOperation.Builder().create(d -> d.document(origin).index(index)).build());
        }
        // 调用 bulk 方法执行批量插入操作
        BulkResponse bulkResponse = client.bulk(e -> e.index(index).operations(list));
        System.out.println("bulkResponse.items() = " + bulkResponse.items());
    }
}
