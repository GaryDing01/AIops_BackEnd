package com.aiops_web.entity.mysql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
public class ParsedLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "parse_id", type = IdType.AUTO)
    private Long parseId;

    private String logLineid;

    private String logDate;

    private String logTimestamp;

    private String logTraceid;

    private String logSpanid;

    private String logUnknown;

    private String logLevel;

    private String logContent;

    private String logEventid;

    private String logEventtemplate;

    @TableLogic
    private Integer deleted;


}
