package com.aiops_web.entity.sql;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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


}
