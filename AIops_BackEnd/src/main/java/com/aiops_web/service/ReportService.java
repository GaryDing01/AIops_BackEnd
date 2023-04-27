package com.aiops_web.service;

import com.aiops_web.entity.sql.Report;
import com.aiops_web.entity.sql.WorkflowExec;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
public interface ReportService extends IService<Report> {
    // 没生成一个执行情况，就生成一份对应的报告
    Boolean saveOneReportByExec(WorkflowExec workflowExec);

}
