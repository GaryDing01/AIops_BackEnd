package com.aiops_web.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.aiops_web.dao.elasticsearch.OriginalDataLakeRepository;
import com.aiops_web.dao.elasticsearch.OriginalDataRepository;
import com.aiops_web.entity.elasticsearch.OriginalData;
import com.aiops_web.entity.elasticsearch.OriginalDataLake;
import com.aiops_web.service.OriginalDataService;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author DaiQL
 * @time 2023/4/17
 */
@Service
public class OriginalDataServiceImpl implements OriginalDataService {
    private final OriginalDataRepository originalDataRepository;
    private final OriginalDataLakeRepository originalDataLakeRepository;

    private final String index = "origin_data";

    public OriginalDataServiceImpl(OriginalDataRepository originalDataRepository, OriginalDataLakeRepository originalDataLakeRepository) {
        this.originalDataRepository = originalDataRepository;
        this.originalDataLakeRepository = originalDataLakeRepository;
    }

    @Override
    public List<OriginalData> getRange(int beginId, int endId) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("82.157.145.14", 9200)).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);

        SearchResponse<OriginalData> searchResponse = client.search(
                s -> s.index(index).query(
                        q -> q.range(r -> r.field("calcId").gte(JsonData.of(beginId)).lte(JsonData.of(endId)))),
                OriginalData.class);
        List<OriginalData> dataList = new ArrayList<>();
        searchResponse.hits().hits().forEach(h -> dataList.add(h.source()));

        transport.close();
        restClient.close();
        return dataList;
    }

    @Override
    public List<OriginalData> getAll() {
        Iterable<OriginalData> dataIterable = originalDataRepository.findAll();
        List<OriginalData> dataList = new ArrayList<>();
        dataIterable.forEach(dataList::add);
        return dataList;
    }

    @Override
    public boolean deleteRange(int beginId, int endId) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("82.157.145.14", 9200)).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);

        //查询 es 中符合条件的数据 id
        SearchResponse<OriginalData> searchResponse = client.search(
                s -> s.index(index).query(
                        q -> q.range(r -> r.field("calcId").gte(JsonData.of(beginId)).lte(JsonData.of(endId)))),
                OriginalData.class);
        transport.close();
        restClient.close();


        List<OriginalData> updateList = new ArrayList<>();
        searchResponse.hits().hits().forEach(h -> updateList.add(h.source()));
        for (OriginalData data : updateList) {
            data.setDeleted(1);
        }
        originalDataRepository.saveAll(updateList);
        return true;
    }

    @Override
    public void addBatchDoc(int batchId, int objId, String filepath) {
        long curNum = originalDataRepository.count();
        System.out.println("当前已有" + curNum + "条源数据");
        List<OriginalData> addList = new ArrayList<>();
        List<OriginalDataLake> addList2 = new ArrayList<>();
        long addNum = 1L;
        try {
            Scanner scanner = new Scanner(new File(filepath));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                OriginalData originalData = new OriginalData();
                originalData.setBatchId(batchId);
                originalData.setContent(line);
                originalData.setDeleted(0);
                originalData.setObjId(objId);
                originalData.setCalcId(curNum + addNum);
                addList.add(originalData);

                OriginalDataLake originalDataLake = new OriginalDataLake();
                originalDataLake.setBatchId(batchId);
                originalDataLake.setContent(line);
                originalDataLake.setDeleted(0);
                originalDataLake.setObjId(objId);
                originalDataLake.setCalcId(curNum + addNum);
                addList2.add(originalDataLake);

                addNum++;
                if (addNum % 200 == 0) {
                    originalDataRepository.saveAll(addList);
                    originalDataLakeRepository.saveAll(addList2);
                    addList.clear();
                    addList2.clear();
                    System.out.println("insert 200 successfully");
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        originalDataRepository.saveAll(addList);
        originalDataLakeRepository.saveAll(addList2);
        System.out.println("insert " + addNum + " in total");
    }
}
