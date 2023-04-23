package com.aiops_web.entity.elasticsearch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 将 origin_data_lake 中的数据映射为实体类
 *
 * @author DaiQL
 * @time 2023/4/17
 */
@Document(indexName = "origin_data_lake")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OriginalDataLake {

    private Integer batchId;

    @Field(type = FieldType.Text)
    private String content;

    private Integer objId;

    @Id
    private Long calcId;

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
