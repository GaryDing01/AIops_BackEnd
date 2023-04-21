package com.aiops_web.entity.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "origin_data") // 若索引不存在，会自动创建，因为该注解的 createIndex 属性默认为 true
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OriginalData {

    private Integer batchId;

    @Field(type = FieldType.Text)
    private String content;

    private Integer objId;

    @Id
    private Long calcId;
//
//    private Integer deleted;
}
