package com.aiops_web.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.aiops_web.dao.elasticsearch.OriginalDataLakeRepository;
import com.aiops_web.dao.elasticsearch.OriginalDataRepository;
import com.aiops_web.entity.elasticsearch.OriginalData;
import com.aiops_web.entity.elasticsearch.OriginalDataLake;
import com.aiops_web.service.DataIntroducingService;
import com.aiops_web.service.OriginalDataService;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author DaiQL
 * @time 2023/4/17
 */
@Service
public class OriginalDataServiceImpl implements OriginalDataService {
    private final DataIntroducingService dataIntroducingService;
    private final OriginalDataRepository originalDataRepository;
    private final OriginalDataLakeRepository originalDataLakeRepository;
    private final RestTemplateBuilder restTemplateBuilder;
    private final String index = "origin_data";
    private final String indexLake = "origin_data_lake";

    public OriginalDataServiceImpl(OriginalDataRepository originalDataRepository,
                                   RestTemplateBuilder restTemplateBuilder,
                                   DataIntroducingService dataIntroducingService,
                                   OriginalDataLakeRepository originalDataLakeRepository) {
        this.originalDataRepository = originalDataRepository;
        this.restTemplateBuilder = restTemplateBuilder;
        this.dataIntroducingService = dataIntroducingService;
        this.originalDataLakeRepository = originalDataLakeRepository;
    }

    @Override
    public List<OriginalData> getRange(long beginId, long endId) {
        List<OriginalData> dataList = new ArrayList<>();
        //两个索引都查一下
        SearchResponse<OriginalData> searchResponse = getRangeSearchResponse(beginId, endId, index, OriginalData.class);
        if (searchResponse != null) {
            searchResponse.hits().hits().forEach(h -> dataList.add(h.source()));
        }
        searchResponse = getRangeSearchResponse(beginId, endId, indexLake, OriginalData.class);
        if (searchResponse != null) {
            searchResponse.hits().hits().forEach(h -> dataList.add(h.source()));
        }
        Collections.sort(dataList); // 按 calcId 排序
        return dataList;
    }

    /**
     * 在 indexName 索引中查询 beginId<=id<=endId 的数据，并指定其返回类型
     *
     * @param beginId:   范围查询，id最小值
     * @param endId:     范围查询，id最大值
     * @param indexName: 想要查询的索引名称
     * @param className: 查询后返回的类型
     * @return co.elastic.clients.elasticsearch.core.SearchResponse<T>
     */
    private <T> SearchResponse<T> getRangeSearchResponse(long beginId, long endId, String indexName, Class<T> className) {
        RestClient restClient = RestClient.builder(new HttpHost("82.157.145.14", 9200)).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);

        SearchResponse<T> searchResponse = null;
        try {
            searchResponse = client.search(
                    s -> s.index(indexName).query(
                            q -> q.range(r -> r.field("calcId").gte(JsonData.of(beginId)).lte(JsonData.of(endId)))
                    ), className);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("连接出错，获取数据失败！");
        }
        return searchResponse;
    }

    @Override
    public List<OriginalData> getRelativeRange(int batchId, long beginId, long endId) {
        RestClient restClient = RestClient.builder(new HttpHost("82.157.145.14", 9200)).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);

        List<OriginalData> dataList = new ArrayList<>();
        try {
            // 根据 batchId 找到数据所在的索引
            String indexName;
            System.out.println(dataIntroducingService.getById(batchId).getPlace());
            if (dataIntroducingService.getById(batchId).getPlace().equals("OriginalData")) {
//                indexName = index;
                indexName = index;
            } else {
                indexName = indexLake;
            }

            SearchResponse<OriginalData> searchResponse = client.search(
                    s -> s.index(indexName).query(
                            q -> q.bool(b -> b.
                                    must(m -> m.match(u -> u.field("batchId").query(batchId))).
                                    must(m -> m.range(r -> r.field("relaId").gte(JsonData.of(beginId)).lte(JsonData.of(endId)))))
                    ).sort(o -> o.field(f -> f.field("relaId").order(SortOrder.Asc))),
                    OriginalData.class); // must是必须满足所有条件
            searchResponse.hits().hits().forEach(h -> dataList.add(h.source()));

            transport.close();
            restClient.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("连接出错，获取数据失败！");
        }
