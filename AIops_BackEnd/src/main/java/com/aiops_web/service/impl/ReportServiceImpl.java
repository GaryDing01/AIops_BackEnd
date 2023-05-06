package com.aiops_web.service.impl;

import com.aiops_web.dao.sql.WorkflowExecMapper;
import com.aiops_web.dto.ExecStepDTO;
import com.aiops_web.entity.sql.Report;
import com.aiops_web.dao.sql.ReportMapper;
import com.aiops_web.entity.sql.WorkflowExec;
import com.aiops_web.service.ReportService;
import com.aiops_web.utils.Utils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {
    @Resource
    ReportMapper reportMapper;

    @Resource
    WorkflowExecMapper workflowExecMapper;

    @Resource
    WorkflowExecServiceImpl workflowExecService;

    Utils utils = new Utils();

    @Override
    public Boolean saveOneReportByExec(WorkflowExec workflowExec) {
        Report report = new Report();
        int inSample = 0; // 0表示没有用Sample，即数据小于5条
        int outSample = 0; // 1表示使用Sample，即数据大于等于5条

        // 1. 生成报告编号
        int currentReportNum = workflowExecMapper.selectCount(null);
        System.out.println("currentReportNum: " + currentReportNum);
//        workflowExec.setReportId(currentReportNum + 1);
//        reports.setReportId(currentReportNum + 1);

        // 2. 生成输入数据样例
        // 2.1 获取输入数据
        System.out.println("workflowExec.getInputId(): " + workflowExec.getInputId());
        List<String> inputDataAll = workflowExecService.getInOutData(workflowExec.getInputTypeId(), workflowExec.getInputId(), 1);
//        System.out.println("inputDataAll: " + inputDataAll);
        // 2.2 生成样例
        if (inputDataAll.size() < 5) {
            report.setInputData(inputDataAll.toString());
        }
        else {
            inSample = 1;
            report.setInputData(inputDataAll.subList(0, 5).toString());
        }
        // 如果是源日志作为输入的话，覆盖
        if (workflowExec.getInputTypeId() == 1 && workflowExec.getOutputTypeId() == 1) {
            inSample = -1;
            String[] originalDataTemp = workflowExec.getInputId().split("\\|");
            if (originalDataTemp.length != 3) {
                return false;
            }
            report.setInputData("您已选择源日志, " + "批次编号为: " + originalDataTemp[0]
                    + ", 起始相对位置为: " + originalDataTemp[1]
                    + ", 结束相对位置编号为: " + originalDataTemp[2]);
        }
//        System.out.println("inputSample: " + report.getInputData()); // 有乱码

        // 3. 生成输出数据样例
        // 3.1 获取输入数据
        System.out.println("workflowExec.getOutputId(): " + workflowExec.getOutputId());
        List<String> outputDataAll = workflowExecService.getInOutData(workflowExec.getOutputTypeId(), workflowExec.getOutputId(), 1);
        // 2.2 生成样例
        if (outputDataAll.size() < 5) {
            report.setOutputData(outputDataAll.toString());
        }
        else {
            outSample = 1;
            report.setOutputData(outputDataAll.subList(0, 5).toString());
        }
        System.out.println("outputSample: " + report.getOutputData());

        // 4. 生成执行信息和备注
        report.setSituation("该步骤执行已成功, 详情请查看输入输出数据情况及执行报告.");
        StringBuilder remark = new StringBuilder("");
        if (workflowExec.getOutputTypeId() < 5) {
            if (inSample == 0) {
                remark.append("已展示所有输入数据. ");
            }
            else if (inSample == 1) {
                remark.append("因输入数据过多, 仅展示5条样例. ");
            }
            else {
                remark.append("");
            }
            if (outSample == 0) {
                remark.append("已展示所有输出数据. ");
            }
            else if (outSample == 1) {
                remark.append("因输出数据过多, 仅展示5条样例. ");
            }
            else {
                remark.append("");
            }
        }
        else if (workflowExec.getOutputTypeId() == 5) {
            remark.append("已展示故障检测结果. ");
        }

        report.setRemark(remark.toString());
        report.setTstamp(utils.getCurrentTstamp());
//        System.out.println(report);
        reportMapper.insert(report);

        workflowExec.setReportId(report.getReportId()); // 别忘了修改原来的WorkflowExec

        return true;
    }

    @Override
    public List<Report> selectReportByWf(Integer wfId) {
        List<ExecStepDTO> execStepDTOList = workflowExecMapper.selectExecStepByWf(wfId);
        List<Integer> reportIdList = new ArrayList<>();
        for (ExecStepDTO execStepDTO : execStepDTOList) {
            reportIdList.add(execStepDTO.getReportId());
        }
        List<Report> reportList = reportMapper.selectBatchIds(reportIdList);
        return reportList;
    }
}
