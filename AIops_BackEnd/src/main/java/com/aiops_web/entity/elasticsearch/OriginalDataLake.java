package com.aiops_web.entity.elasticsearch;

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
}