//        System.out.println("dataList: ");
//        System.out.println(dataList);
        return dataList;
    }

    @Override
    public boolean deleteRange(long beginId, long endId) {
        List<OriginalData> dataList1 = new ArrayList<>();
        //查询 es 中符合条件的数据 id
        SearchResponse<OriginalData> searchResponse = getRangeSearchResponse(beginId, endId, index, OriginalData.class);
        if (searchResponse != null) {
            searchResponse.hits().hits().forEach(h -> dataList1.add(h.source()));
            for (OriginalData data : dataList1) {
                data.setDeleted(1);
            }
            originalDataRepository.saveAll(dataList1);
        }
        List<OriginalDataLake> dataList2 = new ArrayList<>();
        SearchResponse<OriginalDataLake> searchResponse2 = getRangeSearchResponse(beginId, endId, indexLake, OriginalDataLake.class);
        if (searchResponse2 != null) {
            searchResponse2.hits().hits().forEach(h -> dataList2.add(h.source()));
            for (OriginalDataLake data : dataList2) {
                data.setDeleted(1);
            }
            originalDataLakeRepository.saveAll(dataList2);
        }
        return true;
    }

    /**
     * 从文件中读取源数据，添加到 elasticsearch 的 origin_data
     * 不支持并发插入
     *
     * @param batchId:  源数据的批次号
     * @param objId:    数据类型
     * @param filepath: 存储源数据的文件
     */
    @Override
    public long addBatchDoc(int batchId, int objId, String filepath) {
        long curNum = originalDataRepository.count() + originalDataLakeRepository.count();
        System.out.println("当前已有" + curNum + "条源数据");
        List<OriginalData> addList = new ArrayList<>();
        long addNum = 1L;
        try {
            Scanner scanner = new Scanner(new File(filepath));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                OriginalData originalData = new OriginalData();
                originalData.setBatchId(batchId);
                originalData.setRelaId(addNum);
                originalData.setContent(line);
                originalData.setDeleted(0);
                originalData.setObjId(objId);
                originalData.setCalcId(curNum + addNum);
                addList.add(originalData);

                addNum++;
                if (addNum % 500 == 0) {
                    originalDataRepository.saveAll(addList);
                    addList.clear();
                    System.out.println("insert 500 successfully");
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        originalDataRepository.saveAll(addList);
        System.out.println("insert " + (addNum - 1L) + " in total");
        return addNum;
    }

    /**
     * 将批次为 batchId 的源数据转移到 OriginalDataLake 索引中
     *
     * @param batchId: 源数据的批次
     */
    @Override
    public void TransferDataToLake(int batchId) {
        String base = "http://82.157.145.14:9200";
        String url = base + "/_reindex"; // 请求路径
        //通过 build 方法获取核心类 RestTemplate，提供了所拥有访问 Rest 服务的接口
        RestTemplate restTemplate = restTemplateBuilder.build();
        // 设置请求头和请求体
        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
        requestHeaders.setContentType(type);
        /*  POST _reindex
            {
              "source": {
                "index": "origin_data",
                "query": {
                    "match": {
                        "batchId": 1
                    }
                }
              },
              "dest": {
                "index": "origin_data_lake"
              }
            }
        *
        * */
        String content = "{\"source\": {" +
                "\"index\": \"origin_data\"," +
                "\"query\": {\"match\": {\"batchId\": " + batchId + "} } }," +
                "\"dest\": {\"index\": \"origin_data_lake\"} }";
        HttpEntity<String> requestEntity = new HttpEntity<>(content, requestHeaders);
        ResponseEntity<String> postForEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        // 返回是一个 json，解析
        JSONObject jsTemp = JSONObject.parseObject(postForEntity.getBody());
        System.out.println(jsTemp);

        // reindex 并不会删除原索引中的数据，需要另外设置
        /*  POST _delete_by_query
            {
               "query": {
                  "match": {
                     "batchId": 1
                  }
               }
            }
         * */
        url = base + "/" + index + "/_doc/_delete_by_query";
        content = "{\"query\": {\"match\": {\"batchId\": " + batchId + "} } }";
        requestEntity = new HttpEntity<>(content, requestHeaders);
        postForEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        jsTemp = JSONObject.parseObject(postForEntity.getBody());
        System.out.println(jsTemp);
    }
}
