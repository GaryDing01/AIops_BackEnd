package com.aiops_web.entity.elasticsearch;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private Long relaId;

    private Integer deleted;

    /* 预配置索引的 _class 字段
     * 使用 OriginalDataRepository 的 save 方法往索引中推数据时，会发现索引结构多出了 _class 字段
     * 这是因为是因为 OriginalData 实例被保存到 es 时，会被序列化处理为 json 结构字符串（jackson）
     * 而类信息会被放置在固定字段 _class 上，用以在取回数据时反序列化为对应类实例
     * 因此 es 在接收到数据后会按照默认方案，生成 string 字段对应的 text+keyword 结构。
     * */
    @Field(name = "_class", type = FieldType.Keyword, index = false)
    @JsonIgnore
    private String _class;
}
