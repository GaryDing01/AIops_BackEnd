package com.aiops_web.entity.sql;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.cypher.internal.expressions.In;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AiopsAlg implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "alg_id", type = IdType.AUTO)
    private Integer algId;

    private Integer typeId;

    private String name;

    private String intro;

    private String source;

    private Date updateTstamp;

    private Integer updateNum;

    private Integer userId;

    private String param;

    private String content;


    public AiopsAlg() {
    }

    /**
     * 把 json 解析成 map  之后用map初始化 alg
     * 因为 param 是 String  因此不能直接把json解析成AiopsAlg对象
     * @param map
     */
    public AiopsAlg(Map<String, Object> map) throws JsonProcessingException {
        this.algId = (Integer) map.get("algId");
        this.typeId = (Integer) map.get("typeId");
        this.name = (String) map.get("name");
        this.intro = (String) map.get("intro");
        this.source = (String) map.get("source");
        this.updateTstamp = new Date(Long.parseLong((String)map.get("updateTstamp")));
        this.updateNum = (Integer) map.get("updateNum");
        this.userId = (Integer) map.get("userId");
        this.content = (String) map.get("content");
        param = listToParamString((List) map.get("param"));
    }

    public String listToParamString(List<Map<String, Object>> params) throws JsonProcessingException {
//        StringBuilder sb = new StringBuilder();
//        sb.append("[");
//        for (Map<String, Object> param : params) {
//            sb.append("{\"name\": \"" + param.get("name") + "\", \"value\": \"" + param.get("value") + "\" },");
//        }
//        sb.deleteCharAt(sb.length()-1);  // 删除最后一个 ,
//        sb.append("]");
//
//        param = sb.toString();

        // jackson 里有 对象 -> String
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(params);

        System.out.println(str);
        return str;
    }

    public List<Map<String, Object>> paramToList() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> list = mapper.readValue(param, List.class);
        return list;
    }
}
