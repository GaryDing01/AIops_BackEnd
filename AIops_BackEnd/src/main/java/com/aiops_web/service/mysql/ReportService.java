package com.aiops_web.service.mysql;

import com.aiops_web.entity.mysql.Report;
import com.aiops_web.entity.mysql.WorkflowExec;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    // 根据流程获取报告
    List<Report> selectReportByWf(Integer wfId);
}
